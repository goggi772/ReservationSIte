import React, { useState } from "react";
import Modal from "react-modal";
import {IUser} from "@/interface/interface";

import { IBike } from "@/interface/interface";
import {start} from "repl";
import {modifyDate} from "@/routes/route";
import {FormData} from "next/dist/compiled/@edge-runtime/primitives";
interface Props {
    IUser: IUser | null;
    isOpen: boolean;
    onClose: () => void;
    position: { x: number; y: number };
    modifyButton: (user: IUser | null) => Promise<void>;
}



const ModifyDateModal: React.FC<Props> = ({
  IUser,
  isOpen,
  onClose,
  position,
  modifyButton
}) => {
  if (!IUser) return <Modal isOpen={isOpen} onRequestClose={onClose} ariaHideApp={false}></Modal>;

  const [modalPosition, setModalPosition] = useState(position);
  const [formData, setFormData] = useState({
     startDate: IUser?.startDate || '',
     endDate: IUser?.endDate || ''
  });
  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const modifiedUser: IUser = {
        ...IUser,
        startDate: formData.startDate,
        endDate: formData.endDate,
    };
    await modifyButton(modifiedUser);
  }

    const handleInputChange = (
        e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>
    ) => {
        const { name, value} = e.currentTarget;
        setFormData({
            ...formData,
            [name]: value,
        });
    };


  return(
    <Modal isOpen={isOpen} onRequestClose={onClose} ariaHideApp={false}
           style={{
             content: {
               width: '20%', // 원하는 크기로 조절
               height: '10%',
               margin: 'auto', // 가운데 정렬
               top: modalPosition.y,
               left: modalPosition.x,
             },
             overlay: {
               backgroundColor: 'rgba(0, 0, 0, 0)', // 모달 바깥 배경 색상
             },
           }}>
        <form onSubmit={handleSubmit}>
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

        </form>
        </Modal>
    )
};

export default ModifyDateModal;