# 🚀 Project Setup Guide

**Quick start guide to get the Recipe App running locally.**

## Prerequisites

- **Node.js** 16+ (frontend)
- **Java** 17+ (backend)
- **MySQL** 8+ (database)
- **Docker** & **Docker Compose** (optional, recommended)
- **Git** (version control)

## Installation

### 1. Clone Repository

```bash
git clone <repository-url>
cd recipe-app
```

### 2. Start Database

Using Docker (recommended):
```bash
docker-compose up -d
```

This will start MySQL and create the database using `database/schema.sql`.

**Or manually**:
- Install MySQL
- Create database: `recipe_db`
- Run: `mysql -u root -p recipe_db < database/schema.sql`

### 3. Start Backend (Spring Boot)

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

Backend will run on: `http://localhost:8080`

Check health: `curl http://localhost:8080/health`

### 4. Start Frontend (React)

```bash
cd frontend
npm install
npm start
```

Frontend will run on: `http://localhost:3000`

### 5. Access Application

- **Frontend**: http://localhost:3000
- **Backend API**: http://localhost:8080
- **Database**: localhost:3306

## Configuration

### Backend Configuration

**File**: `backend/src/main/resources/application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/recipe_db
spring.datasource.username=root
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update
```

### Frontend Configuration

**Files**: 
- `frontend/.env` (development)
- `frontend/.env.production` (production)

```env
REACT_APP_API_URL=http://localhost:8080
REACT_APP_ENV=development
```

## Development Workflow

### Running All Services

**Terminal 1 - Database:**
```bash
docker-compose up -d
```

**Terminal 2 - Backend:**
```bash
cd backend
mvn spring-boot:run
```

**Terminal 3 - Frontend:**
```bash
cd frontend
npm start
```

### Building for Production

**Backend:**
```bash
cd backend
mvn clean package
# Creates JAR: backend/target/recipe-app.jar
```

**Frontend:**
```bash
cd frontend
npm run build
# Creates build folder: frontend/build/
```

### Running Production Build

**Backend:**
```bash
java -jar backend/target/recipe-app.jar
```

**Frontend** (using static server):
```bash
npm install -g serve
serve -s frontend/build
```

## Docker Deployment

### Build Docker Image

```bash
docker-compose up --build -d
```

### View Logs

```bash
docker-compose logs -f backend
docker-compose logs -f mysql
```

### Stop Services

```bash
docker-compose down
```

## Troubleshooting

### Port Already in Use

**Frontend (3000)**:
```bash
# Kill process on port 3000
lsof -ti:3000 | xargs kill -9
```

**Backend (8080)**:
```bash
# Kill process on port 8080
lsof -ti:8080 | xargs kill -9
```

**MySQL (3306)**:
```bash
docker-compose down
docker-compose up -d
```

### Database Connection Error

1. Verify MySQL is running
2. Check credentials in `application.properties`
3. Ensure database schema is created:
   ```bash
   mysql -u root -p recipe_db < database/schema.sql
   ```

### Frontend Build Error

```bash
# Clear cache and reinstall
rm -rf frontend/node_modules frontend/package-lock.json
cd frontend
npm install
npm start
```

### Backend Compilation Error

```bash
# Clean and rebuild
cd backend
mvn clean
mvn compile
mvn install
```

## Testing

### API Testing

Use the included `API_TESTING_GUIDE.md` or:

```bash
curl -X GET http://localhost:8080/api/recipes
```

### Frontend Testing

```bash
cd frontend
npm test
```

## Environment Variables

### Backend (application.properties)

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/recipe_db
spring.datasource.username=root
spring.datasource.password=password
spring.jpa.show-sql=false
spring.jpa.hibernate.ddl-auto=update
server.port=8080
```

### Frontend (.env)

```env
REACT_APP_API_URL=http://localhost:8080
REACT_APP_ENV=development
REACT_APP_ENABLE_DEBUG=true
```

## Project Structure

```
recipe-app/
├── backend/              Spring Boot API
├── frontend/             React application
├── database/             Database schema
├── docker-compose.yml    Docker setup
├── README.md             Project overview
├── API.md                API documentation
├── ARCHITECTURE.md       System design
├── SECURITY.md           Security guidelines
└── SETUP.md             This file
```

## Next Steps

1. **Read ARCHITECTURE.md** - Understand system design
2. **Check API.md** - Learn available endpoints
3. **Review SECURITY.md** - Understand security best practices
4. **Start developing** - Follow CONTRIBUTING.md guidelines

## Support

For issues or questions:
1. Check troubleshooting section above
2. Review relevant documentation files
3. Check git history: `git log --oneline`
4. Open an issue in the repository

## Additional Resources

- **Frontend**: See `FRONTEND_SETUP.md` for detailed React setup
- **Backend**: See `backend/README.md` for Spring Boot details
- **Testing**: See `API_TESTING_GUIDE.md` for API testing
- **Development**: See `CONTRIBUTING.md` for development standards

---

**Last Updated**: November 2025  
**Status**: Production Ready
