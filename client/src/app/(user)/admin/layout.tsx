import SignupBtn from "@/components/SignupBtn";
import AdminHomeBtn from "@/components/AdminHomeBtn";
import LogoutBtn from "@/components/LogoutBtn";

export default function AdminLayout({
    children,
}: {
    children: React.ReactNode;
}) {
    return (
        <div className="relative">
            <SignupBtn />
            <AdminHomeBtn />
            <LogoutBtn />
            {children}
        </div>

    );
}