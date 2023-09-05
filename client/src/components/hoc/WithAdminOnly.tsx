import React, { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import { getCheckAdmin } from "@/routes/route";

const WithAdminOnly = (WrappedComponent: React.ComponentType<any>) => {
  const AdminComponent: React.ComponentType<any> = (props) => {
    const [isAdmin, setIsAdmin] = useState(false);
    const router = useRouter();

    useEffect(() => {
      const checkAdmin = async () => {
        const isAdminConfirmed = await getCheckAdmin();
        if (!isAdminConfirmed) {
          router.push("/reservation");
        } else {
          setIsAdmin(true);
        }
      };

      checkAdmin();
      // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    if (!isAdmin) {
      return null;
    }

    return <WrappedComponent {...props} />;
  };

  AdminComponent.displayName = `WithAdminOnly(${getDisplayName(
    WrappedComponent
  )})`;

  return AdminComponent;
};

function getDisplayName(WrappedComponent: React.ComponentType<any>) {
  return WrappedComponent.displayName || WrappedComponent.name || "Component";
}

export default WithAdminOnly;
