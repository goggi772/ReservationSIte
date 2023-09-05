"use client";
import { useEffect, useState } from "react";
import {getBikes, getLogout, getUsername, putBikeInfo} from "@/routes/route";
import { IBike } from "@/interface/interface";
import BikeForUser from "@/components/BikeForUser";
import WithAuthOnly from "@/components/hoc/WithAuthOnly";
import LogoutBtn from "@/components/LogoutBtn";
import Cookies from "js-cookie";
import {router} from "next/client";
import {useRouter} from "next/navigation";

const SeatsPage = () => {
  const [bikes, setBikes] = useState<IBike[]>([]);
  const [username, setUsername] = useState('');

  const handleClick = async (index: number) => {
    const newBikes = [...bikes];

    if (
      newBikes[index].status !== "available" &&
      newBikes[index].status !== "yours"
    ) {
      return;
    }

    //fetch
    const res = await putBikeInfo(newBikes[index].id);



    if (res.status === 200) {
      const data = await res.json();
      const { message } = data;
      alert(message);
      setBikes(newBikes);
      window.location.reload();
      return;
    } else if (res.status === 400) {
      const data = await res.json();
      const { message } = data;
      alert(message);
      window.location.reload();
      return;
    } else if (res.status === 403) {
      alert("예약 가능한 시간이 아닙니다.");
      return;
    } else {
      alert("internal server error");
      window.location.reload();
      return;
    }
  };

  useEffect(() => {
    const fetchBikes = async () => {
      const res = await getBikes();
      const res2 = await getUsername();

      if (res.status == 401) {
        alert("로그아웃 되었습니다.");
        return;
      }
      else if (res.status !== 200) {
        alert("예약 가능 시간이 아닙니다.");
        return;
      }
      if (res2.status !== 200) {
        alert("다시 로그인해주세요.")
        window.location.reload();
        return;
      }

      const data = await res2.json();
      const { username, name } = data;

      setUsername(username);

      const newBikes = (await res.json()) as IBike[];

      //yours 찾기
      newBikes.forEach((bike) => {
        if (bike.owner === username) bike.status = "yours";
      });

      setBikes(newBikes);
    };
    fetchBikes();
  }, []);

  const LogoutBtn = () => {
    const router = useRouter();

    const onClickBtn = async () => {
      const status = await getLogout();

      // if (status === 200) router.refresh();
      if (status === 204) {
        Cookies.remove("accessToken");
        Cookies.remove("refreshToken");
        router.push("/login");
      }
    };
  }


  return (
    <div className="p-8">
      <h1 className="text-3xl font-semibold mb-4">Bike Seat Reservation</h1>
      <p className="text-3xl font-semibold mb-4 justify-end flex items-center">{username}님</p>
      <div className="grid grid-cols-5 gap-4">
        {bikes.map((bike, index) => (
          <BikeForUser
            key={bike.id}
            id={bike.id}
            status={bike.status}
            onClick={() => handleClick(index)}
          />
        ))}
      </div>
      <div className="h-3"></div>
      <div className="container justify-end flex items-center">
        <div className="w-14 h-14 bg-blue-500 text-white rounded-lg"></div>
        <p> :예약 가능</p>
        <div className="w-14 h-14 bg-green-500 text-white rounded-lg"></div>
        <p> :예약한 자리</p>
        <div className="w-14 h-14 bg-gray-500 text-white rounded-lg"></div>
        <p> :이미 예약된 자리</p>
        <div className="w-14 h-14 bg-red-500 text-white rounded-lg"></div>
        <p> :이용 불가능한 자리</p>
      </div>
    </div>
  );
};

export default WithAuthOnly(SeatsPage);
