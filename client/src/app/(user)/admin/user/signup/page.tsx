"use client";

import WithAdminOnly from "@/components/hoc/WithAdminOnly";
import { postSignup } from "@/routes/route";

const SignupPage = () => {
  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const formData = new FormData(e.currentTarget);

    const username = formData.get("username") as string;
    const password = formData.get("password") as string;

    const status = await postSignup(username, password);
    if (status === 201) alert("회원가입 완료");
    else if (status === 400) alert("아이디가 겹칩니다.");
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
              htmlFor="name"
            >
              Name
            </label>
            <input
              type="text"
              id="name"
              name="name"
              className="mt-1 p-2 w-full border rounded-md"
            />
          </div>
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
