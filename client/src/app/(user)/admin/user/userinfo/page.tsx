"use client";

import WithAdminOnly from "@/components/hoc/WithAdminOnly";
import { useEffect, useState } from "react";
import { IUser } from "@/interface/interface";
import {deleteUser, getIUser, putUserPW} from "@/routes/route";
import Pagination from "@/components/UserInfoPaging";
import React from "react";
import {element} from "prop-types";


const UserInfo = () => {

    const [IUser, setIUsers] = useState<IUser[]>([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(1);
    const [totalCnt, setTotalCnt] = useState(0);
    const [pagesize, setPageSize] = useState(10);


    const handlePageChange = (page: number) => {
        setCurrentPage(page);
    };

    const handleResetPassword = async (username: string) => {
        const shouldReset = window.confirm("비밀번호를 초기화하시겠습니까?");

        if (shouldReset) {
            const res = await putUserPW(username);

            const data = await res.json();
            const { message } = data;
            if (res.status == 200) {
                alert("비밀번호가 초기화 되었습니다.")
            } else if (res.status == 400) {
                alert(message)
            } else {
                alert("internal server error")
            }
        }
    };

    const handleDeleteUser = async (username: string) => {
        const shouldDelete = window.confirm("삭제하시겠습니까?");

        if (shouldDelete) {
            const res = await deleteUser(username);
            if (res.status === 200) {
                alert("삭제되었습니다.")
                setIUsers((prevUsers) => prevUsers.filter((user) => user.username !== username));
            } else if (res.status === 400) {
                alert("유저가 존재하지 않거나 정상적으로 처리되지 않았습니다.");
            } else {
                alert("internal server error");
            }
        }
    };

    useEffect(() => {

        const fetchUsers = async () => {
            const res = await getIUser();
            console.log("유저정보 가져오기")

            if (res.status == 401) {
                alert("로그아웃 되었습니다.");
                return;
            } else if (res.status !== 200) {
                alert("internal server error");
            }

            const newIUser = await res.json();
            const totalCount = newIUser.length;
            setIUsers(newIUser);
            setTotalCnt(totalCount);
            setTotalPages(Math.ceil(totalCount / pagesize));
        }
        fetchUsers();
    }, []);

    const startIndex = (currentPage - 1) * pagesize;
    const endIndex = startIndex + pagesize;
    const usersToShow = IUser.slice(startIndex, endIndex);

    return (
        <div className="flex flex-col justify-center items-center h-screen px-28">
            <h1 className="text-3xl font-semibold mb-4">회원 관리</h1>
            <table className="min-w-full divide-y divide-gray-500">
                <thead className="bg-gray-800 sticky top-0">
                <tr>
                    <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-white uppercase tracking-wider">
                        No.
                    </th>
                    <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-white uppercase tracking-wider">
                        이름
                    </th>
                    <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-white uppercase tracking-wider">
                        번호
                    </th>
                    <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-white uppercase tracking-wider">
                        VIP
                    </th>
                    <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-white uppercase tracking-wider">
                        예약여부
                    </th>
                    <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-white uppercase tracking-wider">
                        PW 초기화
                    </th>
                    <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-white uppercase tracking-wider">
                        회원 정보 삭제
                    </th>
                </tr>
                </thead>
                <tbody className="bg-white divide-y divide-gray-200">
                {usersToShow.map((user, index) => (
                    <tr key={index}>
                    <td className="px-6 whitespace-nowrap" width="50px">
                        <div className="text-sm text-gray-900">{totalCnt - ((totalCnt - (index+1)) - (currentPage-1) * pagesize)}</div>
                    </td>
                    <td className="px-6 whitespace-nowrap" width="50px">
                        <div className="text-sm text-gray-900">{user.username}</div>
                    </td>
                    <td className="px-6 whitespace-nowrap" width="100px">
                        <div className="text-sm text-gray-900">{user.phoneNumber}</div>
                    </td>
                    <td className="px-6 whitespace-nowrap" width="100px">
                        <div className="text-sm text-gray-900">{user.vip ? 'VIP' : '일반회원'}</div>
                    </td>
                    <td className="px-5 whitespace-nowrap" width="100px">
                        <div className="text-sm text-gray-900">{user.reserved ? '예약 불가' : '예약 가능'}</div>
                    </td>
                    <td className="px-5 whitespace-nowrap" width="50px">
                        <button className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
                        onClick={() => handleResetPassword(user.username)}>
                            초기화
                        </button>
                    </td>
                    <td className="px-6 whitespace-nowrap" width="50px">
                        <button className="bg-red-400 hover:bg-red-500 text-white font-bold py-2 px-4 rounded"
                        onClick={() => handleDeleteUser(user.username)}>
                            삭제
                        </button>
                    </td>
                </tr>
                    ))}
                </tbody>
            </table>
                <Pagination
                    currentPage={currentPage}
                    totalPages={totalPages}
                    onPageChange={handlePageChange}/>
        </div>


    );
};
export default WithAdminOnly(UserInfo);