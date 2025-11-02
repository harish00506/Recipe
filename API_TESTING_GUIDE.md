# API Testing Quick Start Guide

**Phase**: 4.5 - Entity Models & API Ready  
**Date**: November 2, 2025  
**Status**: ✅ Ready for Integration Testing

---

## 🚀 Getting Started

### Prerequisites
1. Java 21 installed
2. PostgreSQL 14+ running
3. Maven or IDE with Spring Boot support
4. Postman or cURL for API testing

### Database Setup
```bash
# Create database
createdb recipe_app

# Load schema (from backend/database)
psql recipe_app < database/schema.sql
```

### Application Start
```bash
# Navigate to backend directory
cd backend

# Run Spring Boot application
mvn spring-boot:run

# Or run as JAR
mvn clean package
java -jar target/recipe-app-0.0.1-SNAPSHOT.jar
```

Application will start on: `http://localhost:8080`

---

## 🔐 Authentication Flow

### 1. Register New User
```bash
POST /auth/register
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "SecurePassword123!",
  "firstName": "John",
  "lastName": "Doe"
}

Response (201):
{
  "email": "user@example.com",
  "message": "User registered successfully"
}
```

### 2. Login
```bash
POST /auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "SecurePassword123!"
}

Response (200):
{
  "accessToken": "eyJhbGc...",
  "refreshToken": "eyJhbGc...",
  "expiresIn": 900,
  "tokenType": "Bearer"
}
```

### 3. Use Token for Protected Endpoints
```bash
GET /recipes
Authorization: Bearer <accessToken>

Response (200):
[
  {
    "id": "uuid",
    "title": "Pasta Carbonara",
    "cuisine": "Italian",
    ...
  }
]
```

### 4. Refresh Token (When Access Token Expires)
```bash
POST /auth/refresh
Content-Type: application/json

{
  "refreshToken": "eyJhbGc..."
}

Response (200):
{
  "accessToken": "eyJhbGc...",
  "expiresIn": 900
}
```

---

## 📋 API Endpoints Summary

### Authentication Endpoints
| Method | Endpoint | Purpose | Auth |
|--------|----------|---------|------|
| POST | `/auth/register` | Register new user | ❌ |
| POST | `/auth/login` | Login and get tokens | ❌ |
| POST | `/auth/refresh` | Refresh access token | ❌ |
| POST | `/auth/validate` | Validate token | ✅ |

### Recipe Endpoints
| Method | Endpoint | Purpose | Auth |
|--------|----------|---------|------|
| GET | `/recipes` | List recipes (paginated) | ✅ |
| POST | `/recipes` | Create recipe | ✅ |
| GET | `/recipes/{id}` | Get recipe details | ✅ |
| PUT | `/recipes/{id}` | Update recipe | ✅ |
| DELETE | `/recipes/{id}` | Delete recipe | ✅ |
| GET | `/recipes/search` | Search recipes | ✅ |
| GET | `/recipes/cuisine/{id}` | Filter by cuisine | ✅ |
| GET | `/recipes/random` | Get random recipe | ✅ |

### Cuisine Endpoints
| Method | Endpoint | Purpose | Auth |
|--------|----------|---------|------|
| GET | `/cuisines` | List cuisines | ✅ |
| POST | `/cuisines` | Create cuisine | ✅ |
| GET | `/cuisines/{id}` | Get cuisine | ✅ |
| PUT | `/cuisines/{id}` | Update cuisine | ✅ |
| DELETE | `/cuisines/{id}` | Delete cuisine | ✅ |
| GET | `/cuisines/search` | Search cuisines | ✅ |

### Ingredient Endpoints
| Method | Endpoint | Purpose | Auth |
|--------|----------|---------|------|
| GET | `/ingredients` | List ingredients | ✅ |
| POST | `/ingredients` | Create ingredient | ✅ |
| GET | `/ingredients/{id}` | Get ingredient | ✅ |
| PUT | `/ingredients/{id}` | Update ingredient | ✅ |
| DELETE | `/ingredients/{id}` | Delete ingredient | ✅ |
| GET | `/ingredients/search` | Search ingredients | ✅ |
| GET | `/ingredients/category` | Filter by category | ✅ |

### Shopping List Endpoints
| Method | Endpoint | Purpose | Auth |
|--------|----------|---------|------|
| GET | `/shopping-lists` | List user's lists | ✅ |
| POST | `/shopping-lists` | Create list | ✅ |
| GET | `/shopping-lists/{id}` | Get list | ✅ |
| PUT | `/shopping-lists/{id}` | Update list | ✅ |
| DELETE | `/shopping-lists/{id}` | Delete list | ✅ |
| POST | `/shopping-lists/{id}/items` | Add item | ✅ |
| PUT | `/shopping-lists/items/{id}` | Update item | ✅ |
| DELETE | `/shopping-lists/items/{id}` | Delete item | ✅ |
| POST | `/shopping-lists/items/{id}/toggle` | Toggle purchased | ✅ |

---

## 🧪 Example Testing Workflow

### 1. Create a Cuisine
```bash
POST /cuisines
Authorization: Bearer <token>
Content-Type: application/json

{
  "name": "Italian",
  "description": "Italian cuisine"
}

Response (201):
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "name": "Italian",
  "description": "Italian cuisine"
}
```

