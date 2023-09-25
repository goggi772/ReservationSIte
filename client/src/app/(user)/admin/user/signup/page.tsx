"use client";

import WithAdminOnly from "@/components/hoc/WithAdminOnly";
import {postSignup} from "@/routes/route";
import {useState} from "react";
import {Simulate} from "react-dom/test-utils";
import error = Simulate.error;
import {boolean} from "zod";
import {useRouter} from "next/navigation";

const SignupPage = () => {

  const router = useRouter();

  const [error, setError] = useState<string | null>(null);
  const [formData, setFormData] = useState({
    username: "",
    password: "",
    phoneNumber: "",
    isVIP: false,
  });

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (formData.username == "" || formData.password == "") {
      setError("필수 입력 사항 입니다.");
      return;
    }

    const status = await postSignup(formData.username, formData.password, formData.phoneNumber, formData.isVIP);
    if (status === 200) {
      alert("회원가입 완료");
      setFormData({
        username: "",
        password: "",
        phoneNumber: "",
        isVIP: false,
      });
    }
    else if (status === 400){
      setError("이미 존재하는 이름입니다.");
      return;
    }
    else alert("internal server error");
  };

  const handleInputChange = (
      e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>
  ) => {
    const { name, value} = e.currentTarget;
    const type = e.currentTarget.type;
    const checked = e.currentTarget.ariaChecked;
    const inputValue = type === "checkbox" ? checked : value;

    setFormData({
      ...formData,
      [name]: inputValue,
    });
  };

  const onClickBtn = async () => {
    router.push("/admin/reservation");
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
              value={formData.username}
              onChange={handleInputChange}
              className="mt-1 p-2 w-full border rounded-md"
            />
          </div>
          {error && <div className="text-red-500 text-sm mb-4">{error}</div>}
          <div className="mb-4">
            <label
                className="block text-sm font-medium text-black"
                htmlFor="phoneNumber"
            >
              UserNumber
            </label>
            <input
                type="text"
                id="phoneNumber"
                name="phoneNumber"
                value={formData.phoneNumber}
                onChange={handleInputChange}
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
              value={formData.password}
              onChange={handleInputChange}
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
              checked={formData.isVIP}
              onChange={handleInputChange}
            />
          </div>
          <button
            type="submit"
            className="w-full bg-blue-500 text-white p-2 rounded-md hover:bg-blue-600"
          >
            Sign Up
          </button>
        </form>
          <div className="mb-4"></div>
          <button
              onClick={onClickBtn}
              className="w-full bg-gray-500 rounded-md p-2 text-white hover:bg-gray-600"
          >
            예약화면으로
          </button>
      </div>
    </div>
  );
};

export default WithAdminOnly(SignupPage);
