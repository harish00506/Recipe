import react from '@vitejs/plugin-react';
import { fileURLToPath } from 'url';
import { dirname, resolve } from 'path';
import { defineConfig } from 'vite';

const __filename = fileURLToPath(import.meta.url);
const __dirname = dirname(__filename);

export default defineConfig({
    plugins: [react()],
    resolve: {
        alias: {
            '@': resolve(__dirname, './src'),
            '@components': resolve(__dirname, './src/components'),
            '@features': resolve(__dirname, './src/features'),
            '@hooks': resolve(__dirname, './src/hooks'),
            '@lib': resolve(__dirname, './src/lib'),
            '@routes': resolve(__dirname, './src/routes'),
            '@styles': resolve(__dirname, './src/styles'),
            '@types': resolve(__dirname, './src/types'),
        },
    },
    server: {
        port: 3000,
        strictPort: false,
        open: true,
    },
    build: {
        target: 'ES2020',
        minify: 'terser',
        sourcemap: false,
        rollupOptions: {
            output: {
                manualChunks: {
                    'vendor': ['react', 'react-dom', 'react-router-dom'],
                    'heroui': ['@heroui/react', 'framer-motion'],
                    'form': ['react-hook-form', '@hookform/resolvers', 'zod'],
                },
            },
        },
    },
});
