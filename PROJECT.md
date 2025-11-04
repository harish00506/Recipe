# 📖 Recipe App - Project Overview

A full-stack recipe management application built with modern web technologies.

## 🎯 Project Summary

**Recipe App** is a web application that allows users to:
- Browse and search recipes
- Create and manage personal recipes
- Organize ingredients
- Create and manage shopping lists
- Explore recipes by cuisine

## 🏗️ Architecture

### Technology Stack

**Backend**:
- Spring Boot 3.x
- Java 17+
- MySQL 8.x
- JPA/Hibernate
- JWT Authentication

**Frontend**:
- React 18.2+
- TypeScript 4.9+
- Tailwind CSS 3.3+
- Zustand (state management)
- Axios (HTTP client)

**Infrastructure**:
- Docker & Docker Compose
- MySQL Database
- Git Version Control

### System Design

```
┌─────────────────────────────────────────────────────┐
│                                                     │
│              React Frontend (Port 3000)              │
│        TypeScript + Tailwind + Zustand               │
│                                                     │
└────────────────────┬────────────────────────────────┘
                     │ HTTP/REST + JWT
                     │
┌────────────────────▼────────────────────────────────┐
│                                                     │
│         Spring Boot Backend (Port 8080)             │
│      40+ REST Endpoints + JWT Authentication        │
│                                                     │
└────────────────────┬────────────────────────────────┘
                     │ JDBC
                     │
┌────────────────────▼────────────────────────────────┐
│                                                     │
│         MySQL Database (Port 3306)                  │
│              8 Entity Models                        │
│                                                     │
└─────────────────────────────────────────────────────┘
```

## 📊 Key Features

### Authentication & Users
- User registration
- User login with JWT
- Secure password handling
- Session management

### Recipe Management
- View all recipes (paginated)
- Search recipes
- View recipe details
- Create new recipes
- Edit recipes
- Delete recipes
- Filter by cuisine

### Ingredient Management
- Browse ingredients
- Search ingredients
- Track ingredient information

### Shopping Lists
- Create shopping lists
- Add items to lists
- Remove items
- Mark items as purchased
- View shopping lists

### Additional Features
- Responsive design (mobile & desktop)
- Error handling & validation
- Toast notifications
- Loading states
- Protected routes

## 📁 Project Structure

```
recipe-app/
│
├── 📁 backend/                     Spring Boot Application
│   ├── src/main/java/com/recipeapp/
│   │   ├── controller/             REST Controllers (5)
│   │   ├── dto/                    Data Transfer Objects
│   │   ├── exception/              Exception Handlers
│   │   ├── model/                  JPA Entity Models (8)
│   │   ├── repository/             Data Access Layer
│   │   ├── security/               JWT & Security Config
│   │   ├── service/                Business Logic
│   │   └── RecipeAppApplication.java
│   └── pom.xml
│
├── 📁 frontend/                    React Application
│   ├── src/
│   │   ├── components/             React Components
│   │   ├── pages/                  Page Components
│   │   ├── services/               API Client
│   │   ├── store/                  Zustand Stores
│   │   ├── hooks/                  Custom Hooks
│   │   ├── types/                  TypeScript Types
│   │   ├── styles/                 Global Styles
│   │   ├── App.tsx
│   │   └── index.tsx
│   ├── public/
│   ├── package.json
│   └── tsconfig.json
│
├── 📁 database/                    Database Files
│   └── schema.sql                  MySQL Schema
│
├── 📋 Configuration Files
│   ├── docker-compose.yml
│   ├── .gitignore
│   ├── .env.example
│   └── LICENSE
│
└── 📖 Documentation
    ├── README.md
    ├── SETUP.md
    ├── PROJECT.md (this file)
    ├── ARCHITECTURE.md
    ├── API.md
    ├── SECURITY.md
    ├── CONTRIBUTING.md
    └── CHANGELOG.md
```

## 🔌 API Overview

### Main Endpoints

