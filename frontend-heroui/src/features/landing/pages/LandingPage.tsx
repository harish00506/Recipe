import React from 'react';
import { Button } from '@heroui/react';
import { useNavigate } from 'react-router-dom';
import { ChefHat, Book, Clock } from 'lucide-react';

export default function LandingPage(): React.ReactElement {
    const navigate = useNavigate();

    return (
        <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100 dark:from-gray-900 dark:to-gray-800">
            {/* Navigation Bar */}
            <nav className="border-b border-blue-200 dark:border-gray-700 bg-white dark:bg-gray-800 shadow-sm">
                <div className="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-4 flex justify-between items-center">
                    <div className="flex items-center gap-2">
                        <ChefHat className="w-8 h-8 text-blue-600 dark:text-blue-400" />
                        <span className="text-2xl font-bold text-gray-900 dark:text-white">RecipeApp</span>
                    </div>
                    <div className="flex gap-3">
                        <Button
                            onPress={() => navigate('/auth/signin')}
                            variant="light"
                            className="text-gray-700 dark:text-gray-200"
                        >
                            Sign In
                        </Button>
                        <Button
                            onPress={() => navigate('/auth/signup')}
                            color="primary"
                        >
                            Get Started
                        </Button>
                    </div>
                </div>
            </nav>

            {/* Hero Section */}
            <section className="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-20">
                <div className="grid md:grid-cols-2 gap-12 items-center">
                    {/* Left Content */}
                    <div>
                        <h1 className="text-5xl md:text-6xl font-bold text-gray-900 dark:text-white mb-6">
                            Discover Your Next Favorite Recipe
                        </h1>
                        <p className="text-xl text-gray-700 dark:text-gray-300 mb-8">
                            Explore thousands of delicious recipes, save your favorites, and create personalized shopping lists. Cook like a pro with RecipeApp.
                        </p>
                        <div className="flex flex-col sm:flex-row gap-4">
                            <Button
                                onPress={() => navigate('/auth/signup')}
                                color="primary"
                                size="lg"
                                className="font-semibold"
                            >
                                Start Free
                            </Button>
                            <Button
                                onPress={() => navigate('/auth/signin')}
                                variant="bordered"
                                size="lg"
                                className="font-semibold text-gray-900 dark:text-white border-gray-300 dark:border-gray-600"
                            >
                                Sign In
                            </Button>
                        </div>
                    </div>

                    {/* Right Image/Icons */}
                    <div className="grid grid-cols-2 gap-6">
                        <div className="bg-white dark:bg-gray-700 rounded-2xl p-8 shadow-lg text-center">
                            <ChefHat className="w-12 h-12 text-blue-600 dark:text-blue-400 mx-auto mb-4" />
                            <h3 className="font-bold text-gray-900 dark:text-white mb-2">1000+ Recipes</h3>
                            <p className="text-sm text-gray-600 dark:text-gray-300">Explore diverse cuisines</p>
                        </div>
                        <div className="bg-white dark:bg-gray-700 rounded-2xl p-8 shadow-lg text-center">
                            <Book className="w-12 h-12 text-green-600 dark:text-green-400 mx-auto mb-4" />
                            <h3 className="font-bold text-gray-900 dark:text-white mb-2">Save & Organize</h3>
                            <p className="text-sm text-gray-600 dark:text-gray-300">Your personal cookbook</p>
                        </div>
                        <div className="bg-white dark:bg-gray-700 rounded-2xl p-8 shadow-lg text-center col-span-2">
                            <Clock className="w-12 h-12 text-orange-600 dark:text-orange-400 mx-auto mb-4" />
                            <h3 className="font-bold text-gray-900 dark:text-white mb-2">Smart Shopping Lists</h3>
                            <p className="text-sm text-gray-600 dark:text-gray-300">Plan meals & cook efficiently</p>
                        </div>
                    </div>
                </div>
            </section>

            {/* Features Section */}
            <section className="bg-white dark:bg-gray-800 py-20">
                <div className="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8">
                    <h2 className="text-4xl font-bold text-center text-gray-900 dark:text-white mb-16">
                        Why Choose RecipeApp?
                    </h2>
                    <div className="grid md:grid-cols-3 gap-8">
                        <div className="p-8 border border-gray-200 dark:border-gray-700 rounded-xl hover:shadow-lg transition-shadow">
                            <div className="w-12 h-12 bg-blue-100 dark:bg-blue-900 rounded-lg flex items-center justify-center mb-4">
                                <ChefHat className="w-6 h-6 text-blue-600 dark:text-blue-300" />
                            </div>
                            <h3 className="text-xl font-bold text-gray-900 dark:text-white mb-3">Curated Recipes</h3>
                            <p className="text-gray-600 dark:text-gray-400">Hand-picked recipes from top chefs and food enthusiasts worldwide</p>
                        </div>
                        <div className="p-8 border border-gray-200 dark:border-gray-700 rounded-xl hover:shadow-lg transition-shadow">
                            <div className="w-12 h-12 bg-green-100 dark:bg-green-900 rounded-lg flex items-center justify-center mb-4">
                                <Book className="w-6 h-6 text-green-600 dark:text-green-300" />
                            </div>
                            <h3 className="text-xl font-bold text-gray-900 dark:text-white mb-3">Easy Organization</h3>
                            <p className="text-gray-600 dark:text-gray-400">Save recipes by cuisine, difficulty, and prep time for quick access</p>
                        </div>
                        <div className="p-8 border border-gray-200 dark:border-gray-700 rounded-xl hover:shadow-lg transition-shadow">
                            <div className="w-12 h-12 bg-orange-100 dark:bg-orange-900 rounded-lg flex items-center justify-center mb-4">
                                <Clock className="w-6 h-6 text-orange-600 dark:text-orange-300" />
                            </div>
                            <h3 className="text-xl font-bold text-gray-900 dark:text-white mb-3">Smart Shopping</h3>
                            <p className="text-gray-600 dark:text-gray-400">Create shopping lists automatically from your selected recipes</p>
                        </div>
                    </div>
                </div>
            </section>

            {/* CTA Section */}
            <section className="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-20 text-center">
                <h2 className="text-4xl font-bold text-gray-900 dark:text-white mb-6">
                    Ready to cook amazing dishes?
                </h2>
                <p className="text-xl text-gray-600 dark:text-gray-300 mb-8">
                    Join thousands of food lovers and start exploring recipes today
                </p>
                <Button
                    onPress={() => navigate('/auth/signup')}
                    color="primary"
                    size="lg"
                    className="font-semibold px-8"
                >
                    Create Your Account
                </Button>
            </section>

            {/* Footer */}
            <footer className="border-t border-gray-200 dark:border-gray-700 bg-white dark:bg-gray-800 py-8">
                <div className="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 text-center text-gray-600 dark:text-gray-400">
                    <p>&copy; 2025 RecipeApp. All rights reserved.</p>
                </div>
            </footer>
        </div>
    );
}
