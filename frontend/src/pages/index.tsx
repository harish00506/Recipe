import React from 'react';
import HelloWorld from '../components/HelloWorld';

const HomePage: React.FC = () => {
    return (
        <div>
            <h1>Welcome to the Recipe App</h1>
            <HelloWorld />
        </div>
    );
};

export default HomePage;