import React, { useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { Toaster } from 'react-hot-toast';
import { useAuthStore } from '@store';
import Layout from '@components/Layout';
import PrivateRoute from '@components/PrivateRoute';
import LoginPage from '@pages/LoginPage';
import RegisterPage from '@pages/RegisterPage';
import HomePage from '@pages/HomePage';
import RecipesPage from '@pages/RecipesPage';
import RecipeDetailPage from '@pages/RecipeDetailPage';
import ShoppingListPage from '@pages/ShoppingListPage';
import '@styles/globals.css';

const App: React.FC = () => {
  const isAuthenticated = useAuthStore((state) => state.isAuthenticated);

  return (
    <Router>
      <div className="min-h-screen bg-gray-50">
        <Toaster
          position="top-right"
          reverseOrder={false}
          gutter={8}
          toastOptions={{
            duration: 4000,
            style: {
              background: '#363636',
              color: '#fff',
            },
          }}
        />

        <Routes>
          {/* Public Routes */}
          <Route path="/login" element={<LoginPage />} />
          <Route path="/register" element={<RegisterPage />} />

          {/* Protected Routes */}
          <Route element={<Layout />}>
            <Route path="/" element={<PrivateRoute component={HomePage} />} />
            <Route path="/recipes" element={<PrivateRoute component={RecipesPage} />} />
            <Route path="/recipes/:id" element={<PrivateRoute component={RecipeDetailPage} />} />
            <Route path="/shopping-list" element={<PrivateRoute component={ShoppingListPage} />} />
          </Route>

          {/* Catch all */}
          <Route path="*" element={<Navigate to={isAuthenticated ? '/' : '/login'} replace />} />
        </Routes>
      </div>
    </Router>
  );
};

export default App;