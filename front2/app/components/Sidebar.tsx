"use client";

import Link from "next/link";
import { usePathname } from "next/navigation";

export default function Sidebar() {
  const pathname = usePathname();

  const menuItems = [
    { name: "Home", href: "/home" },
    { name: "Usuários", href: "/usuarios" },
    { name: "Hóspedes", href: "/hospede" },
    { name: "Quartos", href: "/quarto" },
    { name: "Reservas", href: "/reserva" },
  ];

  return (
    <aside className="w-64 min-h-screen bg-[#030712] border-r border-slate-800/80 p-6 flex flex-col gap-8 shrink-0">
      <div className="text-xs font-semibold tracking-wider text-slate-400 uppercase leading-relaxed border-b border-slate-800 pb-4">
        Sistema de Gerenciamento de Pousadas 2026
      </div>

      <nav className="flex flex-col gap-1.5">
        {menuItems.map((item) => {
          // Destaca a opção se a URL atual começar com o caminho correspondente
          const isActive = pathname.startsWith(item.href);

          return (
            <Link
              key={item.href}
              href={item.href}
              className={`flex items-center px-4 py-3 text-sm font-medium rounded-lg transition-all duration-200 ${
                isActive
                  ? "text-teal-400 bg-slate-800/80 border border-teal-500/20 shadow-sm"
                  : "text-slate-300 hover:bg-slate-800/60 hover:text-teal-400"
              }`}
            >
              {item.name}
            </Link>
          );
        })}
      </nav>
    </aside>
  );
}