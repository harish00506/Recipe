# 🏗️ Architecture Documentation - Recipe App

## System Architecture Overview

```
┌─────────────────────────────────────────────────────────────┐
│                    Frontend Layer                            │
│                    (React + TypeScript)                      │
│  ┌──────────────────────────────────────────────────────┐   │
│  │  Components     Pages     Services     Hooks         │   │
│  │  - RecipeList   - Home    - apiClient  - useRecipes │   │
│  │  - RecipeForm   - Search  - auth       - useSearch  │   │
│  │  - SearchBar    - Shop    - recipes    - useCart    │   │
│  └──────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
                           │
                    HTTP/REST API
                           │
┌─────────────────────────────────────────────────────────────┐
│              Controller Layer (Spring Boot)                  │
│  ┌──────────────────────────────────────────────────────┐   │
│  │  RecipeController      ShoppingListController       │   │
│  │  IngredientController  CuisineController            │   │
│  │  AuthController        ErrorController              │   │
│  └──────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
                           │
                    Service calls
                           │
┌─────────────────────────────────────────────────────────────┐
│            Business Logic Layer (Services)                   │
│  ┌──────────────────────────────────────────────────────┐   │
│  │  RecipeService         ShoppingListService          │   │
│  │  IngredientService     CuisineService               │   │
│  │  AuthenticationService UserService                  │   │
│  └──────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
                           │
                    Repository calls
                           │
┌─────────────────────────────────────────────────────────────┐
│          Data Access Layer (Repositories)                    │
│  ┌──────────────────────────────────────────────────────┐   │
│  │  RecipeRepository      ShoppingListRepository       │   │
│  │  IngredientRepository  CuisineRepository            │   │
│  │  UserRepository                                      │   │
│  └──────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
                           │
                        SQL queries
                           │
┌─────────────────────────────────────────────────────────────┐
│            Database Layer (PostgreSQL)                       │
│  ┌──────────────────────────────────────────────────────┐   │
│  │  Users | Recipes | Ingredients | Cuisines | Shopping│   │
│  └──────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
```

---

## 📊 Database Entity Relationship Diagram

```
┌─────────────┐
│   Users     │
│─────────────│
│ id (UUID)   │
│ email       │
│ name        │
│ password    │
└────────────┬┘
             │ (1)
             │
        (M)  │
     ┌──────┴──────┐
     │             │
     │             ├──────────────┐
     │             │              │
┌────┴─────────┐  │    ┌─────────┴────┐
│  Recipes     │  │    │  Shopping    │
│──────────────│  │    │  Lists       │
│ id (UUID)    │  │    │──────────────│
│ name         │  │    │ id (UUID)    │
│ author_id    ├──┘    │ user_id      │
│ cuisine_id   │       │ name         │
│ content      │       └──────┬───────┘
│ prep_time    │              │
│ cook_time    │         (1)  │
│ status       │              │
└────┬────────┬┘          (M) │
     │        │               │
(M)  │        │          ┌────┴───────────┐
     │        │          │                │
┌────┴────┐  │    ┌──────┴────────────┐  │
│ Cuisines│  │    │ Shopping         │  │
│─────────│  │    │ ListRecipes      │  │
│ id      ├──┘    │──────────────────┤  │
│ name    │       │ shopping_list_id │  │
└─────────┘       │ recipe_id        │  │
                  └──────────────────┘  │
                                        │
                         ┌──────────────┘
                         │
        ┌────────────────┴────────────────┐
        │                                 │
┌───────┴──────────┐          ┌──────────┴──────┐
│ Recipe           │          │ Shopping        │
│ Ingredients      │          │ ListItems       │
│──────────────────│          │─────────────────│
│ id (UUID)        │          │ id (UUID)       │
│ recipe_id        │          │ shopping_list_id
│ ingredient_id    │          │ ingredient_id   │
│ quantity         │          │ quantity        │
│ unit             │          │ unit            │
└──────────┬───────┘          │ checked         │
           │                  └────────────────┘
           │
      (M)  │
           │
      ┌────┴────────┐
      │              │
┌─────┴──────────┐   │
│ Ingredients    │   │
│────────────────│   │
│ id (UUID)      │   │
│ name           ├───┘
│ default_unit   │
└────────────────┘
```

---

## 🔄 Request/Response Flow

### Recipe Creation Flow
```
1. User fills recipe form (Frontend)
   ↓
2. POST /api/recipes (Frontend sends request)
   ↓
3. RecipeController.createRecipe() (Controller receives)
   ↓
4. RecipeService.createRecipe() (Business logic)
   ├→ Validate input
   ├→ Calculate reading time
   ├→ Create Recipe entity
   ├→ Map ingredients
   └→ Save to database
   ↓
5. RecipeRepository.save() (Data access)
   ↓
6. PostgreSQL INSERT (Database operation)
   ↓
7. RecipeDTO returned (Response mapping)
   ↓
8. HTTP 201 Created (Frontend receives)
   ↓
9. Update UI with new recipe (Frontend displays)
```

