import { create } from 'zustand';
import { immer } from 'zustand/middleware/immer';
import { AuthResponse, User } from '@types';

interface AuthState {
    user: User | null;
    token: string | null;
    isAuthenticated: boolean;
    isLoading: boolean;
    error: string | null;

    setUser: (user: User) => void;
    setToken: (token: string) => void;
    setLoading: (loading: boolean) => void;
    setError: (error: string | null) => void;
    logout: () => void;
    reset: () => void;
}

export const useAuthStore = create<AuthState>()(
    immer((set) => ({
        user: null,
        token: localStorage.getItem('accessToken'),
        isAuthenticated: !!localStorage.getItem('accessToken'),
        isLoading: false,
        error: null,

        setUser: (user) => set({ user, isAuthenticated: true }),
        setToken: (token) => set({ token, isAuthenticated: true }),
        setLoading: (isLoading) => set({ isLoading }),
        setError: (error) => set({ error }),

        logout: () => {
            set({
                user: null,
                token: null,
                isAuthenticated: false,
                error: null,
            });
            localStorage.removeItem('accessToken');
            localStorage.removeItem('refreshToken');
        },

        reset: () => set({
            user: null,
            token: null,
            isAuthenticated: false,
            isLoading: false,
            error: null,
        }),
    }))
);

interface RecipeState {
    recipes: any[];
    selectedRecipe: any | null;
    isLoading: boolean;
    error: string | null;

    setRecipes: (recipes: any[]) => void;
    setSelectedRecipe: (recipe: any) => void;
    setLoading: (loading: boolean) => void;
    setError: (error: string | null) => void;
    addRecipe: (recipe: any) => void;
    removeRecipe: (id: string) => void;
}

export const useRecipeStore = create<RecipeState>()(
    immer((set) => ({
        recipes: [],
        selectedRecipe: null,
        isLoading: false,
        error: null,

        setRecipes: (recipes) => set({ recipes }),
        setSelectedRecipe: (selectedRecipe) => set({ selectedRecipe }),
        setLoading: (isLoading) => set({ isLoading }),
        setError: (error) => set({ error }),

        addRecipe: (recipe) => set((state) => {
            state.recipes.push(recipe);
        }),

        removeRecipe: (id) => set((state) => {
            state.recipes = state.recipes.filter((r) => r.id !== id);
        }),
    }))
);

interface ShoppingListState {
    shoppingLists: any[];
    selectedList: any | null;
    isLoading: boolean;
    error: string | null;

    setShoppingLists: (lists: any[]) => void;
    setSelectedList: (list: any) => void;
    setLoading: (loading: boolean) => void;
    setError: (error: string | null) => void;
    addShoppingList: (list: any) => void;
    removeShoppingList: (id: string) => void;
}

export const useShoppingListStore = create<ShoppingListState>()(
    immer((set) => ({
        shoppingLists: [],
        selectedList: null,
        isLoading: false,
        error: null,

        setShoppingLists: (shoppingLists) => set({ shoppingLists }),
        setSelectedList: (selectedList) => set({ selectedList }),
        setLoading: (isLoading) => set({ isLoading }),
        setError: (error) => set({ error }),

        addShoppingList: (list) => set((state) => {
            state.shoppingLists.push(list);
        }),

        removeShoppingList: (id) => set((state) => {
            state.shoppingLists = state.shoppingLists.filter((l) => l.id !== id);
        }),
    }))
);
