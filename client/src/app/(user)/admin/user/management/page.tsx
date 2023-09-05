"use client";

import { useState } from "react";

import { IUser } from "@/interface/interface";
import { deleteUser, getUserByUseranme, putUserName } from "@/routes/route";
import WithAdminOnly from "@/components/hoc/WithAdminOnly";

const ManagementUserPage = () => {
  const [isSearched, setIsSearched] = useState(false);

  const [username, setUsername] = useState("");
  const [newName, setNewName] = useState("");

  let id: string;

  const handleSearch = async () => {
    const res = await getUserByUseranme(username);
    if (res.status === 200) {
      const user: IUser = await res.json();

      id = user.id;
      setNewName(user.name);
      setIsSearched(true);
    } else if (res.status === 400) {
      alert("읎는거같은뎁숑?");
    } else {
      alert("internal server error");
    }
  };

  const handleUpdateName = async () => {
    const res = await putUserName(id, newName);
    if (res.status === 200) {
      alert("성공!");
    } else if (res.status === 400) {
      alert(res.statusText);
    } else {
      alert("internal server error");
    }
  };

  const handleResetPW = async () => {
    const res = await putUserName(id, newName);
    if (res.status === 200) {
      alert("성공!");
    } else if (res.status === 400) {
      alert(res.statusText);
    } else {
      alert("internal server error");
    }
  };

  const handleDeleteUser = async () => {
    const res = await deleteUser(username);
    if (res.status === 200) {
      id = "";
      setNewName("");
      setUsername("");
      setIsSearched(false);
    } else if (res.status === 400) {
      alert("이럴리가 없는데...");
    } else {
      alert("internal server error");
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-white">
      <div className="p-12 bg-white rounded shadow-lg w-96">
        <h1 className="text-3xl font-semibold mb-4">유저 관리</h1>
        <div className="mb-4">
          <label
            className="block text-sm font-medium text-black"
            htmlFor="username"
          >
            Username
          </label>
          <div className="flex items-center">
            <input
              type="text"
              id="username"
              name="username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              className="mt-1 p-2 border rounded-md h-10 w-48"
            />
            <button
              onClick={handleSearch}
              className="ml-2 bg-blue-500 text-white p-2 rounded-md hover:bg-blue-600 h-10 w-full"
            >
              조회
            </button>
          </div>
        </div>

        {isSearched && (
          <div className="mt-8">
            <label
              className="block text-sm font-medium text-black"
              htmlFor="newName"
            >
              New Name
            </label>
            <div className="flex">
              <input
                type="text"
                id="newName"
                name="newName"
                value={newName}
                onChange={(e) => setNewName(e.target.value)}
                className="mt-1 p-2 border rounded-md h-10 w-48"
              />
              <button
                onClick={handleUpdateName}
                className="ml-2 bg-blue-500 text-white p-1 rounded-md hover:bg-blue-600 h-10 w-full"
              >
                이름 변경
              </button>
            </div>
            <div className="flex mt-3">
              <button
                onClick={handleResetPW}
                className="ml-2 bg-red-500 text-white p-2 rounded-md hover:bg-red-600 h-10 w-1/2"
              >
                비밀번호 초기화
              </button>
              <button
                onClick={handleDeleteUser}
                className="ml-2 bg-red-500 text-white p-2 rounded-md hover:bg-red-600 h-10 w-1/2"
              >
                계정 삭제
              </button>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default WithAdminOnly(ManagementUserPage);
