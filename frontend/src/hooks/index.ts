import { useState, useCallback } from 'react';
import { useAuthStore } from '@store';
import apiClient from '@services/apiClient';

export const useAuth = () => {
  const store = useAuthStore();
  const [error, setError] = useState<string | null>(null);

  const register = useCallback(
    async (email: string, password: string, firstName: string, lastName: string) => {
      try {
        store.setLoading(true);
        setError(null);
        const user = await apiClient.register(email, password, firstName, lastName);
        store.setUser(user);
        return user;
      } catch (err: any) {
        const errorMessage = err.response?.data?.message || 'Registration failed';
        setError(errorMessage);
        store.setError(errorMessage);
        throw err;
      } finally {
        store.setLoading(false);
      }
    },
    [store]
  );

  const login = useCallback(
    async (email: string, password: string) => {
      try {
        store.setLoading(true);
        setError(null);
        const response = await apiClient.login(email, password);
        store.setToken(response.accessToken);
        return response;
      } catch (err: any) {
        const errorMessage = err.response?.data?.message || 'Login failed';
        setError(errorMessage);
        store.setError(errorMessage);
        throw err;
      } finally {
        store.setLoading(false);
      }
    },
    [store]
  );

  const logout = useCallback(() => {
    store.logout();
    apiClient.clearToken();
    setError(null);
  }, [store]);

  return {
    user: store.user,
    token: store.token,
    isAuthenticated: store.isAuthenticated,
    isLoading: store.isLoading,
    error: error || store.error,
    register,
    login,
    logout,
  };
};

export const useFetch = <T,>(url: string, options?: any) => {
  const [data, setData] = useState<T | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const fetch = useCallback(async () => {
    try {
      setLoading(true);
      setError(null);
      const response = await apiClient.client.get<T>(url, options);
      setData(response.data);
    } catch (err: any) {
      const errorMessage = err.response?.data?.message || 'Fetch failed';
      setError(errorMessage);
    } finally {
      setLoading(false);
    }
  }, [url, options]);

  return {
    data,
    loading,
    error,
    fetch,
  };
};

export const useAsync = <T, E = string>(
  asyncFunction: () => Promise<T>,
  immediate = true
) => {
  const [status, setStatus] = useState<'idle' | 'pending' | 'success' | 'error'>('idle');
  const [value, setValue] = useState<T | null>(null);
  const [error, setError] = useState<E | null>(null);

  const execute = useCallback(async () => {
    setStatus('pending');
    setValue(null);
    setError(null);
    try {
      const response = await asyncFunction();
      setValue(response);
      setStatus('success');
      return response;
    } catch (error) {
      setError(error as E);
      setStatus('error');
      throw error;
    }
  }, [asyncFunction]);

  return { execute, status, value, error };
};
