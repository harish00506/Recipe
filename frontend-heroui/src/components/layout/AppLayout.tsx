import React from 'react';
import { Outlet } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { AppNavbar } from './AppNavbar';

export default function AppLayout(): React.ReactElement {
    const [theme, setTheme] = useState<'light' | 'dark'>(() => {
        const saved = localStorage.getItem('theme') as 'light' | 'dark' | null;
        return saved || 'light';
    });

    useEffect(() => {
        localStorage.setItem('theme', theme);
        const html = document.documentElement;
        if (theme === 'dark') {
            html.classList.add('dark');
        } else {
            html.classList.remove('dark');
        }
    }, [theme]);

    return (
        <div className={`min-h-screen ${theme === 'dark' ? 'dark' : ''}`}>
            <AppNavbar theme={theme} onThemeChange={setTheme} />
            <main className="p-4 sm:p-6 lg:p-8">
                <div className="mx-auto max-w-7xl">
                    <Outlet />
                </div>
            </main>
        </div>
    );
}
