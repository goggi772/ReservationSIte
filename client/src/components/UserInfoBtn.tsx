"use client";

import { useRouter } from "next/navigation";

const UserInfoBtn = () => {
    const router = useRouter();

    const onClickBtn = async () => {
        router.push("/admin/user/userinfo");
    };

    return (
        <button
            onClick={onClickBtn}
            className="absolute top-4 right-80 bg-gray-500 rounded-md p-2 text-white"
        >
            회원관리
        </button>
    );
};

export default UserInfoBtn;