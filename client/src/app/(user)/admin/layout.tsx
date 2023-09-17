import SignupBtn from "@/components/button/SignupBtn";
import AdminHomeBtn from "@/components/button/AdminHomeBtn";
import LogoutBtn from "@/components/button/LogoutBtn";
import UserInfoBtn from "@/components/button/UserInfoBtn";

export default function AdminLayout({
    children,
}: {
    children: React.ReactNode;
}) {
    return (
        <div className="relative">
            <SignupBtn />
            <UserInfoBtn/>
            <AdminHomeBtn />
            <LogoutBtn />
            {children}
        </div>

    );
}