### Shopping List Generation Flow
```
1. User selects recipes (Frontend)
   ↓
2. POST /api/shopping-lists (Frontend sends)
   ↓
3. ShoppingListController.generate() (Controller)
   ↓
4. ShoppingListService.generateShoppingList() (Business logic)
   ├→ Fetch selected recipes
   ├→ Extract all ingredients
   ├→ Consolidate ingredients
   │  ├→ Group by ingredient
   │  ├→ Sum quantities
   │  └→ Convert units
   ├→ Create ShoppingList entity
   ├→ Create ShoppingListItems
   └→ Save to database
   ↓
5. ShoppingListRepository.save() (Data access)
   ↓
6. PostgreSQL INSERT (Database operations)
   ↓
7. ShoppingListDTO returned (Response mapping)
   ↓
8. HTTP 201 Created (Frontend receives)
   ↓
9. Display consolidated list (Frontend displays)
```

---

## 🔐 Security Architecture

### Authentication Flow
```
User Login
    ↓
POST /api/auth/login
    ↓
AuthController receives credentials
    ↓
AuthenticationService.authenticate()
    ├→ Find user by email
    ├→ Verify password (bcrypt)
    └→ Generate JWT token
    ↓
JWT Token (eye.payload.signature)
    ↓
Return token to Frontend
    ↓
Frontend stores token (localStorage/sessionStorage)
    ↓
Subsequent requests include: Authorization: Bearer <token>
    ↓
JwtAuthenticationFilter validates token
    ↓
Request processing or 401 Unauthorized
```

### Authorization Flow
```
1. JWT Token arrives with request
   ↓
2. JwtAuthenticationFilter intercepts
   ↓
3. Validate token signature and expiration
   ↓
4. Extract user information from token
   ↓
5. Load user from database (CustomUserDetails)
   ↓
6. Set SecurityContext with authenticated user
   ↓
7. Check method-level authorization
   ├→ @PreAuthorize("hasRole('USER')")
   └→ @Secured("ROLE_ADMIN")
   ↓
8. If authorized: proceed
   If not: throw AccessDeniedException
```

---

## 📈 Scalability Considerations

### Current State
- Single backend instance
- Single database
- No caching layer
- Synchronous operations

### Future Improvements
```
┌─────────────────┐
│  Load Balancer  │
└────────┬────────┘
         │
    ┌────┴────┬────┐
    │         │    │
┌───┴──┐ ┌────┴─┐ ┌───┴──┐
│Back1 │ │Back2 │ │Back3 │
└───┬──┘ └────┬─┘ └───┬──┘
    │         │      │
    └────┬────┴──┬───┘
         │       │
    ┌────┴──┐  ┌─┴──────────┐
    │Cache  │  │  Database  │
    │(Redis)│  │ (Replicated)
    └───────┘  └────────────┘
```

### Optimization Strategies
1. **Caching:** Redis for frequently accessed data
2. **Database Optimization:** Indexes on common queries
3. **Pagination:** Limit result sets
4. **Async Processing:** Background jobs for exports
5. **CDN:** Static assets distribution
6. **Search:** Full-text search optimization

---

## 🏭 Deployment Architecture

### Development Environment
```
docker-compose.yml
    ├── PostgreSQL (localhost:5432)
    ├── pgAdmin (localhost:5050)
    ├── Backend (localhost:8080)
    └── Frontend (localhost:3000)
```

### Production Environment
```
Cloud Provider (AWS/GCP/Azure)
    ├── Application Server (Backend)
    │   ├── Auto-scaling Group
    │   ├── Load Balancer
    │   └── Health Checks
    ├── Database
    │   ├── Managed PostgreSQL
    │   ├── Backup & Recovery
    │   └── Read Replicas
    ├── Frontend
    │   ├── CDN Distribution
    │   ├── Static Asset Hosting
    │   └── SSL/TLS
    └── Monitoring
        ├── Logs (CloudWatch/Stackdriver)
        ├── Metrics (Prometheus)
        └── Alerts (PagerDuty)
```

---

## 🔄 API Versioning Strategy

Current version: `v1` (implicit in `/api` prefix)

Future versioning:
```
/api/v1/recipes         (Current)
/api/v2/recipes         (Future with breaking changes)
/api/v1/recipes-beta    (Beta features)
```

---

## 📊 Data Flow Example: Search Recipe

```
Frontend:
1. User types "pasta" in search box
2. onChange event triggers
3. Call recipeService.searchRecipes("pasta")

HTTP Request:
GET /api/recipes/search?query=pasta&page=0&size=20
Authorization: Bearer <token>

Backend:
1. RecipeController.searchRecipes(query, pageable)
2. RecipeService.searchRecipes(query)
   - RecipeRepository.findByNameContainingIgnoreCase(query, pageable)
3. Database Query:
   SELECT * FROM recipes 
   WHERE LOWER(name) LIKE LOWER('%pasta%')
   ORDER BY created_at DESC
   LIMIT 20

Response:
200 OK
[
  { id: "...", name: "Pasta Carbonara", ... },
  { id: "...", name: "Pasta Bolognese", ... }
]

Frontend:
1. Receive response
2. Map to RecipeDTO objects
3. Update component state
4. Re-render with results
```

---

## 🛠️ Development Guidelines

### Adding a New Feature

1. **Backend:**
   - Create Entity (if needed)
   - Create DTO
   - Create Repository
   - Create Service
   - Create Controller
   - Write Tests

2. **Frontend:**
   - Create Component
   - Create Service
   - Create Page (if needed)
   - Add Routing
   - Write Tests

3. **Database:**
   - Add migration (if needed)
   - Update schema.sql

4. **Documentation:**
   - Update API.md
   - Update README.md
   - Update ARCHITECTURE.md

---

**Last Updated:** November 2, 2025
