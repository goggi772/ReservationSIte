"use client";

import WithAdminOnly from "@/components/hoc/WithAdminOnly";
import {postSignup} from "@/routes/route";
import {useState} from "react";
import {Simulate} from "react-dom/test-utils";
import error = Simulate.error;
import {boolean, date} from "zod";
import {useRouter} from "next/navigation";
import {now} from "lodash";

const SignupPage = () => {

  const router = useRouter();

  const [userNameError, setUsernameError] = useState<string | null>(null);
  const [passwordError, setPasswordError] = useState<string | null>(null);
  const [phoneNumberError, setPhoneNumberError] = useState<string | null>(null);
  const [dateError, setDateError] = useState<string | null>(null);

  const [formData, setFormData] = useState({
    username: "",
    password: "",
    phoneNumber: "",
    isVIP: false,
    startDate: "",
    endDate: ""
  });

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setPasswordError(null);
    setDateError(null);
    setPhoneNumberError(null);
    setUsernameError(null);

    if (formData.username == "" || formData.password == "" ||
        formData.phoneNumber == "" || formData.startDate == "" ||
        formData.endDate == "" || new Date(formData.startDate) > new Date(formData.endDate)){
      if (formData.username == "") {
        setUsernameError("필수 입력 사항 입니다.");
      }
      if (formData.password == "") {
        setPasswordError("필수 입력 사항입니다.");
      }
      if (formData.phoneNumber == "") {
        setPhoneNumberError("필수 입력 사항입니다.");
      }
      if (formData.startDate == "" || formData.endDate == "") {
        setDateError("필수 입력 사항입니다.");
      } else if (new Date(formData.startDate) > new Date(formData.endDate)) {
        setDateError("시작 날짜는 종료 날짜보다 이전이어야 합니다.");
      }
      return;
    }


    const status = await postSignup(formData.username, formData.password,
        formData.phoneNumber, formData.isVIP, formData.startDate, formData.endDate);
    if (status === 200) {
      alert("회원가입 완료");
      setFormData({
        username: "",
        password: "",
        phoneNumber: "",
        isVIP: false,
        startDate: "",
        endDate: "",
      });
    }
    else if (status === 400){
      setUsernameError("이미 존재하는 이름입니다.");
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
        <form onSubmit={handleSubmit} className="justify-start">
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
          {userNameError && <div className="text-red-500 text-sm mb-4">{userNameError}</div>}
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
          {passwordError && <div className="text-red-500 text-sm mb-4">{passwordError}</div>}
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
          {phoneNumberError && <div className="text-red-500 text-sm mb-4">{phoneNumberError}</div>}
          <div className="mb-4 flex">
            <div className="w-1/2">
            <label
                className="block text-sm font-medium text-black"
                htmlFor="startDate"
            >
              StartDate
            </label>
            <input
                type="date"
                id="startDate"
                name="startDate"
                value={formData.startDate}
                onChange={handleInputChange}
                className="mt-1 p-2 w-full border rounded-md"
            />
            </div>
            <div className="w-1/2">
            <label
                className="block text-sm font-medium text-black"
                htmlFor="endDate"
            >
              EndDate
            </label>
            <input
                type="date"
                id="endDate"
                name="endDate"
                value={formData.endDate}
                onChange={handleInputChange}
                className="mt-1 p-2 w-full border rounded-md"
            />
            </div>
          </div>
          {dateError && <div className="text-red-500 text-sm mb-4">{dateError}</div>}

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
