import axios, { AxiosInstance, AxiosError } from 'axios';
import { AuthResponse, User } from '@types';

const API_BASE_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080';

class ApiClient {
    private client: AxiosInstance;
    private token: string | null = null;

    constructor() {
        this.client = axios.create({
            baseURL: API_BASE_URL,
            headers: {
                'Content-Type': 'application/json',
            },
        });

        // Add request interceptor
        this.client.interceptors.request.use(
            (config) => {
                if (this.token) {
                    config.headers.Authorization = `Bearer ${this.token}`;
                }
                return config;
            },
            (error) => Promise.reject(error)
        );

        // Add response interceptor
        this.client.interceptors.response.use(
            (response) => response,
            (error: AxiosError) => {
                if (error.response?.status === 401) {
                    // Handle unauthorized - redirect to login
                    window.location.href = '/login';
                }
                return Promise.reject(error);
            }
        );

        // Load token from localStorage
        this.loadToken();
    }

    private loadToken(): void {
        const stored = localStorage.getItem('accessToken');
        if (stored) {
            this.token = stored;
        }
    }

    public setToken(token: string): void {
        this.token = token;
        localStorage.setItem('accessToken', token);
    }

    public clearToken(): void {
        this.token = null;
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');
    }

    // Auth endpoints
    public async register(email: string, password: string, firstName: string, lastName: string): Promise<User> {
        const response = await this.client.post('/auth/register', {
            email,
            password,
            firstName,
            lastName,
        });
        return response.data;
    }

    public async login(email: string, password: string): Promise<AuthResponse> {
        const response = await this.client.post('/auth/login', { email, password });
        const data = response.data as AuthResponse;
        this.setToken(data.accessToken);
        localStorage.setItem('refreshToken', data.refreshToken);
        return data;
    }

    public async refreshToken(refreshToken: string): Promise<AuthResponse> {
        const response = await this.client.post('/auth/refresh', { refreshToken });
        const data = response.data as AuthResponse;
        this.setToken(data.accessToken);
        return data;
    }

    public async validateToken(): Promise<{ valid: boolean }> {
        const response = await this.client.post('/auth/validate', {});
        return response.data;
    }

    // Recipe endpoints
    public async getRecipes(page = 0, size = 10) {
        const response = await this.client.get('/recipes', {
            params: { page, size },
        });
        return response.data;
    }

    public async getRecipeById(id: string) {
        const response = await this.client.get(`/recipes/${id}`);
        return response.data;
    }

    public async createRecipe(recipeData: any) {
        const response = await this.client.post('/recipes', recipeData);
        return response.data;
    }

    public async updateRecipe(id: string, recipeData: any) {
        const response = await this.client.put(`/recipes/${id}`, recipeData);
        return response.data;
    }

    public async deleteRecipe(id: string) {
        await this.client.delete(`/recipes/${id}`);
    }

    public async searchRecipes(query: string, page = 0, size = 10) {
        const response = await this.client.get('/recipes/search', {
            params: { q: query, page, size },
        });
        return response.data;
    }

    public async getRandomRecipe() {
        const response = await this.client.get('/recipes/random');
        return response.data;
    }

    // Cuisine endpoints
    public async getCuisines() {
        const response = await this.client.get('/cuisines');
        return response.data;
    }

    public async getCuisineById(id: string) {
        const response = await this.client.get(`/cuisines/${id}`);
        return response.data;
    }

    public async createCuisine(name: string, description: string) {
        const response = await this.client.post('/cuisines', { name, description });
        return response.data;
    }

    // Ingredient endpoints
    public async getIngredients(page = 0, size = 20) {
        const response = await this.client.get('/ingredients', {
            params: { page, size },
        });
        return response.data;
    }

    public async searchIngredients(query: string) {
        const response = await this.client.get('/ingredients/search', {
            params: { q: query },
        });
        return response.data;
    }

    // Shopping List endpoints
    public async getShoppingLists() {
        const response = await this.client.get('/shopping-lists');
        return response.data;
    }

    public async getShoppingListById(id: string) {
        const response = await this.client.get(`/shopping-lists/${id}`);
        return response.data;
    }

    public async createShoppingList(name: string) {
        const response = await this.client.post('/shopping-lists', { name });
        return response.data;
    }

    public async updateShoppingList(id: string, name: string) {
        const response = await this.client.put(`/shopping-lists/${id}`, { name });
        return response.data;
    }

    public async deleteShoppingList(id: string) {
        await this.client.delete(`/shopping-lists/${id}`);
    }

    public async addShoppingListItem(listId: string, itemData: any) {
        const response = await this.client.post(`/shopping-lists/${listId}/items`, itemData);
        return response.data;
    }

    public async updateShoppingListItem(itemId: string, itemData: any) {
        const response = await this.client.put(`/shopping-lists/items/${itemId}`, itemData);
        return response.data;
    }

    public async deleteShoppingListItem(itemId: string) {
        await this.client.delete(`/shopping-lists/items/${itemId}`);
    }

    public async toggleShoppingListItemPurchased(itemId: string) {
        const response = await this.client.post(`/shopping-lists/items/${itemId}/toggle`);
        return response.data;
    }
}

export const apiClient = new ApiClient();
export default apiClient;
