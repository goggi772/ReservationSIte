"use client";

import { IAdminBikeProps } from "@/interface/interface";

const BikeForAdmin: React.FC<IAdminBikeProps> = ({
  id,
  status,
  onClick,
  owner,
}) => {
  let bgColor;
  let isClickable = true;

  switch (status) {
    case "available":
      bgColor = "bg-blue-500";
      break;
    case "completed":
      bgColor = "bg-gray-500";
      break;
    case "disabled":
      bgColor = "bg-red-500";
  }

  return (
    <button
      disabled={!isClickable}
      onClick={isClickable ? onClick : undefined}
      className={`w-16 h-16 ${bgColor} text-white rounded-lg focus:outline-none ${
        isClickable ? "cursor-pointer" : "cursor-not-allowed"
      }`}
    >
      {id}<br/>
      {owner}
    </button>
  );
};

export default BikeForAdmin;
