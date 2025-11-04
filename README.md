# 🍳 Recipe App

A modern, full-stack recipe management application. Browse, search, create, and manage recipes. Automatically generate shopping lists from selected recipes.

## 🎯 Quick Links

- **[Setup Guide](SETUP.md)** - Get started in 5 minutes
- **[Project Details](PROJECT.md)** - Full project overview
- **[API Documentation](API.md)** - REST API reference
- **[Architecture](ARCHITECTURE.md)** - System design
- **[Security](SECURITY.md)** - Security guidelines
- **[Contributing](CONTRIBUTING.md)** - Development standards

## ⚡ Quick Start

### Prerequisites
- Node.js 16+
- Java 17+
- MySQL 8+
- Docker (recommended)

### Installation

```bash
# Clone repository
git clone <repo-url>
cd recipe-app

# Start database
docker-compose up -d

# Start backend (Terminal 1)
cd backend
mvn spring-boot:run

# Start frontend (Terminal 2)
cd frontend
npm install
npm start
```

**Access**:
- Frontend: http://localhost:3000
- Backend: http://localhost:8080

See [SETUP.md](SETUP.md) for detailed setup instructions.

## ✨ Features

### Recipe Management
- Browse and search recipes
- Create, edit, and delete recipes
- Filter by cuisine
- View recipe details (ingredients, instructions)
- Responsive design (mobile & desktop)

### Shopping Lists
- Create shopping lists
- Add recipes to lists
- Manage items and quantities
- Mark items as purchased

### Authentication
- User registration and login
- Secure JWT authentication
- Session management

## 🏗️ Architecture

```
React Frontend (TypeScript + Tailwind)
          ↕ (REST API + JWT)
Spring Boot Backend (Java + JPA)
          ↕ (JDBC)
MySQL Database (8 entities)
```

**Components**:
- **Backend**: 5 Controllers, 40+ Endpoints
- **Frontend**: 10+ Components, 3 Zustand Stores
- **Database**: 8 JPA Entity Models

See [ARCHITECTURE.md](ARCHITECTURE.md) for detailed architecture.

## � Project Structure

```
recipe-app/
├── backend/                 Spring Boot API
├── frontend/                React Application
├── database/                Database Schema
├── docker-compose.yml       Docker Configuration
├── README.md                This file
├── SETUP.md                 Setup Instructions
├── PROJECT.md               Project Details
├── API.md                   API Documentation
├── ARCHITECTURE.md          System Design
├── SECURITY.md              Security Guidelines
├── CONTRIBUTING.md          Development Standards
└── CHANGELOG.md             Version History
```

## 🔌 API Overview

### Authentication
```
POST   /auth/register      Register new user
POST   /auth/login         User login
```

### Recipes
```
GET    /recipes            List recipes
GET    /recipes/:id        Get recipe details
POST   /recipes            Create recipe
PUT    /recipes/:id        Update recipe
DELETE /recipes/:id        Delete recipe
GET    /recipes/search     Search recipes
```

### Shopping Lists
```
GET    /shopping-lists              List lists
POST   /shopping-lists              Create list
PUT    /shopping-lists/:id/items    Manage items
DELETE /shopping-lists/:id          Delete list
```

See [API.md](API.md) for complete API documentation.

## 🗄️ Database

8 Entity Models:
- User, Cuisine, Ingredient, Unit
- Recipe, RecipeIngredient
- ShoppingList, ShoppingListItem

See [ARCHITECTURE.md](ARCHITECTURE.md) for ER diagram.

## 🔐 Security

- JWT token authentication
- Password hashing (BCrypt)
- CORS configuration
- Input validation
- Error handling

See [SECURITY.md](SECURITY.md) for security guidelines.

## 🚀 Development

### Running Development Server

```bash
# Backend
cd backend && mvn spring-boot:run

# Frontend
cd frontend && npm start
```

### Building for Production

```bash
# Backend
cd backend && mvn clean package

# Frontend
cd frontend && npm run build
```

### Docker Deployment

```bash
docker-compose up --build -d
```

See [SETUP.md](SETUP.md) for detailed instructions.

## 📚 Documentation

| Document | Purpose |
|----------|---------|
| [SETUP.md](SETUP.md) | Installation & configuration |
| [PROJECT.md](PROJECT.md) | Project overview & features |
| [API.md](API.md) | REST API reference |
| [ARCHITECTURE.md](ARCHITECTURE.md) | System design |
| [SECURITY.md](SECURITY.md) | Security best practices |
| [CONTRIBUTING.md](CONTRIBUTING.md) | Development guidelines |
| [CHANGELOG.md](CHANGELOG.md) | Version history |

## 🛠️ Tech Stack

### Backend
- Spring Boot 3.x
- Java 17+
- MySQL 8.x
- JPA/Hibernate
- JWT Authentication

### Frontend
- React 18.2+
- TypeScript 4.9+
- Tailwind CSS 3.3+
- Zustand (state management)
- Axios (HTTP client)

### Infrastructure
- Docker & Docker Compose
- Git Version Control

## 📊 Status

| Phase | Status | Details |
|-------|--------|---------|
| Backend | ✅ Complete | 40+ REST endpoints |
| Frontend | ✅ Complete | Modern React setup |
| Components | 🔄 In Progress | Building UI components |
| Testing | ⏳ Pending | Unit & integration tests |
| Deployment | ⏳ Pending | Cloud deployment |

## 📝 License

See [LICENSE](LICENSE) for licensing information.

## 👥 Contributing

Contributions welcome! See [CONTRIBUTING.md](CONTRIBUTING.md) for guidelines.

## 📞 Support

- Check [SETUP.md](SETUP.md) for common issues
- Review [API.md](API.md) for API questions
- See [ARCHITECTURE.md](ARCHITECTURE.md) for design questions

## 🔄 Version

**1.0.0** - Initial Release

See [CHANGELOG.md](CHANGELOG.md) for version history.