export interface User {
    id: string;
    email: string;
    name: string;
    avatar?: string;
    role: 'user' | 'admin';
}

export interface AuthState {
    user: User | null;
    isAuthenticated: boolean;
    isLoading: boolean;
    error: string | null;
}

export interface Recipe {
    id: string;
    name: string;
    description: string;
    cuisine: string;
    prepTime: number;
    cookTime: number;
    servings: number;
    ingredients: Ingredient[];
    instructions: string;
    imageUrl?: string;
    createdAt: string;
    updatedAt: string;
}

export interface Ingredient {
    id: string;
    name: string;
    quantity: number;
    unit: string;
}

export interface ShoppingList {
    id: string;
    name: string;
    items: ShoppingItem[];
    createdAt: string;
    updatedAt: string;
}

export interface ShoppingItem {
    id: string;
    name: string;
    quantity: number;
    unit: string;
    completed: boolean;
}

export interface Cuisine {
    id: string;
    name: string;
    description?: string;
}
