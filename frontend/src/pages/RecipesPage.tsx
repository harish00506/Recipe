import React from 'react';

const RecipesPage: React.FC = () => {
    return (
        <div className="space-y-8">
            <div className="section-title">Recipes</div>
            <div className="btn-primary">Create New Recipe</div>
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                {/* Recipes will be displayed here */}
                <div className="card">Recipe Card Placeholder</div>
            </div>
        </div>
    );
};

export default RecipesPage;
