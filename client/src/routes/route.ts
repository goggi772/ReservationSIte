import dotenv from "dotenv";
import Cookies from "js-cookie";
import fetchInterceptor from "fetch-intercept";

dotenv.config();

const serverURL = process.env.NEXT_PUBLIC_SERVER_URL as string;

//
// fetchInterceptor.register({
//   request: function (url, config) {
//     const accessToken = Cookies.get('accessToken');
//     // 특정 요청에만 accessToken 추가
//     console.log("토큰 있나? ",accessToken);
//     if (accessToken != undefined) {
//       config.headers['Authorization'] = `Bearer ${accessToken}`;
//     }
//     return [url, config];
//   },
// });

// fetchIntercept.register({
//   response: async function (response) {
//     if (response.status === 401) {
//   },
// });

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
  phoneNumber: string,
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
    body: JSON.stringify({ username, password, phoneNumber, isVIP }),
  });

  return res.status;
};

export const getUserByUsername = async (username: string) => {
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

export const putUserPW = async (username: string) => {
  const accessToken = Cookies.get('accessToken');
  return await fetch(serverURL + "/admin/resetPassword", {
    method: "POST",
    credentials: "include",
    headers: {
      "Authorization": `Bearer ${accessToken}`,
      "Content-Type": "text/plain",
    },
    // body: JSON.stringify({username}),
    body: username,
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

export const deleteUser = async (username: string) => {
  const accessToken = Cookies.get('accessToken');
  const res = await fetch(serverURL + "/admin/delete", {
    method: "DELETE",
    credentials: "include",
    headers: {
      "Authorization": `Bearer ${accessToken}`,
      "Content-Type": "text/plain",
    },
    body: username,
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

export const getIUser = async () => {
  const accessToken = Cookies.get('accessToken');
  const res = await fetch(serverURL + `/admin/member/view`, {
    method: "GET",
    credentials: "include",
    headers: {
      "Authorization": `Bearer ${accessToken}`,
      "Content-Type": "application/json",
    },
  });

  return res;
};


