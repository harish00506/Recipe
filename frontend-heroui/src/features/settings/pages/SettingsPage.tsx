import React, { useState } from 'react';
import { Card, CardBody, CardHeader, Input, Button, Tabs, Tab, Switch, Select, SelectItem } from '@heroui/react';
import { useAuth } from '../../../hooks/useAuth';

export default function SettingsPage(): React.ReactElement {
    const { user } = useAuth();
    const [profileData, setProfileData] = useState({
        name: user?.name || '',
        email: user?.email || '',
    });
    const [preferences, setPreferences] = useState({
        emailNotifications: true,
        pushNotifications: false,
        darkMode: localStorage.getItem('theme') === 'dark',
        language: 'en',
    });

    const handleProfileSave = (): void => {
        // TODO: Save profile data to API
        alert('Profile updated! (not connected to API yet)');
    };

    const handlePreferenceChange = (key: string, value: unknown): void => {
        setPreferences((prev) => ({ ...prev, [key]: value }));
    };

    const handlePreferenceSave = (): void => {
        if (preferences.darkMode) {
            document.documentElement.classList.add('dark');
            localStorage.setItem('theme', 'dark');
        } else {
            document.documentElement.classList.remove('dark');
            localStorage.setItem('theme', 'light');
        }
        alert('Preferences saved!');
    };

    return (
        <div className="space-y-8">
            {/* Header */}
            <div>
                <h1 className="text-4xl font-bold">Settings</h1>
                <p className="mt-2 text-gray-600 dark:text-gray-400">Manage your profile and preferences</p>
            </div>

            {/* Tabs */}
            <Tabs aria-label="Settings options" color="primary">
                {/* Profile Tab */}
                <Tab key="profile" title="Profile">
                    <Card>
                        <CardHeader>
                            <h3 className="text-xl font-bold">Profile Information</h3>
                        </CardHeader>
                        <CardBody className="space-y-6">
                            <div>
                                <Input type="text" label="Full Name" value={profileData.name} onChange={(e) => setProfileData((prev) => ({ ...prev, name: e.target.value }))} />
                            </div>

                            <div>
                                <Input type="email" label="Email Address" value={profileData.email} isDisabled />
                            </div>

                            <div>
                                <Input type="password" label="Change Password" placeholder="Enter new password" />
                            </div>

                            <Button color="primary" onPress={handleProfileSave}>
                                Save Changes
                            </Button>
                        </CardBody>
                    </Card>
                </Tab>

                {/* Preferences Tab */}
                <Tab key="preferences" title="Preferences">
                    <Card>
                        <CardHeader>
                            <h3 className="text-xl font-bold">Preferences</h3>
                        </CardHeader>
                        <CardBody className="space-y-6">
                            <div className="flex items-center justify-between">
                                <span>Email Notifications</span>
                                <Switch isSelected={preferences.emailNotifications} onChange={(e) => handlePreferenceChange('emailNotifications', e.target.checked)} />
                            </div>

                            <div className="flex items-center justify-between">
                                <span>Push Notifications</span>
                                <Switch isSelected={preferences.pushNotifications} onChange={(e) => handlePreferenceChange('pushNotifications', e.target.checked)} />
                            </div>

                            <div className="flex items-center justify-between">
                                <span>Dark Mode</span>
                                <Switch isSelected={preferences.darkMode} onChange={(e) => handlePreferenceChange('darkMode', e.target.checked)} />
                            </div>

                            <div>
                                <Select label="Language" selectedKeys={[preferences.language]} onChange={(e) => handlePreferenceChange('language', e.target.value)}>
                                    <SelectItem key="en">English</SelectItem>
                                    <SelectItem key="es">Español</SelectItem>
                                    <SelectItem key="fr">Français</SelectItem>
                                    <SelectItem key="de">Deutsch</SelectItem>
                                </Select>
                            </div>

                            <Button color="primary" onPress={handlePreferenceSave}>
                                Save Preferences
                            </Button>
                        </CardBody>
                    </Card>
                </Tab>

                {/* Theme Tab */}
                <Tab key="theme" title="Theme">
                    <Card>
                        <CardHeader>
                            <h3 className="text-xl font-bold">Theme Settings</h3>
                        </CardHeader>
                        <CardBody className="space-y-6">
                            <div className="grid gap-4 md:grid-cols-2">
                                <div className="rounded-lg border-2 border-primary p-4">
                                    <h4 className="font-semibold">Light Theme</h4>
                                    <p className="mt-2 text-sm text-gray-600">Bright and clean interface</p>
                                    <Button size="sm" className="mt-4" color="primary">
                                        Apply
                                    </Button>
                                </div>

                                <div className="rounded-lg border border-gray-300 p-4 dark:border-gray-600">
                                    <h4 className="font-semibold">Dark Theme</h4>
                                    <p className="mt-2 text-sm text-gray-600 dark:text-gray-400">Comfortable for low light</p>
                                    <Button size="sm" className="mt-4" variant="bordered">
                                        Apply
                                    </Button>
                                </div>
                            </div>
                        </CardBody>
                    </Card>
                </Tab>
            </Tabs>
        </div>
    );
}
