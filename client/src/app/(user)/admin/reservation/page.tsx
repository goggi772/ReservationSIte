"use client";

import { useEffect, useState } from "react";
import { cloneDeep } from "lodash";

import BikeForAdmin from "@/components/BikeForAdmin";
import { getBikes, putCancelBook, putDeleteBook } from "@/routes/route";
import { IBike } from "@/interface/interface";
import BikeInfoModal from "@/components/BikeInfoModal";
import WithAdminOnly from "@/components/hoc/WithAdminOnly";
import BikeForUser from "@/components/BikeForUser";

const SeatsPage = () => {

  const [bikes, setBikes] = useState<IBike[]>([]);

  const [isModalOpen, setModalOpen] = useState(false);
  const [bike, setBike] = useState<IBike | null>(null);
  const newBikesInfo = [...bikes];

  const bike_for_index = [0, 2, 5, 9, 14, 20, 26, 33];

  const handleClick = async (index: number) => {

    setBike(cloneDeep(bikes[index]));
    setModalOpen(true);
  };

  const handleCancelBook = async () => {
    if (bike?.owner == null) return;

    const res = await putCancelBook(bike.id);
    if (res.status === 200) {
      const newBikesInfo = [...bikes];
      newBikesInfo[bike.id - 1].status = "available";
      newBikesInfo[bike.id - 1].owner = null;

      alert("취소되었습니다.");
      setBikes(newBikesInfo);
      setModalOpen(false);
      return;
    } else if (res.status === 400) {
      alert(res.statusText);
    } else {
      alert("internal server error");
    }
  };

  const handleDisableBook = async () => {
    if (!bike) return;
    const res = await putDeleteBook(bike.id);
    console.log(bike.id);
    if (res.status === 200) {
      // const newBikesInfo = [...bikes];

      if (bike.status === "disabled") {
        newBikesInfo[bike.id - 1].status = "available";
        alert("예약 가능 상태로 전환되었습니다.");
      } else {
        newBikesInfo[bike.id - 1].status = "disabled";
        alert("예약 불가 상태로 전환되었습니다.");
      }

      setBikes(newBikesInfo);
      setModalOpen(false);
    } else if (res.status === 400) {
      alert("이미 예약된 자리는 예약 불가로 설정할 수 없습니다.");
    } else {
      alert("internal server error");
      window.location.reload();
    }
    return;
  };

  useEffect(() => {
    const fetchBikes = async () => {
      const res = await getBikes();

      if (res.status == 401) {
        alert("다시 로그인 해주세요.");
        return;
      } else

      setBikes(await res.json());
    };
    fetchBikes();
  }, []);

  return (
    <div className="p-8">
      <BikeInfoModal
        bike={bike}
        isOpen={isModalOpen}
        onClose={() => setModalOpen(false)}
        cancelBook={handleCancelBook}
        disableBook={handleDisableBook}
      />
      <div className="mb-5"></div>
      <h1 className="text-3xl font-semibold mb-4">Spinning Reservation</h1>

      {bike_for_index.slice(0, 7).map((_, index) => {
        const i = bike_for_index[index];
        const j = bike_for_index[index + 1];
        return (
            <div className="flex justify-center items-center mb-3">
              {bikes.slice(i, j).map((bike, index) => (
                  <div className="mr-12 ml-12">
                    <BikeForUser
                        key={bike.id}
                        id={bike.id}
                        status={bike.status}
                        onClick={() => handleClick(index)}
                    />
                  </div>
              ))}
              <div className={i == 20 ? 'ml-40' : ''}></div>
            </div>
        );
      })}
      {/*<div className="grid grid-cols-5 gap-4">*/}
      {/*  {bikes.map((bike, index) => (*/}
      {/*    <BikeForAdmin*/}
      {/*      key={bike.id}*/}
      {/*      id={bike.id}*/}
      {/*      owner={bike.owner}*/}
      {/*      status={bike.status}*/}
      {/*      onClick={() => handleClick(index)}*/}
      {/*    />*/}
      {/*  ))}*/}
      {/*</div>*/}
    </div>
  );
};

export default WithAdminOnly(SeatsPage);
