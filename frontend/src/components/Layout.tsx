import React, { useState } from 'react';
import { Outlet } from 'react-router-dom';
import Navigation from '@components/Navigation';
import Sidebar from '@components/Sidebar';

const Layout: React.FC = () => {
    const [sidebarOpen, setSidebarOpen] = useState(false);

    return (
        <div className="flex h-screen bg-gray-100">
            {/* Sidebar */}
            <Sidebar open={sidebarOpen} setOpen={setSidebarOpen} />

            {/* Main Content */}
            <div className="flex-1 flex flex-col overflow-hidden">
                {/* Top Navigation */}
                <Navigation sidebarOpen={sidebarOpen} setSidebarOpen={setSidebarOpen} />

                {/* Page Content */}
                <main className="flex-1 overflow-auto">
                    <div className="mx-auto max-w-7xl px-4 py-6 sm:px-6 md:px-8">
                        <Outlet />
                    </div>
                </main>
            </div>
        </div>
    );
};

export default Layout;
