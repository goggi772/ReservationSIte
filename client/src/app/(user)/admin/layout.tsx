import SignupBtn from "@/components/SignupBtn";
import LogoutBtn from "@/components/LogoutBtn";
import AdminHomeBtn from "@/components/AdminHomeBtn";

export default function UserLayout({
    children,
}: {
    children: React.ReactNode;
}) {
    return (
        <div className="relative">
            <SignupBtn />
            <AdminHomeBtn />
            {children}
        </div>

    );
}