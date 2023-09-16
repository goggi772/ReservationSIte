import SignupBtn from "@/components/SignupBtn";
import AdminHomeBtn from "@/components/AdminHomeBtn";
import LogoutBtn from "@/components/LogoutBtn";
import UserInfoBtn from "@/components/UserInfoBtn";

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