"use client";

import React from 'react';
import {PaginationProps} from "@/interface/interface";

const Pagination: React.FC<PaginationProps> = ({ currentPage, totalPages, onPageChange }) => {

    const itemsPerPage = 10; // 페이지 그룹당 페이지 수
    const currentPageGroup = Math.ceil(currentPage / itemsPerPage);
    const lastPageGroup = Math.ceil(totalPages / itemsPerPage);

    // 페이지 그룹마다 첫 번째 페이지와 마지막 페이지 계산
    const startPage = (currentPageGroup - 1) * itemsPerPage + 1;
    const endPage = currentPageGroup === lastPageGroup ? totalPages : currentPageGroup * itemsPerPage;

    const pages = Array.from({ length: endPage - startPage + 1 }, (_, index) => startPage + index);
    // const pages = Array.from({length: totalPages}, (_, index) => index + 1);

    return (
        <div className="row">
            <div className="col">
                <ul className="pagination">
                    <button
                        onClick={() => onPageChange(currentPage - (currentPage % 10 + 9))}
                        className={`px-3 py-1 mx-1 rounded-md 
                        bg-white text-black hover:bg-gray-200`}
                        disabled={currentPage <= 10}
                    >
                        ⇦
                    </button>
                    {pages.map((page) => (
                        <button
                            key={page}
                            onClick={() => onPageChange(page)}
                            className={`px-3 py-1 mx-1 rounded-md ${
                                currentPage === page
                                    ? 'bg-black text-white'
                                    : 'bg-white text-black hover:bg-gray-200'
                            }`}
                        >
                            {page}
                        </button>
                    ))}
                    <button
                        onClick={() => onPageChange(currentPage + (11 - currentPage % 11))}
                        className={`px-3 py-1 mx-1 rounded-md ${
                            currentPage === totalPages
                                ? 'bg-white text-black hover:bg-gray-200'
                                : 'bg-white text-black hover:bg-gray-200'
                        }`}
                        disabled={currentPage > totalPages - (totalPages % 10)}
                    >
                        ⇨
                    </button>
                </ul>
            </div>
        </div>
    );
};

export default Pagination;