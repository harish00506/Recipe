import React, { useState } from 'react';
import { Card, CardBody, CardHeader, Button, Table, TableHeader, TableColumn, TableBody, TableRow, TableCell, Pagination, Input, Chip } from '@heroui/react';
import { ChefHat, Clock, Users } from 'lucide-react';

interface Recipe {
    id: string;
    name: string;
    cuisine: string;
    prepTime: number;
    servings: number;
}

const SAMPLE_RECIPES: Recipe[] = [
    { id: '1', name: 'Spaghetti Carbonara', cuisine: 'Italian', prepTime: 30, servings: 4 },
    { id: '2', name: 'Pad Thai', cuisine: 'Thai', prepTime: 25, servings: 2 },
    { id: '3', name: 'Chicken Tikka Masala', cuisine: 'Indian', prepTime: 45, servings: 4 },
    { id: '4', name: 'Beef Tacos', cuisine: 'Mexican', prepTime: 20, servings: 4 },
    { id: '5', name: 'Caesar Salad', cuisine: 'American', prepTime: 15, servings: 2 },
];

export default function DashboardPage(): React.ReactElement {
    const [page, setPage] = useState(1);
    const [searchTerm, setSearchTerm] = useState('');

    const filtered = SAMPLE_RECIPES.filter((r) => r.name.toLowerCase().includes(searchTerm.toLowerCase()));

    return (
        <div className="space-y-8">
            {/* Header */}
            <div>
                <h1 className="text-4xl font-bold">Welcome to Recipe App 👨‍🍳</h1>
                <p className="mt-2 text-gray-600 dark:text-gray-400">Discover, create, and manage your favorite recipes</p>
            </div>

            {/* Stats Cards */}
            <div className="grid grid-cols-1 gap-6 md:grid-cols-3">
                <Card>
                    <CardBody className="flex flex-row gap-4">
                        <div className="flex h-12 w-12 items-center justify-center rounded-lg bg-primary-100 dark:bg-primary-900">
                            <ChefHat className="text-primary" />
                        </div>
                        <div>
                            <p className="text-sm text-gray-600 dark:text-gray-400">Total Recipes</p>
                            <p className="text-2xl font-bold">42</p>
                        </div>
                    </CardBody>
                </Card>

                <Card>
                    <CardBody className="flex flex-row gap-4">
                        <div className="flex h-12 w-12 items-center justify-center rounded-lg bg-success-100 dark:bg-success-900">
                            <Users className="text-success" />
                        </div>
                        <div>
                            <p className="text-sm text-gray-600 dark:text-gray-400">Saved Favorites</p>
                            <p className="text-2xl font-bold">12</p>
                        </div>
                    </CardBody>
                </Card>

                <Card>
                    <CardBody className="flex flex-row gap-4">
                        <div className="flex h-12 w-12 items-center justify-center rounded-lg bg-warning-100 dark:bg-warning-900">
                            <Clock className="text-warning" />
                        </div>
                        <div>
                            <p className="text-sm text-gray-600 dark:text-gray-400">Avg Prep Time</p>
                            <p className="text-2xl font-bold">28 min</p>
                        </div>
                    </CardBody>
                </Card>
            </div>

            {/* Recipe List */}
            <Card>
                <CardHeader className="flex flex-col gap-4">
                    <div className="flex flex-col gap-2">
                        <h2 className="text-xl font-bold">Recent Recipes</h2>
                        <p className="text-sm text-gray-600 dark:text-gray-400">Browse and filter your recipes</p>
                    </div>
                    <div className="flex gap-2">
                        <Input
                            isClearable
                            className="w-full sm:max-w-xs"
                            placeholder="Search recipes..."
                            value={searchTerm}
                            onValueChange={setSearchTerm}
                        />
                        <Button color="primary">Add Recipe</Button>
                    </div>
                </CardHeader>
                <CardBody>
                    <Table aria-label="Recipe list">
                        <TableHeader>
                            <TableColumn>NAME</TableColumn>
                            <TableColumn>CUISINE</TableColumn>
                            <TableColumn>PREP TIME</TableColumn>
                            <TableColumn>SERVINGS</TableColumn>
                            <TableColumn>ACTION</TableColumn>
                        </TableHeader>
                        <TableBody emptyContent="No recipes found">
                            {filtered.map((recipe) => (
                                <TableRow key={recipe.id}>
                                    <TableCell>{recipe.name}</TableCell>
                                    <TableCell>
                                        <Chip size="sm" variant="flat" color="primary">
                                            {recipe.cuisine}
                                        </Chip>
                                    </TableCell>
                                    <TableCell>{recipe.prepTime} min</TableCell>
                                    <TableCell>{recipe.servings}</TableCell>
                                    <TableCell>
                                        <Button size="sm" variant="light">
                                            View
                                        </Button>
                                    </TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </CardBody>
            </Card>

            {/* Pagination */}
            <div className="flex justify-center">
                <Pagination total={5} page={page} onChange={setPage} />
            </div>
        </div>
    );
}
