# 🚀 Quick Start Guide - Recipe App

## ⚡ 5-Minute Setup

### Prerequisites
- Java 21
- Node.js 18+
- PostgreSQL 15+
- Maven 3.9+
- Git

### Quick Setup Commands

```bash
# 1. Clone repository
git clone https://github.com/YOUR-USERNAME/recipe-app.git
cd recipe-app

# 2. Start with Docker (Easiest)
docker-compose up -d

# Or manual setup:

# 3A. Backend Setup
cd backend
mvn clean install
mvn spring-boot:run
# Runs on http://localhost:8080

# 3B. Frontend Setup (in new terminal)
cd frontend
npm install
npm run dev
# Runs on http://localhost:5173

# 4. Database Setup
createdb recipe_app
psql -U postgres -d recipe_app -f database/schema.sql
```

---

## 📚 Documentation Quick Links

| Document | Purpose |
|----------|---------|
| [README.md](README.md) | Project overview |
| [PROJECT_STATEMENT.md](PROJECT_STATEMENT.md) | Requirements & user stories |
| [CONTRIBUTING.md](CONTRIBUTING.md) | How to contribute |
| [GITHUB_PUSH_GUIDE.md](GITHUB_PUSH_GUIDE.md) | Detailed push instructions |
| [CHANGELOG.md](CHANGELOG.md) | Version history |
| [LICENSE](LICENSE) | MIT License |

---

## 🎯 Key Files

```
recipe-app/
├── PROJECT_STATEMENT.md      ← Start here for requirements
├── README.md                 ← Project overview
├── CONTRIBUTING.md           ← Contribution guidelines
├── GITHUB_PUSH_GUIDE.md      ← Push instructions
├── docker-compose.yml        ← Docker setup
├── .env.example              ← Configuration template
├── .gitignore                ← Git ignore rules
├── backend/                  ← Spring Boot
├── frontend/                 ← React
├── database/schema.sql       ← Database schema
└── .github/                  ← GitHub workflows & templates
```

---

## 🔄 Common Commands

### Backend
```bash
cd backend
mvn clean install          # Build
mvn spring-boot:run        # Run
mvn test                   # Test
mvn jacoco:report          # Coverage
```

### Frontend
```bash
cd frontend
npm install                # Install
npm run dev               # Development
npm run build             # Production build
npm test                  # Test
npm run lint              # Lint
```

### Git
```bash
git add .                 # Stage all
git commit -m "message"   # Commit
git push origin main      # Push
git pull origin main      # Pull
```

---

## 🐛 Troubleshooting

**Port already in use:**
```bash
# Kill process on port 8080 (Java)
lsof -ti:8080 | xargs kill -9

# Or use different port in .env
```

**Database connection error:**
```bash
# Ensure PostgreSQL is running
psql -U postgres -c "SELECT 1"

# Create database if not exists
createdb recipe_app
```

**Frontend build error:**
```bash
cd frontend
rm -rf node_modules package-lock.json
npm install
```

---

## 📞 Need Help?

- **Questions:** Open an issue
- **Bug Report:** Use bug report template
- **Feature Request:** Use feature request template
- **Security Issues:** Email security@recipeapp.com

---

**Last Updated:** November 2, 2025
