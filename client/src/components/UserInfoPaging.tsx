"use client";

import React from 'react';
import {PaginationProps} from "@/interface/interface";

const Pagination: React.FC<PaginationProps> = ({ currentPage, totalPages, onPageChange }) => {
    const pages = Array.from({ length: totalPages }, (_, index) => index + 1);

    return (
        <div className="row">
            <div className="col">
                <ul className="pagination">
                    {/*{pages.map((page) => (
                        <li key={page} className={`page-item ${currentPage === page ? 'active' : ''}`}>
                            <a
                                href={`#`}
                                className={`page-link`}
                                onClick={(e) => {
                                    e.preventDefault();
                                    onPageChange(page);
                                }}
                            >
                                <span>{page}</span>
                            </a>
                        </li>
                    ))}*/}
                    {pages.map((page) => (
                        <button
                            key={page}
                            onClick={() => onPageChange(page)}
                            className={`px-3 py-1 mx-1 rounded-md ${
                                currentPage === page
                                    ? 'bg-blue-500 text-white'
                                    : 'bg-white text-blue-500 hover:bg-blue-100'
                            }`}
                        >
                            {page}
                        </button>
                    ))}
                </ul>
            </div>
        </div>
    );
};

export default Pagination;