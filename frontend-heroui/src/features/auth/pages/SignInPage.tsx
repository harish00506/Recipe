import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { Card, Input, Button } from '@heroui/react';
import { useAuth } from '../../../hooks/useAuth';

export default function SignInPage(): React.ReactElement {
    const navigate = useNavigate();
    const { login, error, isLoading, clearError } = useAuth();
    const [formData, setFormData] = useState({ email: '', password: '' });
    const [errors, setErrors] = useState<Record<string, string>>({});

    const handleSubmit = async (e: React.FormEvent): Promise<void> => {
        e.preventDefault();
        clearError();
        setErrors({});

        if (!formData.email || !formData.password) {
            setErrors({
                email: !formData.email ? 'Email is required' : '',
                password: !formData.password ? 'Password is required' : '',
            });
            return;
        }

        try {
            await login(formData.email, formData.password);
            navigate('/');
        } catch {
            // Error handled by hook
        }
    };

    return (
        <div className="flex min-h-screen items-center justify-center bg-gradient-to-br from-blue-50 to-indigo-100 dark:from-gray-900 dark:to-gray-800 p-4">
            <Card className="w-full max-w-md shadow-xl">
                <div className="p-8">
                    <h1 className="mb-2 text-3xl font-bold text-gray-900 dark:text-white">Welcome Back</h1>
                    <p className="mb-6 text-gray-700 dark:text-gray-300">Sign in to your recipe app account</p>

                    <form onSubmit={handleSubmit} className="space-y-4">
                        {error && <div className="rounded-lg bg-red-100 p-3 text-sm font-medium text-red-900 dark:bg-red-900/30 dark:text-red-200">{error}</div>}

                        <div>
                            <Input
                                type="email"
                                label="Email"
                                placeholder="you@example.com"
                                value={formData.email}
                                onChange={(e) => setFormData((prev) => ({ ...prev, email: e.target.value }))}
                                isInvalid={!!errors.email}
                                errorMessage={errors.email}
                                required
                            />
                        </div>

                        <div>
                            <Input
                                type="password"
                                label="Password"
                                placeholder="Enter your password"
                                value={formData.password}
                                onChange={(e) => setFormData((prev) => ({ ...prev, password: e.target.value }))}
                                isInvalid={!!errors.password}
                                errorMessage={errors.password}
                                required
                            />
                        </div>

                        <Button type="submit" fullWidth color="primary" size="lg" isLoading={isLoading}>
                            Sign In
                        </Button>
                    </form>

                    <p className="mt-6 text-center text-sm text-gray-700 dark:text-gray-300">
                        Don't have an account?{' '}
                        <Link to="/auth/signup" className="font-semibold text-blue-600 dark:text-blue-400 hover:underline">
                            Sign Up
                        </Link>
                    </p>
                </div>
            </Card>
        </div>
    );
}
