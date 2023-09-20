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
            className="absolute top-4 bg-gray-500 rounded-md p-2 text-white"
            style={{right: "304px"}}
        >
            회원관리
        </button>
    );
};

export default UserInfoBtn;