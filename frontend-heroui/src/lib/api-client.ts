import type { Recipe, ShoppingList, User, Cuisine } from '../types';

const API_BASE_URL = (import.meta as any).env.VITE_API_BASE_URL || 'http://localhost:8080/api';

class APIClient {
    private baseURL: string;
    private headers: Record<string, string>;

    constructor() {
        this.baseURL = API_BASE_URL;
        this.headers = {
            'Content-Type': 'application/json',
        };
    }

    private getAuthToken(): string | null {
        return localStorage.getItem('accessToken');
    }

    private async request<T>(
        endpoint: string,
        options: RequestInit = {}
    ): Promise<T> {
        const token = this.getAuthToken();
        const headers: Record<string, string> = {
            ...this.headers,
            ...(options.headers as Record<string, string>),
        };

        if (token) {
            headers.Authorization = `Bearer ${token}`;
        }

        try {
            const response = await fetch(`${this.baseURL}${endpoint}`, {
                ...options,
                headers,
            });

            if (!response.ok) {
                if (response.status === 401) {
                    localStorage.removeItem('accessToken');
                    localStorage.removeItem('user');
                    window.location.href = '/auth/signin';
                }

                // Try to parse error response
                let errorMessage = `API error: ${response.statusText}`;
                try {
                    const errorData = await response.json();
                    errorMessage = errorData.message || errorData.error || errorMessage;
                } catch {
                    // Use default error message if JSON parsing fails
                }
                throw new Error(errorMessage);
            }

            return response.json() as Promise<T>;
        } catch (error) {
            // Network error or JSON parsing error
            if (error instanceof TypeError && error.message.includes('fetch')) {
                throw new Error('Backend server is not running. Please start the server on http://localhost:8080');
            }
            throw error;
        }
    }

    // Auth endpoints
    async login(email: string, password: string): Promise<{ token: string; user: User }> {
        return this.request('/auth/login', {
            method: 'POST',
            body: JSON.stringify({ email, password }),
        });
    }

    async register(email: string, password: string, name: string): Promise<{ token: string; user: User }> {
        return this.request('/auth/register', {
            method: 'POST',
            body: JSON.stringify({ email, password, name }),
        });
    }

    async getCurrentUser(): Promise<User> {
        return this.request('/auth/me', { method: 'GET' });
    }

    // Recipe endpoints
    async getRecipes(page: number = 1, limit: number = 10): Promise<{ recipes: Recipe[]; total: number }> {
        return this.request(`/recipes?page=${page}&limit=${limit}`, { method: 'GET' });
    }

    async getRecipe(id: string): Promise<Recipe> {
        return this.request(`/recipes/${id}`, { method: 'GET' });
    }

    async createRecipe(recipe: Omit<Recipe, 'id' | 'createdAt' | 'updatedAt'>): Promise<Recipe> {
        return this.request('/recipes', {
            method: 'POST',
            body: JSON.stringify(recipe),
        });
    }

    async updateRecipe(id: string, recipe: Partial<Recipe>): Promise<Recipe> {
        return this.request(`/recipes/${id}`, {
            method: 'PUT',
            body: JSON.stringify(recipe),
        });
    }

    async deleteRecipe(id: string): Promise<void> {
        await this.request(`/recipes/${id}`, { method: 'DELETE' });
    }

    // Shopping list endpoints
    async getShoppingLists(): Promise<ShoppingList[]> {
        return this.request('/shopping-lists', { method: 'GET' });
    }

    async getShoppingList(id: string): Promise<ShoppingList> {
        return this.request(`/shopping-lists/${id}`, { method: 'GET' });
    }

    async createShoppingList(name: string): Promise<ShoppingList> {
        return this.request('/shopping-lists', {
            method: 'POST',
            body: JSON.stringify({ name }),
        });
    }

    async deleteShoppingList(id: string): Promise<void> {
        await this.request(`/shopping-lists/${id}`, { method: 'DELETE' });
    }

    // Cuisines
    async getCuisines(): Promise<Cuisine[]> {
        return this.request('/cuisines', { method: 'GET' });
    }

    // Health check
    async checkBackendHealth(): Promise<boolean> {
        try {
            const response = await fetch(`${this.baseURL.replace('/api', '')}/health`, {
                method: 'GET',
            });
            return response.ok;
        } catch {
            return false;
        }
    }
}

export const apiClient = new APIClient();
