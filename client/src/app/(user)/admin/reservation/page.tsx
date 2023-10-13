"use client";

import React, { useEffect, useState } from "react";
import { cloneDeep } from "lodash";

import BikeForAdmin from "@/components/BikeForAdmin";
import {getBikes, getBikesInfoClone, putCancelBook, putDeleteBook} from "@/routes/route";
import { IBike } from "@/interface/interface";
import BikeInfoModal from "@/components/BikeInfoModal";
import WithAdminOnly from "@/components/hoc/WithAdminOnly";
import BikeForUser from "@/components/BikeForUser";
import LogoutBtn from "@/components/button/LogoutBtn";
import SignupBtn from "@/components/button/SignupBtn";
import AdminHomeBtn from "@/components/button/AdminHomeBtn";
import UserInfoBtn from "@/components/button/UserInfoBtn";
import Modal from "react-modal";
import ReactModal from "react-modal";
import BikeInfoCloneModal from "@/components/BikeInfoCloneModal";

const SeatsPage = () => {

  const [bikes, setBikes] = useState<IBike[]>([]);
  const [cloneBikes, setCloneBikes] = useState<IBike[]>([]);

  const [isModalOpen, setModalOpen] = useState(false);
  const [CloneModalOpen, setCloneModalOpen] = useState(false);
  const [bike, setBike] = useState<IBike | null>(null);
  // const newBikesInfo = [...bikes];

  const bike_for_index = [0, 2, 5, 9, 14, 20, 26, 33];



  const handleClick = async (index: number) => {

    setBike(cloneDeep(bikes[index]));
    setModalOpen(true);
  };

  const handleGetBikeClone = async() => {
    const res = await getBikesInfoClone();


    if (res.status == 200) {
      setCloneBikes(await res.json());
      setCloneModalOpen(true);
    } else {
      alert("Server Error");
      window.location.reload();
    }
  }

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
    if (res.status === 200) {
      const newBikesInfo = [...bikes];

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

      if (res.status != 200) {
        alert("internal server error.");
        return;
      } else

      setBikes(await res.json());
    };
    fetchBikes();
  }, []);

  const spinning_time = () => {
    const date = new Date;
    const hour = date.getHours();
    const min = date.getMinutes();

    if ((hour >= 0 && hour < 9)){
      return "9시 타임 예약 현황";
    }
    else if (hour == 9) {
      return "10시 타임 예약 현황";
    }
    else if ((hour >= 10 && hour < 19) || (hour == 19 && min <= 20)) {
      return "7시 20분 타임 예약 현황";
    }
    else return "8시 20분 타임 예약 현황";
  }
  const clone_bike_time = () => {
    const date = new Date;
    const hour = date.getHours();
    const min = date.getMinutes();
    if (hour < 10)  return "9시 타임 예약 현황"
    else if ((hour >= 10 && hour < 19) || (hour == 19 && min <= 20)) return "10시 타임 예약 현황"
    else if ((hour == 19 && min > 20) || (hour == 20 && min <= 20)) return " 7시 20분 타임 예약 현황"
    else return "8시 20분 타임 예약 현황"
  }


  return (
    <div className="p-8">
      <BikeInfoModal
        bike={bike}
        isOpen={isModalOpen}
        onClose={() => setModalOpen(false)}
        cancelBook={handleCancelBook}
        disableBook={handleDisableBook}
      />
      <BikeInfoCloneModal bikes={cloneBikes} isOpen={CloneModalOpen} onClose={() => setCloneModalOpen(false)}
      timeStatus={clone_bike_time()}/>
      <div className="mb-5"></div>
      <div className="flex justify-end">
      <h1 className="text-3xl font-semibold mb-4">Spinning Reservation</h1>
        <div className="m-auto"></div>
        <button
            onClick={handleGetBikeClone}
            className="absolute top-4 bg-gray-500 rounded-md p-2 text-white"
            style={{right: "400px"}}
        >
          이전 예약
        </button>
        <UserInfoBtn/>
        <AdminHomeBtn/>
        <SignupBtn/>
        <LogoutBtn/>

      </div>

      <h2 className="text-xl font-semibold mb-4 text-center">{spinning_time()}</h2>

      {bike_for_index.slice(0, 7).map((_, index1) => {
        const i = bike_for_index[index1];
        const j = bike_for_index[index1 + 1];
        if (bikes.length != 0){
        return (
            <div className="flex justify-center items-center mb-3" key={index1}>
              {bikes.slice(i, j).map((bike, index) => (
                  <div className="mr-12 ml-12" key={bike.id}>
                    <BikeForAdmin
                        key={bike.id}
                        id={bike.id}
                        owner={bike.owner}
                        status={bike.status}
                        onClick={() => handleClick(i + index)}
                    />
                  </div>
              ))}
              <div className={i == 20 ? 'ml-40' : ''}></div>
            </div>
        );}
      })}

    </div>
  );
};

export default WithAdminOnly(SeatsPage);
