import { useCallback, useState } from 'react';
import type { User } from '../types';
import { apiClient } from '../lib/api-client';

interface UseAuthReturn {
    user: User | null;
    isAuthenticated: boolean;
    isLoading: boolean;
    error: string | null;
    login: (email: string, password: string) => Promise<void>;
    register: (email: string, password: string, name: string) => Promise<void>;
    logout: () => void;
    clearError: () => void;
}

export function useAuth(): UseAuthReturn {
    const [user, setUser] = useState<User | null>(() => {
        const token = localStorage.getItem('accessToken');
        return token ? (JSON.parse(localStorage.getItem('user') || 'null') as User | null) : null;
    });
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const login = useCallback(async (email: string, password: string) => {
        setIsLoading(true);
        setError(null);
        try {
            const { token, user: userData } = await apiClient.login(email, password);
            localStorage.setItem('accessToken', token);
            localStorage.setItem('user', JSON.stringify(userData));
            setUser(userData);
        } catch (err) {
            setError(err instanceof Error ? err.message : 'Login failed');
            throw err;
        } finally {
            setIsLoading(false);
        }
    }, []);

    const register = useCallback(async (email: string, password: string, name: string) => {
        setIsLoading(true);
        setError(null);
        try {
            const { token, user: userData } = await apiClient.register(email, password, name);
            localStorage.setItem('accessToken', token);
            localStorage.setItem('user', JSON.stringify(userData));
            setUser(userData);
        } catch (err) {
            setError(err instanceof Error ? err.message : 'Registration failed');
            throw err;
        } finally {
            setIsLoading(false);
        }
    }, []);

    const logout = useCallback(() => {
        localStorage.removeItem('accessToken');
        localStorage.removeItem('user');
        setUser(null);
    }, []);

    const clearError = useCallback(() => {
        setError(null);
    }, []);

    return {
        user,
        isAuthenticated: !!user,
        isLoading,
        error,
        login,
        register,
        logout,
        clearError,
    };
}
