import React from 'react';

const HomePage: React.FC = () => {
  return (
    <div className="space-y-8">
      <div className="section-title">Welcome to RecipeApp 🍳</div>
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <div className="card-hover">
          <h3 className="section-subtitle">👨‍🍳 Recipes</h3>
          <p className="text-gray-600">Browse and create delicious recipes</p>
        </div>
        <div className="card-hover">
          <h3 className="section-subtitle">🛒 Shopping List</h3>
          <p className="text-gray-600">Organize your groceries efficiently</p>
        </div>
        <div className="card-hover">
          <h3 className="section-subtitle">⭐ Favorites</h3>
          <p className="text-gray-600">Save your favorite recipes</p>
        </div>
      </div>
    </div>
  );
};

export default HomePage;
