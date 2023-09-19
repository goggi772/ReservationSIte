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
  reserved: string;
  vip: boolean;
}

export interface IBike {
  id: number;
  status: "available" | "completed" | "disabled" | "yours";
  owner: string | null;
}

export interface PaginationProps {
  currentPage: number;
  totalPages: number;
  onPageChange: (page: number) => void;
}
