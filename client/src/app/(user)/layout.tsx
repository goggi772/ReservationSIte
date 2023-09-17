import LogoutBtn from "@/components/button/LogoutBtn";

export default function UserLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <div className="relative">
      <LogoutBtn />
      {children}
    </div>
  );
}
