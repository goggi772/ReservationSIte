"use client";

import {changePW} from "@/routes/route";
import { useState } from "react";
import WithAuthOnly from "@/components/hoc/WithAuthOnly";
import { useRouter } from "next/navigation";
import Cookies from "js-cookie";
import LoginPage from "@/app/login/page";

const ChangePwPage = () => {
  const [oldPassword, setOldPassword] = useState<string>("");
  const [newPassword, setNewPassword] = useState<string>("");
  const [checkPassword, setCheckPassword] = useState<string>("");
  const [error, setError] = useState<string | null>(null);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

      const response = await changePW(oldPassword, newPassword);

    if (newPassword != checkPassword) {
      setNewPassword("");
      setCheckPassword("");
      setError("입력한 새 비밀번호가 일치하지 않습니다.")
    }

  };
  return (
      <div className="min-h-screen flex items-center justify-center bg-white">
        <div className="p-12 bg-white rounded shadow-lg w-96">
          <h1 className="text-3xl font-semibold mb-4">비밀번호 변경</h1>
          <form onSubmit={handleSubmit}>
            <div className="mb-4">
              <label
                  className="block text-sm font-medium text-black"
                  htmlFor="OldPassword"
              >
                현재 비밀번호
              </label>
              <input
                  type="password"
                  id="oldPassword"
                  name="oldPassword"
                  value={oldPassword}
                  onChange={(e) => setOldPassword(e.target.value)}
                  className="mt-1 p-2 w-full border rounded-md"
              />
            </div>

            <div className="mb-4">
              <label
                  className="block text-sm font-medium text-black"
                  htmlFor="password"
              >
                새 비밀번호
              </label>
              <input
                  type="password"
                  id="newPassword"
                  name="newPassword"
                  value={newPassword}
                  onChange={(e) => setNewPassword(e.target.value)}
                  className="mt-1 p-2 w-full border rounded-md"
              />
            </div>
            <div className="mb-4">
              <label
                  className="block text-sm font-medium text-black"
                  htmlFor="password"
              >
                새 비밀번호 확인
              </label>
              <input
                  type="password"
                  id="checkPassword"
                  name="checkPassword"
                  value={checkPassword}
                  onChange={(e) => setCheckPassword(e.target.value)}
                  className="mt-1 p-2 w-full border rounded-md"
              />
            </div>
            {error && <div className="text-red-500 text-sm mb-4">{error}</div>}
            <button
                type="submit"
                className="w-full bg-blue-500 text-white p-2 rounded-md hover:bg-blue-600"
            >
              변경
            </button>
          </form>
        </div>
      </div>
  );
};
export default WithAuthOnly(ChangePwPage);