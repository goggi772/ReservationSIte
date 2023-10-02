import dotenv from "dotenv";
import Cookies from "js-cookie";
import fetchIntercept from "fetch-intercept";

dotenv.config();

const serverURL = process.env.NEXT_PUBLIC_SERVER_URL as string;


async function customFetch(url: string, options: RequestInit) {
  try {
    const response = await fetch(url, options);

    if (response.status === 401) {
      const accessToken = Cookies.get('accessToken');
      const refreshToken = Cookies.get('refreshToken');
      const res = await fetch(serverURL + "/api/refreshToken", {
        method: 'POST',
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ accessToken, refreshToken }),
      });
      if (res.status === 200) {
        const data = await res.json();
        const {accessToken} = data;

        Cookies.set('accessToken', accessToken);

        options.headers = {
          ...options.headers,
          Authorization: `Bearer ${accessToken}`
        };
        return fetch(url, options);
      } else {
        const status = await getLogout();
        if (status === 204) {
          Cookies.remove("accessToken");
          Cookies.remove("refreshToken");
          alert("다시 로그인해주세요.")
          window.location.href = "/login";
        }
      }
    }
    return response;
  } catch (error) {
    // 오류 처리
    console.error("HTTP 요청 중 오류 발생:", error);
    throw error;
  }
}

export const fetchLogin = async (username: string, password: string) => {
  const res = await fetch(serverURL + "/login", {
    method: "POST",
    credentials: "include",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ username, password }),
  });


  return res;
};

export const getLogout = async () => {
  const accessToken = Cookies.get('accessToken');
  const res = await fetch(serverURL + "/logout", {
    method: "POST",
    credentials: "include",
    headers: {
      "Authorization": `Bearer ${accessToken}`,
      "Content-Type": "application/json",
    },
  });

  return res.status;
};

export const getBikes = async () => {
  const accessToken = Cookies.get('accessToken');
  const res = await customFetch(serverURL + "/bike", {
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

  const res = await customFetch(serverURL + "/get/user", {
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
  const res = await customFetch(serverURL + "/bike/reservation", {
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
  const res = await customFetch(serverURL + "/admin/register", {
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
  const res = await customFetch(serverURL, {
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



export const putUserPW = async (username: string) => {
  const accessToken = Cookies.get('accessToken');
  return await customFetch(serverURL + "/admin/resetPassword", {
    method: "POST",
    credentials: "include",
    headers: {
      "Authorization": `Bearer ${accessToken}`,
      "Content-Type": "application/json",
    },
    body: username,
  });
};

export const changePW = async (oldPassword: string, newPassword: string) => {
  const accessToken = Cookies.get('accessToken');
  return await customFetch(serverURL + "/changePw", {
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
  const res = await customFetch(serverURL + "/admin/delete", {
    method: "DELETE",
    credentials: "include",
    headers: {
      "Authorization": `Bearer ${accessToken}`,
      "Content-Type": "application/json",
    },
    body: username,
  });

  return res;
};

export const putCancelBook = async (bikeId: number) => {
  const accessToken = Cookies.get('accessToken');
  const res = await customFetch(serverURL + "/admin/reservation/cancel", {
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
  const res = await customFetch(serverURL + "/admin/reservation/disabled", {
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

  if (accessToken != null) {

    const res = await customFetch(serverURL + "/auth/user", {
      method: "GET",
      credentials: "include",
      headers: {
        "Authorization": `Bearer ${accessToken}`,
        "Content-Type": "application/json",
      },
    });


    return res.status === 200;
  } else return false;
};

export const getCheckAdmin = async () => {
  const accessToken = Cookies.get('accessToken');

  if (accessToken != null) {
    const res = await customFetch(serverURL + "/auth/admin", {
      method: "GET",
      credentials: "include",
      headers: {
        "Authorization": `Bearer ${accessToken}`,
        "Content-Type": "application/json",
      },
    });

    return res.status === 200;
  } else return false;

};

export const getIUser = async () => {
  const accessToken = Cookies.get('accessToken');
  const res = await customFetch(serverURL + `/admin/member/view`, {
    method: "GET",
    credentials: "include",
    headers: {
      "Authorization": `Bearer ${accessToken}`,
      "Content-Type": "application/json",
    },
  });

  return res;
};

export const getBikesInfoClone = async () => {
  const accessToken = Cookies.get('accessToken');
  const res = await customFetch(serverURL + `/admin/get/bike/cache`, {
    method: "GET",
    credentials: "include",
    headers: {
      "Authorization": `Bearer ${accessToken}`,
      "Content-Type": "application/json",
    },
  });

  return res;
}


