import React from 'react';
import { Navbar, NavbarBrand, NavbarContent, NavbarItem, Button, Avatar, Dropdown, DropdownTrigger, DropdownMenu, DropdownItem } from '@heroui/react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../hooks/useAuth';
import { Sun, Moon } from 'lucide-react';

interface AppNavbarProps {
    theme: 'light' | 'dark';
    onThemeChange: (theme: 'light' | 'dark') => void;
}

export function AppNavbar({ theme, onThemeChange }: AppNavbarProps): React.ReactElement {
    const navigate = useNavigate();
    const { user, logout } = useAuth();

    const handleLogout = (): void => {
        logout();
        navigate('/auth/signin');
    };

    return (
        <Navbar isBordered maxWidth="full">
            <NavbarBrand>
                <p className="font-bold text-inherit">🍳 Recipe App</p>
            </NavbarBrand>
            <NavbarContent className="hidden sm:flex gap-4" justify="center">
                <NavbarItem>
                    <Button
                        isIconOnly
                        className="text-default-900"
                        radius="full"
                        variant="light"
                        onPress={() => onThemeChange(theme === 'light' ? 'dark' : 'light')}
                    >
                        {theme === 'light' ? <Moon size={20} /> : <Sun size={20} />}
                    </Button>
                </NavbarItem>
            </NavbarContent>
            <NavbarContent justify="end">
                {user && (
                    <Dropdown placement="bottom-end">
                        <DropdownTrigger>
                            <Avatar isBordered as="button" className="transition-transform" color="secondary" name={user.name} size="sm" src={user.avatar} />
                        </DropdownTrigger>
                        <DropdownMenu aria-label="Profile Actions" variant="flat">
                            <DropdownItem key="profile" className="h-14 gap-2">
                                <p className="font-semibold">{user.email}</p>
                            </DropdownItem>
                            <DropdownItem key="settings" onPress={() => navigate('/settings')}>
                                Settings
                            </DropdownItem>
                            <DropdownItem key="logout" color="danger" onPress={handleLogout}>
                                Log Out
                            </DropdownItem>
                        </DropdownMenu>
                    </Dropdown>
                )}
            </NavbarContent>
        </Navbar>
    );
}
