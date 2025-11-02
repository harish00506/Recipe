/**
 * Core API Types
 */

export interface User {
  id: string;
  email: string;
  firstName: string;
  lastName: string;
  isActive: boolean;
  createdAt: string;
  updatedAt: string;
}

export interface AuthResponse {
  accessToken: string;
  refreshToken: string;
  expiresIn: number;
  tokenType: string;
}

export interface Cuisine {
  id: string;
  name: string;
  description?: string;
  createdAt: string;
  updatedAt: string;
}

export interface Ingredient {
  id: string;
  name: string;
  category: string;
  nutritionalInfo?: string;
  createdAt: string;
  updatedAt: string;
}

export interface Unit {
  id: string;
  name: string;
  abbreviation: string;
}

export interface RecipeIngredient {
  id: string;
  ingredient: Ingredient;
  quantity: number;
  unit: Unit;
  notes?: string;
}

export interface Recipe {
  id: string;
  title: string;
  description: string;
  instructions: string;
  servings: number;
  cookingTime: number;
  cuisine: Cuisine;
  ingredients: RecipeIngredient[];
  createdAt: string;
  updatedAt: string;
}

export interface ShoppingListItem {
  id: string;
  ingredient: Ingredient;
  quantity: number;
  unit: Unit;
  purchased: boolean;
  createdAt: string;
  updatedAt: string;
}

export interface ShoppingList {
  id: string;
  name: string;
  items: ShoppingListItem[];
  createdAt: string;
  updatedAt: string;
}

export interface PaginatedResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  currentPage: number;
  pageSize: number;
}

export interface ErrorResponse {
  error: string;
  message: string;
  timestamp?: string;
  path?: string;
}