**Authentication**
- `POST /auth/register` - Register new user
- `POST /auth/login` - User login
- `POST /auth/validate` - Validate token
- `POST /auth/refresh` - Refresh token

**Recipes**
- `GET /recipes` - List recipes (paginated)
- `GET /recipes/:id` - Get recipe details
- `POST /recipes` - Create recipe
- `PUT /recipes/:id` - Update recipe
- `DELETE /recipes/:id` - Delete recipe
- `GET /recipes/search` - Search recipes

**Shopping Lists**
- `GET /shopping-lists` - List shopping lists
- `POST /shopping-lists` - Create list
- `GET /shopping-lists/:id` - Get list details
- `PUT /shopping-lists/:id` - Update list
- `DELETE /shopping-lists/:id` - Delete list
- `POST /shopping-lists/:id/items` - Add item
- `DELETE /shopping-lists/items/:id` - Delete item

**Other Resources**
- `/cuisines` - Cuisine management
- `/ingredients` - Ingredient management
- `/units` - Unit management

See `API.md` for complete endpoint documentation.

## 🗄️ Database Schema

### Entity Models

1. **User** - User accounts and authentication
2. **Cuisine** - Recipe cuisine categories
3. **Ingredient** - Food ingredients
4. **Unit** - Measurement units (cup, tbsp, etc.)
5. **Recipe** - Recipe information and metadata
6. **RecipeIngredient** - Recipe-ingredient relationships
7. **ShoppingList** - User shopping lists
8. **ShoppingListItem** - Items in shopping lists

### Key Relationships

- User → Recipe (1:Many)
- User → ShoppingList (1:Many)
- Cuisine → Recipe (1:Many)
- Recipe → RecipeIngredient (1:Many)
- Ingredient → RecipeIngredient (1:Many)
- Unit → RecipeIngredient (1:Many)
- ShoppingList → ShoppingListItem (1:Many)

See `ARCHITECTURE.md` for detailed ER diagram.

## 🔐 Security

### Implemented
- JWT token-based authentication
- Password hashing with BCrypt
- CORS configuration
- Input validation
- Exception handling
- Secure error responses

See `SECURITY.md` for detailed security guidelines.

## 📊 Deployment

### Development
```bash
docker-compose up -d
cd backend && mvn spring-boot:run
cd frontend && npm start
```

### Production

**Docker**:
```bash
docker-compose up --build -d
```

**Cloud Hosting**:
- Backend: Deploy JAR to server
- Frontend: Deploy build to CDN

See `SETUP.md` for detailed setup instructions.

## 🎯 Development Timeline

- **Phase 1-3**: Foundation & database design ✅
- **Phase 4**: Backend REST API ✅
- **Phase 5**: Frontend infrastructure ✅
- **Phase 5B**: Component development (current)
- **Phase 6**: Testing & optimization
- **Phase 7**: Deployment & monitoring

## 📚 Documentation Files

| File | Purpose |
|------|---------|
| README.md | Main project overview |
| SETUP.md | Installation & setup guide |
| PROJECT.md | This file - project details |
| ARCHITECTURE.md | System design & architecture |
| API.md | API endpoint reference |
| SECURITY.md | Security best practices |
| CONTRIBUTING.md | Development standards |
| CHANGELOG.md | Version history |

## 🚀 Getting Started

1. **Installation**: See `SETUP.md`
2. **Architecture**: See `ARCHITECTURE.md`
3. **API Reference**: See `API.md`
4. **Contributing**: See `CONTRIBUTING.md`

## 📞 Support

For questions or issues:
1. Check relevant documentation
2. Review error messages and logs
3. Check GitHub issues
4. Create a new issue with details

## 📄 License

See `LICENSE` file for licensing information.

## 👥 Contributors

- [Your Name] - Project Lead

## 🔄 Version

**Current**: 1.0.0 (MVP)

See `CHANGELOG.md` for version history.

---

**Last Updated**: November 2025  
**Status**: Active Development