### 2. Create Ingredients
```bash
POST /ingredients
Authorization: Bearer <token>

{
  "name": "Tomato",
  "category": "Vegetables",
  "nutritionalInfo": "Low calorie"
}

POST /ingredients
{
  "name": "Basil",
  "category": "Herbs",
  "nutritionalInfo": "Rich in antioxidants"
}
```

### 3. Create a Recipe
```bash
POST /recipes
Authorization: Bearer <token>

{
  "title": "Margherita",
  "description": "Classic Italian pizza",
  "instructions": "1. Make dough\n2. Add sauce\n3. Add cheese\n4. Bake",
  "cuisineId": "550e8400-e29b-41d4-a716-446655440000",
  "servings": 4,
  "cookingTime": 30
}

Response (201):
{
  "id": "660e8400-e29b-41d4-a716-446655440001",
  "title": "Margherita",
  ...
}
```

### 4. Search Recipes
```bash
GET /recipes/search?q=Margherita&page=0&size=10
Authorization: Bearer <token>

Response (200):
{
  "content": [
    {
      "id": "660e8400-e29b-41d4-a716-446655440001",
      "title": "Margherita",
      ...
    }
  ],
  "totalElements": 1,
  "totalPages": 1
}
```

### 5. Create Shopping List
```bash
POST /shopping-lists
Authorization: Bearer <token>

{
  "name": "Weekly Groceries"
}

Response (201):
{
  "id": "770e8400-e29b-41d4-a716-446655440002",
  "name": "Weekly Groceries",
  "items": []
}
```

### 6. Add Items to Shopping List
```bash
POST /shopping-lists/770e8400-e29b-41d4-a716-446655440002/items
Authorization: Bearer <token>

{
  "ingredientId": "880e8400-e29b-41d4-a716-446655440003",
  "quantity": 5,
  "unitId": "990e8400-e29b-41d4-a716-446655440004"
}

Response (201):
{
  "id": "aa0e8400-e29b-41d4-a716-446655440005",
  "quantity": 5,
  "purchased": false
}
```

---

## 🛠️ Testing with Postman

### Setup Postman Collection

1. **Create Environment Variables**
   ```
   base_url: http://localhost:8080
   token: (will be set automatically after login)
   ```

2. **Create Pre-request Script (for /auth/login)**
   ```javascript
   // None needed for login
   ```

3. **Create Post-request Script (for /auth/login)**
   ```javascript
   var jsonData = pm.response.json();
   pm.environment.set("token", jsonData.accessToken);
   ```

4. **For Protected Endpoints**
   - Header: `Authorization: Bearer {{token}}`

---

## 📊 HTTP Status Codes

| Code | Meaning | Usage |
|------|---------|-------|
| 200 | OK | Successful GET, PUT |
| 201 | Created | Successful POST |
| 204 | No Content | Successful DELETE |
| 400 | Bad Request | Invalid request data |
| 401 | Unauthorized | Missing/invalid token |
| 403 | Forbidden | No permission |
| 404 | Not Found | Resource not found |
| 409 | Conflict | Duplicate/conflict |
| 500 | Server Error | Server error |

---

## 🐛 Common Issues & Solutions

### Issue: 401 Unauthorized
**Cause**: Missing or invalid token  
**Solution**: 
1. Register and login to get token
2. Add `Authorization: Bearer <token>` header
3. Check token hasn't expired (15 minutes)

### Issue: 404 Not Found
**Cause**: Resource doesn't exist or wrong ID  
**Solution**:
1. Verify ID is correct
2. List all resources first
3. Create resource if missing

### Issue: 409 Conflict
**Cause**: Duplicate email or cuisine name  
**Solution**:
1. Use unique email/name
2. Check existing resources

### Issue: 500 Internal Server Error
**Cause**: Server error  
**Solution**:
1. Check server logs
2. Verify database connection
3. Check request format

---

## 🔍 Debugging Tips

### Enable Debug Logging
Update `application.properties`:
```properties
logging.level.com.recipeapp=DEBUG
logging.level.org.springframework.web=DEBUG
logging.level.org.springframework.security=DEBUG
```

### Check Database Connection
```bash
# From PostgreSQL
psql recipe_app
SELECT * FROM users;
SELECT * FROM recipes;
```

### Monitor Requests
```bash
# Using curl
curl -v http://localhost:8080/recipes \
  -H "Authorization: Bearer <token>"
```

---

## 📚 Additional Resources

- API Documentation: See `API.md`
- Architecture: See `ARCHITECTURE.md`
- Entity Models: See `PHASE_4_5_ENTITY_MODELS_REPORT.md`
- Project Details: See `README.md`

---

## ✅ Testing Checklist

- [ ] Application starts successfully
- [ ] Database connection works
- [ ] User registration succeeds
- [ ] Login returns valid token
- [ ] Can access protected endpoints with token
- [ ] Token refresh works
- [ ] Can create recipes
- [ ] Can search recipes
- [ ] Can manage shopping lists
- [ ] Pagination works
- [ ] Invalid requests return proper errors
- [ ] Token expiration triggers 401

---

## 🚀 Next Steps

Once testing is complete:
1. Review results
2. Fix any issues found
3. Run full test suite
4. Prepare for deployment

---

**Happy Testing!** 🎉

For more information, see the main `README.md` file.
