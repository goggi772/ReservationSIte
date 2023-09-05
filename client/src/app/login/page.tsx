// pages/login.tsx
"use client";

import {fetchLogin, getCheckAdmin} from "@/routes/route";
import { useState } from "react";
import { useRouter } from "next/navigation";
import WithAuthOnly from "@/components/hoc/WithAuthOnly";
import {log} from "util";
import Cookies from "js-cookie";

const LoginPage = () => {
  const router = useRouter();

  const [username, setUsername] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [error, setError] = useState<string | null>(null);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    const response = await fetchLogin(username, password);


    if (response.status === 200) {
      console.log("성공: "+ response.status)
      const data = await response.json();
      const { accessToken, refreshToken } = data;

      Cookies.set('accessToken', accessToken);
      Cookies.set('refreshToken', refreshToken);
      setError(null);
      const res = await getCheckAdmin();
      if (res) router.push("/admin/reservation");
      else router.push("/reservation");

    } else if (response.status === 500) {
      alert("Internal Server Error!");
    } else {
      console.log("실패: " + response.status)
      setPassword("");
      setError("아이디나 비밀번호가 다릅니다.");
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-white">
      <div className="p-12 bg-white rounded shadow-lg w-96">
        <h1 className="text-3xl font-semibold mb-4">Login</h1>
        <form onSubmit={handleSubmit}>
          <div className="mb-4">
            <label
              className="block text-sm font-medium text-black"
              htmlFor="username"
            >
              아이디
            </label>
            <input
              type="text"
              id="username"
              name="username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              className="mt-1 p-2 w-full border rounded-md"
            />
          </div>

          <div className="mb-4">
            <label
              className="block text-sm font-medium text-black"
              htmlFor="password"
            >
              비밀번호
            </label>
            <input
              type="password"
              id="password"
              name="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className="mt-1 p-2 w-full border rounded-md"
            />
          </div>
          {error && <div className="text-red-500 text-sm mb-4">{error}</div>}
          <button
            type="submit"
            className="w-full bg-blue-500 text-white p-2 rounded-md hover:bg-blue-600"
          >
            Log In
          </button>
        </form>
      </div>
    </div>
  );
};

export default LoginPage;
