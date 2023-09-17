"use client";

import { useRouter } from "next/navigation";

const SignupBtn = () => {
    const router = useRouter();

    const onClickBtn = async () => {
        router.push("/admin/user/signup");
    };

    return (
        <button
            onClick={onClickBtn}
            className="absolute top-4 right-28 bg-gray-500 rounded-md p-2 text-white"
        >
            회원등록
        </button>
    );
};

export default SignupBtn;