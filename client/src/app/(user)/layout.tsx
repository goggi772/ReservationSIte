import LogoutBtn from "@/components/LogoutBtn";

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
