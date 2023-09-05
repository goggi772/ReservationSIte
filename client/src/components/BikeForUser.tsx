"use client";

import { IBikeProps } from "@/interface/interface";

const BikeForUser: React.FC<IBikeProps> = ({ id, status, onClick }) => {
  let bgColor;
  let isClickable = true;

  switch (status) {
    case "available":
      bgColor = "bg-blue-500";
      break;
    case "completed":
      bgColor = "bg-gray-500";
      isClickable = false;
      break;
    case "yours":
      bgColor = "bg-green-500";
      break;
    case "disabled":
      bgColor = "bg-red-500";
      isClickable = false;
  }

  return (
    <button
      disabled={!isClickable}
      onClick={isClickable ? onClick : undefined}
      className={`w-16 h-16 ${bgColor} text-white rounded-lg focus:outline-none ${
        isClickable ? "cursor-pointer" : "cursor-not-allowed"
      }`}
    >
      {id}
    </button>
  );
};

export default BikeForUser;
