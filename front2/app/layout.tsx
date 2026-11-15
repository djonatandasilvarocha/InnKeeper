
import type { Metadata } from "next";
import { Geist, Geist_Mono } from "next/font/google";
import "./globals.css";
import StoreProvider from "./redux/StoreProvider";

const geistSans = Geist({
    variable: "--font-geist-sans",
    subsets: ["latin"],
});

const geistMono = Geist_Mono({
    variable: "--font-geist-mono",
    subsets: ["latin"],
});

export const metadata: Metadata = {
    title: "InnKeeper | Gestão de Pousadas",
    description:
        "InnKeeper é uma plataforma desenvolvida para facilitar e transformar a gestão de pousadas, permitindo administrar hóspedes, quartos e reservas.",
};

export default function RootLayout({
    children,
}: Readonly<{
    children: React.ReactNode;
}>) {
    return (
        <html lang="pt-BR" className="h-full">
            <body
                className={`${geistSans.variable} ${geistMono.variable} antialiased min-h-full`}
            >
                <StoreProvider>
                    {children}
                </StoreProvider>
            </body>
        </html>
    );
}
