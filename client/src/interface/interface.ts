export interface IBikeProps {
  id: number;
  status: "available" | "completed" | "disabled" | "yours";
  onClick: () => void;
}

export interface IAdminBikeProps extends IBikeProps {
  owner: string | null;
}

export interface IUser {
  id: string;
  username: string;
  phoneNumber: string;
  isReserved: boolean;
  isVIP: boolean;
}

export interface IBike {
  id: number;
  status: "available" | "completed" | "disabled" | "yours";
  owner: string | null;
}
