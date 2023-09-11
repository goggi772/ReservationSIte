"use client";

import WithAdminOnly from "@/components/hoc/WithAdminOnly";
import {postSignup, putUserName} from "@/routes/route";
import {useState} from "react";
import {Simulate} from "react-dom/test-utils";
import error = Simulate.error;
import {boolean} from "zod";

const SignupPage = () => {

  const [error, setError] = useState<string | null>(null);

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const formData = new FormData(e.currentTarget);

    const username = formData.get("username") as string;
    const password = formData.get("password") as string;
    const isVIP = formData.has("isVIP");

    if (username == "" || password == "") {
      setError("필수 입력 사항 입니다.");
      return;
    }

    const status = await postSignup(username, password, isVIP);
    if (status === 200) {
      alert("회원가입 완료");
      window.location.reload();
    }
    else if (status === 400){
      setError("이미 존재하는 이름입니다.");
      return;
    }
    else alert("internal server error");
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-white">
      <div className="p-12 bg-white rounded shadow-lg w-96">
        <h1 className="text-3xl font-semibold mb-4">Sign Up</h1>
        <form onSubmit={handleSubmit}>
          <div className="mb-4">
            <label
              className="block text-sm font-medium text-black"
              htmlFor="username"
            >
              Username
            </label>
            <input
              type="text"
              id="username"
              name="username"
              className="mt-1 p-2 w-full border rounded-md"
            />
          </div>
          {error && <div className="text-red-500 text-sm mb-4">{error}</div>}
          <div className="mb-4">
            <label
              className="block text-sm font-medium text-black"
              htmlFor="password"
            >
              Password
            </label>
            <input
              type="password"
              id="password"
              name="password"
              className="mt-1 p-2 w-full border rounded-md"
            />
          </div>
          <div className="mb-4">
            <label
              className="block text-sm font-medium text-black"
              htmlFor="isVIP"
            >
              VIP
            </label>
            <input
              type="checkbox"
              id="isVIP"
              name="isVIP"
            />
          </div>
          <button
            type="submit"
            className="w-full bg-blue-500 text-white p-2 rounded-md hover:bg-blue-600"
          >
            Sign Up
          </button>
        </form>
      </div>
    </div>
  );
};

export default WithAdminOnly(SignupPage);
