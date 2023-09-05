"use client";

import { useEffect, useState } from "react";
import { cloneDeep } from "lodash";

import BikeForAdmin from "@/components/BikeForAdmin";
import { getBikes, putCancleBook, putDeleteBook } from "@/routes/route";
import { IBike } from "@/interface/interface";
import BikeInfoModal from "@/components/BikeInfoModal";
import WithAdminOnly from "@/components/hoc/WithAdminOnly";

const SeatsPage = () => {
  /*const initialBikes: IBike[] = Array.from({ length: 36 }, (_, index) => ({
    id: `${index}`,
    status: "available",
    owner: "",
  }));*/
  const [bikes, setBikes] = useState<IBike[]>([]);

  const [isModalOpen, setModalOpen] = useState(false);
  const [bike, setBike] = useState<IBike | null>(null);

  const handleClick = async (index: number) => {
    setBike(cloneDeep(bikes[index]));
    setModalOpen(true);
  };

  const handleCancleBook = async () => {
    if (!bike) return;
    const res = await putCancleBook(bike.id, bike.status);
    if (res.status === 200) {
      const newBikesInfo = [...bikes];
      newBikesInfo[bike.id].status === "available";
      setBikes(newBikesInfo);

      alert("취소되었습니다.");
      setModalOpen(false);
    } else if (res.status === 400) {
      alert(res.statusText);
    } else {
      alert("internal server error");
    }
  };

  const handleDisableBook = async () => {
    if (!bike) return;
    const res = await putDeleteBook(bike.id, bike.status);
    if (res.status === 200) {
      const newBikesInfo = [...bikes];

      if (bike.status === "disabled") {
        newBikesInfo[bike.id].status === "available";
        alert("예약 가능 상태로 전환되었습니다.");
      } else {
        newBikesInfo[bike.id].status === "disabled";
        alert("예약 불가 상태로 전환되었습니다.");
      }

      setBikes(newBikesInfo);
      setModalOpen(false);
    } else if (res.status === 400) {
      alert(res.statusText);
    } else {
      alert("internal server error");
    }
  };

  useEffect(() => {
    const fetchBikes = async () => {
      const res = await getBikes();

      if (res.status !== 200) {
        alert("예약 가능 기간이 아닙니다.");
        return;
      }

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
        ownerName="John Doe"
        cancelBook={handleCancleBook}
        disableBook={handleDisableBook}
      />

      <h1 className="text-3xl font-semibold mb-4">Bike Seat Reservation</h1>
      <div className="grid grid-cols-6 gap-4">
        {bikes.map((bike, index) => (
          <BikeForAdmin
            key={bike.id}
            id={bike.id}
            owner={bike.owner}
            status={bike.status}
            onClick={() => handleClick(index)}
          />
        ))}
      </div>
    </div>
  );
};

export default WithAdminOnly(SeatsPage);
