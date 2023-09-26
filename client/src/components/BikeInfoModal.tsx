import React, { useState } from "react";
import Modal from "react-modal";

import { IBike } from "@/interface/interface";

interface Props {
  bike: IBike | null;
  isOpen: boolean;
  onClose: () => void;
  // ownerName: string;
  cancelBook: () => Promise<void>;
  disableBook: () => Promise<void>;
}
function convertStatus(status: any) {
  switch (status) {
    case "available":
      return "예약 가능";
    case "disabled":
      return "이용 불가능";
    case "completed":
      return "예약 완료";
    default:
      return "알 수 없음";
  }
}

const BikeInfoModal: React.FC<Props> = ({
  bike,
  isOpen,
  onClose,
  // ownerName,
  cancelBook,
  disableBook,
}) => {
  const isDisabled = bike?.status === "disabled";
  const isBooked = bike?.status === "completed";

  if (!bike) return <Modal isOpen={isOpen} onRequestClose={onClose} ariaHideApp={false}></Modal>;
  return (
    <Modal isOpen={isOpen} onRequestClose={onClose} ariaHideApp={false}
           style={{
             content: {
               width: '50%', // 원하는 크기로 조절
               height: '50%',
               margin: 'auto', // 가운데 정렬
             },
             overlay: {
               backgroundColor: 'rgba(0, 0, 0, 0.7)', // 모달 바깥 배경 색상
             },
           }}>
      <button onClick={onClose} className="absolute top-4 right-4">
        ❌
      </button>

      <div className="flex flex-col items-center justify-center h-full">
        <div className="text-center">
          <h2 className="text-lg font-semibold mb-2">Bike ID: {bike.id}</h2>
          <p className="text-lg font-semibold mb-2">예약 상태: {convertStatus(bike.status)}</p>
          {bike.owner ? (
            <>
              <h2 className="text-lg font-semibold mb-2">예약 정보</h2>
              <p className="mb-2">예약자: {bike.owner}</p>
            </>
          ) : (
            <div></div>
          )}
        </div>
        <div className="h-8"></div>
        <div className="mt-4 flex justify-between w-full px-4">
          <div></div>
          <button
            onClick={cancelBook}
            className={`bg-red-500 text-white p-2 rounded-md ${
              isBooked ? "cursor-pointer" : "cursor-not-allowed"
            }`}
          >
            예약 취소
          </button>

          <button
            onClick={disableBook}
            className={`bg-gray-500 p-2 rounded-md text-white`}
          >
            {isDisabled ? "예약 가능" : "예약 불가"}
          </button>
          <div></div>
        </div>
      </div>
    </Modal>
  );
};

export default BikeInfoModal;
