"use client";

import { useRouter } from "next/navigation";

const AdminHomeBtn = () => {
    const router = useRouter();

    const onClickBtn = async () => {
        router.push("/admin/reservation");
    };

    return (
        <button
            onClick={onClickBtn}
            className="absolute top-4 right-52 bg-gray-500 rounded-md p-2 text-white"
        >
            예약현황
        </button>
    );
};

export default AdminHomeBtn;