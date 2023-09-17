"use client";

import { getLogout } from "@/routes/route";
import { useRouter } from "next/navigation";
import Cookies from "js-cookie";

const LogoutBtn = () => {
  const router = useRouter();

  const onClickBtn = async () => {
    const status = await getLogout();

    // if (status === 200) router.refresh();
    if (status === 204) {
      Cookies.remove("accessToken");
      Cookies.remove("refreshToken");
      alert("로그아웃 되었습니다.")
      router.push("/login");
    }
  };

  return (
    <button
      onClick={onClickBtn}
      className="absolute top-4 right-4 bg-gray-500 rounded-md p-2 text-white"
    >
      로그아웃
    </button>
  );
};

export default LogoutBtn;
