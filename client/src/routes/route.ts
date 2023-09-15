import dotenv from "dotenv";
import Cookies from "js-cookie";

dotenv.config();

const serverURL = process.env.NEXT_PUBLIC_SERVER_URL as string;


export const fetchLogin = async (username: string, password: string) => {
  const res = await fetch(serverURL + "/login", {
    method: "POST",
    credentials: "include",
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ username, password }),
  });


  return res;
};

export const getLogout = async () => {
  const res = await fetch(serverURL + "/api/logout", {
    method: "POST",
    credentials: "include",
    headers: {
      "Content-Type": "application/json",
    },
  });

  return res.status;
};

export const getBikes = async () => {
  const accessToken = Cookies.get('accessToken');

  const res = await fetch(serverURL + "/bike", {
    method: "GET",
    credentials: "include",
    headers: {
      "Authorization": `Bearer ${accessToken}`,
      "Content-Type": "application/json",
    },
  });
  return res;
};
export const getUsername = async () => {
  const accessToken = Cookies.get('accessToken');

  const res = await fetch(serverURL + "/get/user", {
    method: "GET",
    credentials: "include",
    headers: {
      "Authorization": `Bearer ${accessToken}`,
      "Content-Type": "application/json",
    },
  });
  return res;
};



export const putBikeInfo = async (bikeId: number) => {
  const accessToken = Cookies.get('accessToken');
  const res = await fetch(serverURL + "/bike/reservation", {
    method: "PUT",
    credentials: "include",
    headers: {
      "Authorization": `Bearer ${accessToken}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ bikeId }),
  });

  return res;
};

export const postSignup = async (
  username: string,
  password: string,
  isVIP: boolean
) => {
  const accessToken = Cookies.get('accessToken');
  const res = await fetch(serverURL + "/admin/register", {
    method: "POST",
    credentials: "include",
    headers: {
      "Authorization": `Bearer ${accessToken}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ username, password, isVIP }),
  });

  return res.status;
};

export const getUserByUseranme = async (username: string) => {
  const accessToken = Cookies.get('accessToken');
  const res = await fetch(serverURL, {
    method: "POST",
    credentials: "include",
    headers: {
      "Authorization": `Bearer ${accessToken}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ username }),
  });

  return res;
};

export const putUserName = async (id: string, newName: string) => {
  const res = await fetch(serverURL, {
    method: "PUT",
    credentials: "include",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ id, newName }),
  });

  return res;
};

export const putUserPW = async (id: string) => {
  const accessToken = Cookies.get('accessToken');
  return await fetch(serverURL, {
    method: "PUT",
    credentials: "include",
    headers: {
      "Authorization": `Bearer ${accessToken}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify({id}),
  });
};

export const changePW = async (oldPassword: string, newPassword: string) => {
  const accessToken = Cookies.get('accessToken');
  return await fetch(serverURL + "/changePw", {
    method: "POST",
    credentials: "include",
    headers: {
      "Authorization": `Bearer ${accessToken}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify({oldPassword, newPassword})
  });
};

export const deleteUser = async (id: string) => {
  const accessToken = Cookies.get('accessToken');
  const res = await fetch(serverURL, {
    method: "DELETE",
    credentials: "include",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ id }),
  });

  return res;
};

export const putCancelBook = async (bikeId: number) => {
  const accessToken = Cookies.get('accessToken');
  const res = await fetch(serverURL + "/admin/reservation/cancel", {
    method: "PUT",
    credentials: "include",
    headers: {
      "Authorization": `Bearer ${accessToken}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ bikeId }),
  });

  return res;
};

export const putDeleteBook = async (bikeId: number) => {
  const accessToken = Cookies.get('accessToken');
  const res = await fetch(serverURL + "/admin/reservation/disabled", {
    method: "PUT",
    credentials: "include",
    headers: {
      "Authorization": `Bearer ${accessToken}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ bikeId }),
  });

  return res;
};

export const getCheckAuth = async () => {
  const accessToken = Cookies.get('accessToken');

  const res = await fetch(serverURL + "/auth/user", {
    method: "GET",
    credentials: "include",
    headers: {
      "Authorization": `Bearer ${accessToken}`,
      "Content-Type": "application/json",
    },
  });

  console.log(accessToken);

  if (res.status === 200) {
    return true;
  } else {
    return false;
  }
};

export const getCheckAdmin = async () => {
  const accessToken = Cookies.get('accessToken');
  const res = await fetch(serverURL + "/auth/admin", {
    method: "GET",
    credentials: "include",
    headers: {
      "Authorization": `Bearer ${accessToken}`,
      "Content-Type": "application/json",
    },
  });

  if (res.status === 200) {
    return true;
  } else {
    return false;
  }
};

export const getIUser = async (pageNo: number, pageSize: number) => {
  const accessToken = Cookies.get('accessToken');
  const res = await fetch(serverURL + `/admin/member/view?pageNo=${pageNo}&pageSize=${pageSize}`, {
    method: "GET",
    credentials: "include",
    headers: {
      "Authorization": `Bearer ${accessToken}`,
      "Content-Type": "application/json",
    },
  });

  return res;
};
