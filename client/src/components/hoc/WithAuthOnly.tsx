"use client";

import React, { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import { getCheckAuth } from "@/routes/route";

const WithAuthOnly = (WrappedComponent: React.ComponentType<any>) => {
  const AuthComponent: React.ComponentType<any> = (props) => {
    const [isAuth, setIsAuth] = useState(false);
    const router = useRouter();

    useEffect(() => {
      const checkAuth = async () => {
        const isAuthConfirmed = await getCheckAuth();
        if (!isAuthConfirmed) {
          router.push("/login");
        } else {
          setIsAuth(true);
        }
      };

      checkAuth();
      // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    if (!isAuth) {
      return null;
    }

    return <WrappedComponent {...props} />;
  };

  AuthComponent.displayName = `WithAuthOnly(${getDisplayName(
    WrappedComponent
  )})`;

  return AuthComponent;
};

function getDisplayName(WrappedComponent: React.ComponentType<any>) {
  return WrappedComponent.displayName || WrappedComponent.name || "Component";
}

export default WithAuthOnly;
