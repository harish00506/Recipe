# Recipe App - Project Statement

## 📋 Executive Summary

A digital platform enabling users to store, search, and organize recipes while automatically generating shopping lists based on selected recipes. This system streamlines meal planning and grocery shopping by centralizing recipe management and ingredient tracking.

---

## 📚 Definitions

### **Recipe**
A set of instructions and ingredients required to prepare a specific dish, including:
- Recipe name and description
- Ingredients with quantities and units
- Step-by-step cooking instructions
- Preparation and cooking time
- Servings information
- Cuisine type classification

### **Cuisine**
A specific style of cooking characterized by:
- Distinctive ingredients (e.g., Italian, Indian, Mexican, Asian)
- Unique cooking techniques and methods
- Cultural or geographic association
- Traditional dishes and flavor profiles

**Examples:** Italian, Indian, Mexican, Thai, French, Chinese, Mediterranean

### **Shopping List**
A compiled list of ingredients needed to prepare selected recipes, organized for:
- Efficient grocery shopping
- Grouped by ingredient type or store section
- Consolidated quantities (combining duplicates)
- Exportable and shareable formats

---

## 👥 User Stories & Acceptance Criteria

### 1️⃣ **Store New Recipe**

**User Story:**
```
As a home cook
I want to add my recipes with ingredients and instructions
So that I can digitally preserve and access my recipe collection
```

**Acceptance Criteria:**
- ✅ User can input recipe name, servings, and preparation time
- ✅ User can add multiple ingredients with quantities and units
- ✅ User can add step-by-step cooking instructions
- ✅ User can specify cuisine type
- ✅ User can save the recipe to their collection
- ✅ User can edit the recipe after saving

**Related Features:**
- Rich text editor for instructions
- Unit conversion support (cups, ml, grams, etc.)
- Ingredient database with autocomplete
- Recipe validation before saving
- Draft saving capability
- Image upload for recipes

**API Endpoints:**
```
POST   /api/recipes              - Create new recipe
GET    /api/recipes/{id}         - Get recipe details
PUT    /api/recipes/{id}         - Update recipe
DELETE /api/recipes/{id}         - Delete recipe
GET    /api/recipes/user/{userId} - Get user's recipes
```

---

### 2️⃣ **Search Recipes**

**User Story:**
```
As a user
I want to search recipes by ingredients or cuisine type
So that I can quickly find suitable recipes based on my preferences or available ingredients
```

**Acceptance Criteria:**
- ✅ User can search by single or multiple ingredients
- ✅ User can filter recipes by cuisine type
- ✅ Search results display recipe names and key details
- ✅ Results update in real-time as search criteria change
- ✅ User can sort results by preparation time or recipe name

**Related Features:**
- Full-text search on recipe names and descriptions
- Advanced filtering by multiple criteria
- Real-time search suggestions
- Sorting and pagination
- Search history tracking
- Saved searches

**API Endpoints:**
```
GET    /api/recipes/search?query=X          - Search recipes
GET    /api/recipes/filter?ingredients=X&cuisine=Y - Filter recipes
GET    /api/recipes?sort=time&order=asc     - Sort recipes
GET    /api/cuisines                        - Get all cuisines
GET    /api/ingredients                     - Get ingredient list
```

---

### 3️⃣ **Generate Shopping List**

**User Story:**
```
As a meal planner
I want to generate shopping lists from selected recipes
So that I can efficiently purchase all required ingredients
```

**Acceptance Criteria:**
- ✅ User can select multiple recipes for the shopping list
- ✅ System combines ingredients from all selected recipes
- ✅ System consolidates duplicate ingredients and quantities
- ✅ User can modify quantities in the generated list
- ✅ User can export or share the shopping list

**Related Features:**
- Multi-select recipe picker
- Ingredient quantity consolidation algorithm
- Unit normalization
- Shopping list editing interface
- Export to PDF/CSV
- Share via email or link
- Shopping list history
- Checklist functionality

**API Endpoints:**
```
POST   /api/shopping-lists                  - Create shopping list
GET    /api/shopping-lists/{id}             - Get shopping list
PUT    /api/shopping-lists/{id}             - Update shopping list
DELETE /api/shopping-lists/{id}             - Delete shopping list
POST   /api/shopping-lists/{id}/export      - Export as PDF/CSV
POST   /api/shopping-lists/{id}/share       - Generate share link
```

---

## 🏗️ Technical Implementation Plan

### **Database Schema Requirements**

