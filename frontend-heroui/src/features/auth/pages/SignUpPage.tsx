import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { Card, Input, Button } from '@heroui/react';
import { useAuth } from '../../../hooks/useAuth';

export default function SignUpPage(): React.ReactElement {
    const navigate = useNavigate();
    const { register, error, isLoading, clearError } = useAuth();
    const [formData, setFormData] = useState({ name: '', email: '', password: '', confirmPassword: '' });
    const [errors, setErrors] = useState<Record<string, string>>({});

    const handleSubmit = async (e: React.FormEvent): Promise<void> => {
        e.preventDefault();
        clearError();
        setErrors({});

        const newErrors: Record<string, string> = {};
        if (!formData.name) newErrors.name = 'Name is required';
        if (!formData.email) newErrors.email = 'Email is required';
        if (!formData.password) newErrors.password = 'Password is required';
        if (formData.password.length < 8) newErrors.password = 'Password must be at least 8 characters';
        if (formData.password !== formData.confirmPassword) newErrors.confirmPassword = 'Passwords do not match';

        if (Object.keys(newErrors).length > 0) {
            setErrors(newErrors);
            return;
        }

        try {
            await register(formData.email, formData.password, formData.name);
            navigate('/');
        } catch {
            // Error handled by hook
        }
    };

    return (
        <div className="flex min-h-screen items-center justify-center bg-gradient-to-br from-blue-50 to-indigo-100 dark:from-gray-900 dark:to-gray-800 p-4">
            <Card className="w-full max-w-md shadow-xl">
                <div className="p-8">
                    <h1 className="mb-2 text-3xl font-bold text-gray-900 dark:text-white">Create Account</h1>
                    <p className="mb-6 text-gray-700 dark:text-gray-300">Join our community of recipe enthusiasts</p>

                    <form onSubmit={handleSubmit} className="space-y-4">
                        {error && <div className="rounded-lg bg-red-100 p-3 text-sm font-medium text-red-900 dark:bg-red-900/30 dark:text-red-200">{error}</div>}

                        <div>
                            <Input
                                type="text"
                                label="Full Name"
                                placeholder="John Doe"
                                value={formData.name}
                                onChange={(e) => setFormData((prev) => ({ ...prev, name: e.target.value }))}
                                isInvalid={!!errors.name}
                                errorMessage={errors.name}
                                required
                            />
                        </div>

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
                                placeholder="At least 8 characters"
                                value={formData.password}
                                onChange={(e) => setFormData((prev) => ({ ...prev, password: e.target.value }))}
                                isInvalid={!!errors.password}
                                errorMessage={errors.password}
                                required
                            />
                        </div>

                        <div>
                            <Input
                                type="password"
                                label="Confirm Password"
                                placeholder="Re-enter your password"
                                value={formData.confirmPassword}
                                onChange={(e) => setFormData((prev) => ({ ...prev, confirmPassword: e.target.value }))}
                                isInvalid={!!errors.confirmPassword}
                                errorMessage={errors.confirmPassword}
                                required
                            />
                        </div>

                        <Button type="submit" fullWidth color="primary" size="lg" isLoading={isLoading}>
                            Create Account
                        </Button>
                    </form>

                    <p className="mt-6 text-center text-sm text-gray-700 dark:text-gray-300">
                        Already have an account?{' '}
                        <Link to="/auth/signin" className="font-semibold text-blue-600 dark:text-blue-400 hover:underline">
                            Sign In
                        </Link>
                    </p>
                </div>
            </Card>
        </div>
    );
}
