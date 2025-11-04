// HeroUI theme configuration
// Themes are configured via Tailwind CSS using the heroui() preset in tailwind.config.ts
// Light and dark modes are controlled via the 'dark' class on the HTML element

export const themes = {
    light: {
        colors: {
            primary: '#0ea5e9',
            secondary: '#8b5cf6',
            success: '#10b981',
            warning: '#f59e0b',
            error: '#ef4444',
        },
    },
    dark: {
        colors: {
            primary: '#0ea5e9',
            secondary: '#a78bfa',
            success: '#34d399',
            warning: '#fbbf24',
            error: '#f87171',
        },
    },
} as const;

export type Theme = keyof typeof themes;

