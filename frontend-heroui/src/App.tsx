import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { useAuth } from './hooks/useAuth';
import AppLayout from './components/layout/AppLayout';
import DashboardPage from './features/dashboard/pages/DashboardPage';
import SignInPage from './features/auth/pages/SignInPage';
import SignUpPage from './features/auth/pages/SignUpPage';
import SettingsPage from './features/settings/pages/SettingsPage';
import LandingPage from './features/landing/pages/LandingPage';

function ProtectedRoute({
    isAuthenticated,
    children,
}: {
    isAuthenticated: boolean;
    children: React.ReactNode;
}): React.ReactElement {
    return isAuthenticated ? <>{children}</> : <Navigate to="/auth/signin" replace />;
}

function App(): React.ReactElement {
    const { isAuthenticated } = useAuth();
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        // Simulate loading
        setIsLoading(false);
    }, []);

    if (isLoading) {
        return <div className="flex items-center justify-center h-screen">Loading...</div>;
    }

    return (
        <Router>
            <Routes>
                {/* Landing/Public Routes */}
                <Route path="/landing" element={<LandingPage />} />
                <Route path="/auth/signin" element={<SignInPage />} />
                <Route path="/auth/signup" element={<SignUpPage />} />

                {/* Protected Routes */}
                <Route
                    element={
                        <ProtectedRoute isAuthenticated={isAuthenticated}>
                            <AppLayout />
                        </ProtectedRoute>
                    }
                >
                    <Route path="/" element={<DashboardPage />} />
                    <Route path="/settings" element={<SettingsPage />} />
                </Route>

                {/* Catch all - redirect to landing if not authenticated, dashboard if authenticated */}
                <Route path="*" element={<Navigate to={isAuthenticated ? "/" : "/landing"} replace />} />
            </Routes>
        </Router>
    );
}

export default App;
