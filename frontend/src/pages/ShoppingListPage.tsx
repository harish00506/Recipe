import React from 'react';

const ShoppingListPage: React.FC = () => {
  return (
    <div className="space-y-8">
      <div className="section-title">Shopping List</div>
      <div className="btn-primary">Create New List</div>
      <div className="space-y-4">
        {/* Shopping lists will be displayed here */}
        <div className="card">Shopping list placeholder</div>
      </div>
    </div>
  );
};

export default ShoppingListPage;
