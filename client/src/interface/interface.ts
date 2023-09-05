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
  name: string;
  username: string;
  isAdmin: string;
  // isReserved: string;
}

export interface IBike {
  id: number;
  status: "available" | "completed" | "disabled" | "yours";
  owner: string | null;
}
