import React, { useState } from "react";
import Modal from "react-modal";

import { IBike } from "@/interface/interface";

interface Props {
  bike: IBike | null;
  isOpen: boolean;
  onClose: () => void;
  ownerName: string;
  cancelBook: () => Promise<void>;
  disableBook: () => Promise<void>;
}

const BikeInfoModal: React.FC<Props> = ({
  bike,
  isOpen,
  onClose,
  ownerName,
  cancelBook,
  disableBook,
}) => {
  const isDisabled = bike?.status === "disabled";
  const isBooked = bike?.status === "completed";

  if (!bike) return <Modal isOpen={isOpen} onRequestClose={onClose}></Modal>;
  return (
    <Modal isOpen={isOpen} onRequestClose={onClose}>
      <button onClick={onClose} className="absolute top-4 right-4">
        x
      </button>

      <div className="flex flex-col items-center justify-center h-full">
        <div className="text-center">
          <h2 className="text-lg font-semibold mb-2">Bike ID: {bike.id}</h2>
          {bike.ownerID ? (
            <>
              <h2 className="text-lg font-semibold mb-2">User Info</h2>
              <p className="mb-2">User ID: {bike.ownerID}</p>
              <p>Name: {ownerName}</p>
            </>
          ) : (
            <div></div>
          )}
        </div>

        <div className="mt-4 flex justify-between w-full px-4">
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
        </div>
      </div>
    </Modal>
  );
};

export default BikeInfoModal;
