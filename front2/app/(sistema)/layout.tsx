import Sidebar from "../components/Sidebar";
import Header from "../components/Header"
import Footer from "../components/Footer";

export default function SistemaLayout({children}){
    return (
        <div className="flex min-h-screen bg-[#030712] text-slate-100">
          {<Sidebar />}
      
          <div className="flex-1 flex flex-col min-w-0">
            <Header />
      
            <main className="flex-1 w-full max-w-7xl mx-auto p-6 md:p-8">
              {children}
            </main>
      
            {<Footer />}
          </div>
        </div>
      );
}