```sql
-- Users Table
CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email VARCHAR(255) UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Cuisines Table
CREATE TABLE cuisines (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Ingredients Table
CREATE TABLE ingredients (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL UNIQUE,
    default_unit VARCHAR(50),
    calories_per_unit DECIMAL(10, 2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Recipes Table
CREATE TABLE recipes (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    cuisine_id UUID REFERENCES cuisines(id),
    name VARCHAR(255) NOT NULL,
    description TEXT,
    servings INTEGER,
    prep_time_minutes INTEGER,
    cook_time_minutes INTEGER,
    instructions TEXT NOT NULL,
    image_url VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_user_id (user_id),
    INDEX idx_cuisine_id (cuisine_id)
);

-- Recipe Ingredients Junction Table
CREATE TABLE recipe_ingredients (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    recipe_id UUID NOT NULL REFERENCES recipes(id) ON DELETE CASCADE,
    ingredient_id UUID NOT NULL REFERENCES ingredients(id),
    quantity DECIMAL(10, 2) NOT NULL,
    unit VARCHAR(50) NOT NULL,
    notes VARCHAR(255),
    PRIMARY KEY (recipe_id, ingredient_id)
);

-- Shopping Lists Table
CREATE TABLE shopping_lists (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    name VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_user_id (user_id)
);

-- Shopping List Items Junction Table
CREATE TABLE shopping_list_items (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    shopping_list_id UUID NOT NULL REFERENCES shopping_lists(id) ON DELETE CASCADE,
    ingredient_id UUID REFERENCES ingredients(id),
    quantity DECIMAL(10, 2) NOT NULL,
    unit VARCHAR(50) NOT NULL,
    checked BOOLEAN DEFAULT FALSE,
    notes VARCHAR(255),
    PRIMARY KEY (shopping_list_id, ingredient_id)
);

-- Shopping List Recipes Junction Table
CREATE TABLE shopping_list_recipes (
    shopping_list_id UUID NOT NULL REFERENCES shopping_lists(id) ON DELETE CASCADE,
    recipe_id UUID NOT NULL REFERENCES recipes(id),
    PRIMARY KEY (shopping_list_id, recipe_id)
);
```

### **Backend Service Layer**

**RecipeService** - Handles all recipe operations
```java
- createRecipe(CreateRecipeDTO): RecipeDTO
- updateRecipe(UUID, UpdateRecipeDTO): RecipeDTO
- deleteRecipe(UUID): void
- getRecipeById(UUID): RecipeDTO
- getUserRecipes(UUID userId, pagination): Page<RecipeDTO>
- searchRecipes(query, filters): List<RecipeDTO>
- filterByCuisine(UUID cuisineId): List<RecipeDTO>
- filterByIngredients(List<UUID> ingredientIds): List<RecipeDTO>
```

**ShoppingListService** - Handles shopping list operations
```java
- createShoppingList(CreateShoppingListDTO): ShoppingListDTO
- addRecipesToList(UUID listId, List<UUID> recipeIds): ShoppingListDTO
- consolidateIngredients(List<RecipeDTO>): List<ShoppingListItemDTO>
- exportToCSV(UUID listId): File
- exportToPDF(UUID listId): File
- generateShareLink(UUID listId): String
- updateItemQuantity(UUID itemId, BigDecimal quantity): void
```

**IngredientService** - Handles ingredient operations
```java
- getAllIngredients(): List<IngredientDTO>
- searchIngredients(query): List<IngredientDTO>
- createIngredient(CreateIngredientDTO): IngredientDTO
```

**CuisineService** - Handles cuisine operations
```java
- getAllCuisines(): List<CuisineDTO>
- getCuisineById(UUID): CuisineDTO
```

---

## 📊 Feature Matrix

| Feature | Status | Priority | User Story |
|---------|--------|----------|-----------|
| Create Recipe | ⭕ | High | Store Recipe |
| Edit Recipe | ⭕ | High | Store Recipe |
| Delete Recipe | ⭕ | High | Store Recipe |
| Add Ingredients | ⭕ | High | Store Recipe |
| Add Instructions | ⭕ | High | Store Recipe |
| Specify Cuisine | ⭕ | High | Store Recipe |
| Search by Ingredients | ⭕ | High | Search Recipes |
| Filter by Cuisine | ⭕ | High | Search Recipes |
| Real-time Search | ⭕ | Medium | Search Recipes |
| Sort Results | ⭕ | Medium | Search Recipes |
| Select Multiple Recipes | ⭕ | High | Shopping List |
| Generate Shopping List | ⭕ | High | Shopping List |
| Consolidate Ingredients | ⭕ | High | Shopping List |
| Modify Quantities | ⭕ | High | Shopping List |
| Export Shopping List | ⭕ | Medium | Shopping List |
| Share Shopping List | ⭕ | Medium | Shopping List |

---

## 🎯 Success Metrics

- ✅ Users can complete all CRUD operations on recipes
- ✅ Search functionality returns results in < 200ms
- ✅ Shopping list consolidation correctly combines ingredients
- ✅ All user stories implemented with acceptance criteria met
- ✅ API response time < 500ms
- ✅ 90% test coverage for service layer
- ✅ All acceptance criteria verified

---

## 📝 Notes

- UUID identification for all entities
- Email-based authentication with JWT tokens
- MapStruct for entity-to-DTO mapping
- Real-time search using full-text indexing
- Ingredient consolidation with unit conversion
- Export functionality for PDF and CSV
- Comprehensive error handling and validation
- Comprehensive logging and monitoring

---

**Last Updated:** November 2, 2025
