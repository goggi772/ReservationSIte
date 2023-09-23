import {IBike} from "@/interface/interface";
import React from "react";
import Modal from "react-modal";
import BikeForAdmin from "@/components/BikeForAdmin";

interface BikeClone {
    bikes: IBike[] | null;
    isOpen: boolean;
    onClose: () => void;

    timeStatus: String;
}

const BikeInfoCloneModal: React.FC<BikeClone> = ({
    bikes,
    isOpen,
    onClose,
    timeStatus,
}) => {
    const bike_for_index = [0, 2, 5, 9, 14, 20, 26, 33];
    if (bikes?.length == 0) {
        return <Modal isOpen={isOpen} onRequestClose={onClose} ariaHideApp={false}></Modal>;
    }
    return (
        <Modal isOpen={isOpen} onRequestClose={onClose} ariaHideApp={false}
               style={{
                   content: {
                       width: '80%', // 원하는 크기로 조절
                       height: '80%',
                       margin: 'auto', // 가운데 정렬
                   },
                   overlay: {
                       backgroundColor: 'rgba(0, 0, 0, 0.7)', // 모달 바깥 배경 색상
                   },
               }}>
            <button onClick={onClose} className="absolute top-4 right-4">
                ❌
            </button>
            <div className="text-xl font-semibold mb-4 text-center">{timeStatus}</div>

            {bike_for_index.slice(0, 7).map((_, index1) => {
                const i = bike_for_index[index1];
                const j = bike_for_index[index1 + 1];
                if (bikes?.length != 0){
                    return (
                        <div className="flex justify-center items-center mb-3" key={index1}>
                            {bikes?.slice(i, j).map((bike, index) => (
                                <div className="mr-12 ml-12" key={bike.id}>
                                    <BikeForAdmin
                                        key={bike.id}
                                        id={bike.id}
                                        owner={bike.owner}
                                        status={bike.status}
                                        onClick={() => false}
                                    />
                                </div>
                            ))}
                            <div className={i == 20 ? 'ml-40' : ''}></div>
                        </div>
                    );}
            })}
        </Modal>
    );
};

export default BikeInfoCloneModal;