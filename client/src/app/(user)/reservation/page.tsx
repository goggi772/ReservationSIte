"use client";
import { useEffect, useState } from "react";
import {getBikes, getUsername, putBikeInfo} from "@/routes/route";
import { IBike } from "@/interface/interface";
import BikeForUser from "@/components/BikeForUser";
import WithAuthOnly from "@/components/hoc/WithAuthOnly";
import Cookies from "js-cookie";
import {router} from "next/client";

const SeatsPage = () => {
  /*const initialBikes: IBike[] = Array.from({ length: 36 }, (_, index) => ({
    id: `${index}`,
    status: "completed",
    owner: "",
  }));*/
  const [bikes, setBikes] = useState<IBike[]>([]);

  const handleClick = async (index: number) => {
    const newBikes = [...bikes];

    if (
      newBikes[index].status !== "available" &&
      newBikes[index].status !== "yours"
    ) {
      return;
    }

    //fetch
    const res = await putBikeInfo(newBikes[index].id, newBikes[index].status);

    if (res.status === 200) {
      /*if (newBikes[index].status === "available") {
        newBikes[index].status = "yours";
      } else if (newBikes[index].status === "yours") {
        newBikes[index].status = "available";
      }*/
      alert("예약되었습니다.");
      setBikes(newBikes);
      window.location.reload();
      return;
    } else if (res.status === 400) {
      alert("이미 예약되어 있습니다.");
      window.location.reload();
      return;
    } else {
      alert("internal server error");
      return;
    }
  };

  useEffect(() => {
    const fetchBikes = async () => {
      const res = await getBikes();
      const res2 = await getUsername();

      if (res.status !== 200) {
        alert("예약 가능 기간이 아닙니다.");
        return;
      }
      if (res2.status !== 200) {
        alert("다시 로그인해주세요.")
        window.location.reload();
        return;
      }

      const data = await res2.json();
      const { username, name } = data;

      const newBikes = (await res.json()) as IBike[];

      //yours 찾기
      newBikes.forEach((bike) => {
        if (bike.owner === username) bike.status = "yours";
      });

      setBikes(newBikes);
    };
    fetchBikes();
  }, []);


  return (
    <div className="p-8">
      <h1 className="text-3xl font-semibold mb-4">Bike Seat Reservation</h1>
      <div className="grid grid-cols-6 gap-4">
        {bikes.map((bike, index) => (
          <BikeForUser
            key={bike.id}
            id={bike.id}
            status={bike.status}
            onClick={() => handleClick(index)}
          />
        ))}
      </div>
    </div>
  );
};

export default WithAuthOnly(SeatsPage);
