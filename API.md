# 📖 API Documentation - Recipe App

## Base URL
```
http://localhost:8080/api
```

---

## 🔐 Authentication

### Register User
```http
POST /api/auth/register
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "securepassword123",
  "name": "John Doe"
}

Response: 201 Created
{
  "id": "uuid",
  "email": "user@example.com",
  "name": "John Doe",
  "token": "jwt-token-here"
}
```

### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "securepassword123"
}

Response: 200 OK
{
  "id": "uuid",
  "email": "user@example.com",
  "name": "John Doe",
  "token": "jwt-token-here"
}
```

### Logout
```http
POST /api/auth/logout
Authorization: Bearer <token>

Response: 200 OK
{
  "message": "Logged out successfully"
}
```

---

## 🍳 Recipe Endpoints

### Get All Recipes (Paginated)
```http
GET /api/recipes?page=0&size=20&sort=createdAt,desc
Authorization: Bearer <token>

Response: 200 OK
{
  "content": [
    {
      "id": "uuid",
      "name": "Pasta Carbonara",
      "servings": 4,
      "prepTime": 10,
      "cookTime": 20,
      "instructions": "...",
      "cuisine": { "id": "uuid", "name": "Italian" },
      "ingredients": [
        {
          "id": "uuid",
          "name": "Pasta",
          "quantity": 400,
          "unit": "grams"
        }
      ]
    }
  ],
  "totalElements": 100,
  "totalPages": 5,
  "currentPage": 0
}
```

### Get Recipe by ID
```http
GET /api/recipes/{id}
Authorization: Bearer <token>

Response: 200 OK
{
  "id": "uuid",
  "name": "Pasta Carbonara",
  "servings": 4,
  "prepTime": 10,
  "cookTime": 20,
  "instructions": "...",
  "cuisine": { "id": "uuid", "name": "Italian" },
  "ingredients": [...]
}
```

### Create Recipe
```http
POST /api/recipes
Authorization: Bearer <token>
Content-Type: application/json

{
  "name": "Pasta Carbonara",
  "servings": 4,
  "prepTime": 10,
  "cookTime": 20,
  "instructions": "Step 1: Boil pasta...",
  "cuisineId": "uuid",
  "ingredients": [
    {
      "ingredientId": "uuid",
      "quantity": 400,
      "unit": "grams"
    }
  ]
}

Response: 201 Created
{
  "id": "new-uuid",
  "name": "Pasta Carbonara",
  ...
}
```

### Update Recipe
```http
PUT /api/recipes/{id}
Authorization: Bearer <token>
Content-Type: application/json

{
  "name": "Updated Recipe Name",
  "servings": 6,
  ...
}

Response: 200 OK
{
  "id": "uuid",
  "name": "Updated Recipe Name",
  ...
}
```

### Delete Recipe
```http
DELETE /api/recipes/{id}
Authorization: Bearer <token>

Response: 204 No Content
```

### Search Recipes
```http
GET /api/recipes/search?query=pasta
Authorization: Bearer <token>

Response: 200 OK
[
  {
    "id": "uuid",
    "name": "Pasta Carbonara",
    ...
  }
]
```

### Filter by Cuisine
```http
GET /api/recipes/filter?cuisineId=uuid
Authorization: Bearer <token>

Response: 200 OK
[...]
```

---

## 🏷️ Ingredient Endpoints

### Get All Ingredients
```http
GET /api/ingredients
Authorization: Bearer <token>

Response: 200 OK
[
  {
    "id": "uuid",
    "name": "Pasta",
    "defaultUnit": "grams"
  }
]
```

### Search Ingredients
```http
GET /api/ingredients/search?query=pasta
Authorization: Bearer <token>

Response: 200 OK
[...]
```

### Create Ingredient
```http
POST /api/ingredients
Authorization: Bearer <token>
Content-Type: application/json

{
  "name": "Olive Oil",
  "defaultUnit": "ml"
}

Response: 201 Created
{
  "id": "new-uuid",
  "name": "Olive Oil",
  "defaultUnit": "ml"
}
```

---

## 🍽️ Cuisine Endpoints

### Get All Cuisines
```http
GET /api/cuisines
Authorization: Bearer <token>

Response: 200 OK
[
  {
    "id": "uuid",
    "name": "Italian",
    "description": "Italian cuisine..."
  }
]
```

### Get Cuisine by ID
```http
GET /api/cuisines/{id}
Authorization: Bearer <token>

Response: 200 OK
{
  "id": "uuid",
  "name": "Italian",
  "description": "..."
}
```

---

## 🛒 Shopping List Endpoints

### Create Shopping List
```http
POST /api/shopping-lists
Authorization: Bearer <token>
Content-Type: application/json

{
  "name": "Weekly Groceries",
  "recipeIds": ["recipe-uuid-1", "recipe-uuid-2"]
}

Response: 201 Created
{
  "id": "uuid",
  "name": "Weekly Groceries",
  "items": [
    {
      "id": "uuid",
      "ingredientName": "Pasta",
      "quantity": 800,
      "unit": "grams",
      "checked": false
    }
  ],
  "createdAt": "2025-11-02T10:00:00Z"
}
```

### Get Shopping List by ID
```http
GET /api/shopping-lists/{id}
Authorization: Bearer <token>

Response: 200 OK
{...}
```

### Get All Shopping Lists
```http
GET /api/shopping-lists
Authorization: Bearer <token>

Response: 200 OK
[...]
```

### Update Shopping List Item
```http
PUT /api/shopping-lists/{id}/items/{itemId}
Authorization: Bearer <token>
Content-Type: application/json

{
  "quantity": 1000,
  "checked": true
}

Response: 200 OK
{...}
```

### Export Shopping List to CSV
```http
GET /api/shopping-lists/{id}/export/csv
Authorization: Bearer <token>

Response: 200 OK
(CSV file download)
```

### Export Shopping List to PDF
```http
GET /api/shopping-lists/{id}/export/pdf
Authorization: Bearer <token>

Response: 200 OK
(PDF file download)
```

### Delete Shopping List
```http
DELETE /api/shopping-lists/{id}
Authorization: Bearer <token>

Response: 204 No Content
```

---

## Error Responses

### 400 Bad Request
```json
{
  "status": 400,
  "message": "Invalid input data",
  "timestamp": "2025-11-02T10:00:00Z",
  "path": "/api/recipes"
}
```

### 401 Unauthorized
```json
{
  "status": 401,
  "message": "Unauthorized - Invalid or missing token",
  "timestamp": "2025-11-02T10:00:00Z"
}
```

### 403 Forbidden
```json
{
  "status": 403,
  "message": "Forbidden - You don't have permission",
  "timestamp": "2025-11-02T10:00:00Z"
}
```

### 404 Not Found
```json
{
  "status": 404,
  "message": "Recipe not found",
  "timestamp": "2025-11-02T10:00:00Z"
}
```

### 500 Internal Server Error
```json
{
  "status": 500,
  "message": "Internal server error",
  "timestamp": "2025-11-02T10:00:00Z"
}
```

---

## 📝 Request/Response Headers

### Required Headers
```
Authorization: Bearer <jwt-token>
Content-Type: application/json
```

### Response Headers
```
Content-Type: application/json
X-Total-Count: 100
X-Page-Number: 0
X-Page-Size: 20
```

---

## 🔗 Pagination

All list endpoints support pagination:

```http
GET /api/recipes?page=0&size=20&sort=createdAt,desc

Query Parameters:
- page: Page number (0-indexed)
- size: Number of items per page (max 100)
- sort: Sort field and direction (field,asc|desc)
```

---

**Last Updated:** November 2, 2025
