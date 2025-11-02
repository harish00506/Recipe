# 🍳 Recipe App

A modern, full-stack recipe management platform built with React, Spring Boot, and PostgreSQL. Enables users to store, search, and organize recipes while automatically generating shopping lists.

## 📖 Project Overview

**For detailed project requirements and specifications, see [PROJECT_STATEMENT.md](PROJECT_STATEMENT.md)**

A digital platform enabling users to:
- 📝 **Store recipes** with ingredients, instructions, and cuisines
- 🔍 **Search and filter** recipes by ingredients and cuisine types
- 🛒 **Generate shopping lists** from selected recipes
- 📋 **Manage ingredients** with quantity consolidation
- 💾 **Export and share** shopping lists

---

## ✨ Key Features

### Store Recipes
- ✅ Add recipes with name, servings, and preparation time
- ✅ Input multiple ingredients with quantities and units
- ✅ Add step-by-step cooking instructions
- ✅ Specify cuisine type for each recipe
- ✅ Save recipes to personal collection
- ✅ Edit existing recipes
- ✅ Upload recipe images

### Search & Filter
- ✅ Search recipes by name or ingredients
- ✅ Filter recipes by cuisine type
- ✅ Search by single or multiple ingredients
- ✅ Real-time search results
- ✅ Sort by preparation time or name
- ✅ Advanced filtering options

### Shopping Lists
- ✅ Select multiple recipes for shopping list
- ✅ Automatically combine ingredients
- ✅ Consolidate duplicate ingredients
- ✅ Modify quantities in the list
- ✅ Export to PDF or CSV
- ✅ Share shopping lists via link
- ✅ Checklist functionality

---

## 🏗️ Technology Stack

### **Backend**
- **Framework:** Spring Boot 3.5.1
- **Language:** Java 21
- **Database:** PostgreSQL 15+
- **ORM:** Hibernate/JPA
- **ID Generation:** UUID (gen_random_uuid)
- **Authentication:** JWT + Spring Security
- **Mapping:** MapStruct
- **Testing:** JUnit 5, Mockito

### **Frontend**
- **Framework:** React 18.x
- **Language:** TypeScript
- **State Management:** Context API or Redux
- **HTTP Client:** Axios
- **Styling:** Tailwind CSS
- **UI Components:** Material-UI or NextUI
- **Testing:** Jest, React Testing Library

### **DevOps**
- **Build Tool:** Maven
- **Version Control:** Git
- **CI/CD:** GitHub Actions
- **Containerization:** Docker
- **Package Manager:** npm

---

## 📂 Project Structure

```
recipe-app/
├── frontend/                                # React Frontend
│   ├── src/
│   │   ├── components/
│   │   ├── pages/
│   │   ├── services/
│   │   ├── App.tsx
│   │   └── main.tsx
│   ├── package.json
│   ├── vite.config.ts
│   └── tsconfig.json
│
├── backend/                                 # Spring Boot Backend
│   ├── src/main/java/com/recipeapp/
│   │   ├── entity/
│   │   ├── dto/
│   │   ├── controller/
│   │   ├── service/
│   │   ├── repository/
│   │   ├── security/
│   │   ├── config/
│   │   └── RecipeAppApplication.java
│   ├── pom.xml
│   └── application.properties
│
├── database/
│   └── schema.sql
│
├── docs/
│   ├── PROJECT_STATEMENT.md
│   ├── DOCUMENTATION.md
│   ├── API.md
│   ├── CONTRIBUTING.md
│   └── ARCHITECTURE.md
│
├── .github/
│   ├── workflows/
│   ├── ISSUE_TEMPLATE/
│   └── PULL_REQUEST_TEMPLATE.md
│
├── README.md
├── CHANGELOG.md
├── LICENSE
├── .gitignore
├── .env.example
├── docker-compose.yml
└── setup.sh/setup.bat
```

## Getting Started

### Prerequisites

- Java 11 or higher
- Node.js and npm
- PostgreSQL

### Installation

1. Clone the repository:
   ```
   git clone <repository-url>
   cd recipe-app
   ```

2. Set up the backend:
   - Navigate to the `backend` directory.
   - Update the `application.properties` file with your PostgreSQL database credentials.
   - Build and run the Spring Boot application:
     ```
     ./mvnw spring-boot:run
     ```

3. Set up the frontend:
   - Navigate to the `frontend` directory.
   - Install the dependencies:
     ```
     npm install
     ```
   - Start the React application:
     ```
     npm start
     ```

### Usage

- Access the application in your web browser at `http://localhost:3000`.
- The main page will display a "Hello World" message.

## Contributing

Feel free to submit issues or pull requests for improvements or features.