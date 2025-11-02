# 🤝 Contributing to Recipe App

Thank you for your interest in contributing to the Recipe App! We welcome contributions from everyone. This document provides guidelines and instructions for contributing.

## 📋 Table of Contents
- [Code of Conduct](#code-of-conduct)
- [Getting Started](#getting-started)
- [Development Setup](#development-setup)
- [Making Changes](#making-changes)
- [Commit Guidelines](#commit-guidelines)
- [Pull Request Process](#pull-request-process)
- [Coding Standards](#coding-standards)
- [Testing](#testing)
- [Documentation](#documentation)

---

## 📖 Code of Conduct

This project and everyone participating in it is governed by our [Code of Conduct](CODE_OF_CONDUCT.md). By participating, you are expected to uphold this code. Please report unacceptable behavior to [project maintainers].

---

## 🚀 Getting Started

### Prerequisites
- Java 21 or higher
- Node.js 18.x or higher
- PostgreSQL 15 or higher
- Maven 3.9.0 or higher
- Git
- A GitHub account

### Fork and Clone
1. Fork the repository on GitHub
2. Clone your fork locally:
   ```bash
   git clone https://github.com/YOUR-USERNAME/recipe-app.git
   cd recipe-app
   ```
3. Add the upstream repository:
   ```bash
   git remote add upstream https://github.com/ORIGINAL-OWNER/recipe-app.git
   ```

---

## 💻 Development Setup

### Backend Setup
```bash
cd backend
mvn clean install
mvn spring-boot:run
```

### Frontend Setup
```bash
cd frontend
npm install
npm run dev
```

### Database Setup
```bash
# Create the database
createdb recipe_app

# Run migrations
psql -U postgres -d recipe_app -f ../database/schema.sql
```

---

## ✏️ Making Changes

### Create a Branch
Create a new branch for your work:
```bash
git checkout -b feature/your-feature-name
# or
git checkout -b fix/your-bug-fix-name
```

**Branch naming conventions:**
- `feature/` for new features
- `fix/` for bug fixes
- `docs/` for documentation updates
- `refactor/` for code refactoring
- `test/` for adding tests

### Make Your Changes
1. Make your changes to the codebase
2. Keep changes focused and atomic
3. Follow the [Coding Standards](#coding-standards) section
4. Add tests for new functionality
5. Update documentation as needed

---

## 📝 Commit Guidelines

### Commit Message Format
We follow the [Conventional Commits](https://www.conventionalcommits.org/) specification:

```
<type>(<scope>): <subject>

<body>

<footer>
```

### Types
- **feat:** A new feature
- **fix:** A bug fix
- **docs:** Documentation only changes
- **style:** Changes that don't affect code meaning (formatting, semicolons, etc.)
- **refactor:** Code change that neither fixes a bug nor adds a feature
- **perf:** Code change that improves performance
- **test:** Adding missing tests or updating tests
- **chore:** Changes to build process, dependencies, or tooling

### Examples
```
feat(recipe): add ingredient search functionality

fix(shopping-list): correct ingredient consolidation algorithm

docs(readme): update setup instructions

test(recipe-service): add unit tests for recipe validation
```

---

## 🔄 Pull Request Process

### Before Submitting
1. Update your branch with the latest changes from upstream:
   ```bash
   git fetch upstream
   git rebase upstream/main
   ```
2. Run tests locally:
   ```bash
   # Backend
   cd backend && mvn test
   
   # Frontend
   cd frontend && npm test
   ```
3. Build the project:
   ```bash
   # Backend
   cd backend && mvn clean install
   
   # Frontend
   cd frontend && npm run build
   ```

### Submit Your PR
1. Push your branch to your fork:
   ```bash
   git push origin your-branch-name
   ```
2. Create a Pull Request on GitHub using the [PR template](.github/PULL_REQUEST_TEMPLATE.md)
3. Ensure the PR title follows the commit message format
4. Link related issues using "Fixes #123" syntax
5. Wait for CI/CD checks to pass
6. Request review from maintainers

### Review Process
- At least one maintainer review required
- All CI/CD checks must pass
- No merge conflicts allowed
- Documentation must be updated if necessary
- Tests must be added/updated for new features

### After Merge
- Your branch will be automatically deleted
- Thank you for your contribution! 🎉

---

## 📋 Coding Standards

### Java Backend
- **Naming Conventions:**
  - Classes: PascalCase (e.g., `RecipeService`)
  - Methods: camelCase (e.g., `createRecipe()`)
  - Constants: UPPER_SNAKE_CASE (e.g., `MAX_RECIPE_SIZE`)
  - Variables: camelCase (e.g., `recipeList`)

- **Code Style:**
  - Use meaningful variable names
  - Keep methods small and focused
  - Maximum line length: 120 characters
  - Use 4 spaces for indentation
  - Add Javadoc for public methods

- **Use Annotations:**
  - `@Override` when overriding methods
  - `@Deprecated` for deprecated methods
  - Lombok annotations for getters/setters

### TypeScript/React Frontend
- **Naming Conventions:**
  - Components: PascalCase (e.g., `RecipeForm`)
  - Files: PascalCase for components, camelCase for utilities
  - Functions: camelCase (e.g., `handleSubmit()`)
  - Constants: UPPER_SNAKE_CASE or PascalCase for exported constants

- **Code Style:**
  - Use ESLint configuration provided
  - Use Prettier for code formatting
  - Use TypeScript strict mode
  - Add JSDoc comments for complex functions

- **React Best Practices:**
  - Use functional components with hooks
  - Use TypeScript interfaces/types
  - Avoid prop drilling (use Context API or Redux)
  - Memoize components when necessary

### General
- No commented-out code
- No console.log statements in production code
- No secrets or credentials in code
- DRY principle: Don't Repeat Yourself
- KISS principle: Keep It Simple, Stupid

---

## 🧪 Testing

### Backend Testing
```bash
cd backend

# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=RecipeServiceTest

# Run with coverage
mvn test jacoco:report

# View coverage report
open target/site/jacoco/index.html
```

### Frontend Testing
```bash
cd frontend

# Run all tests
npm test

# Run tests in watch mode
npm test -- --watch

# Run with coverage
npm test -- --coverage

# View coverage report
open coverage/lcov-report/index.html
```

### Testing Requirements
- Minimum 80% code coverage for new code
- Unit tests for all public methods
- Integration tests for API endpoints
- Component tests for UI components
- Test naming: `testXxx` or `xxxTest`

---

## 📚 Documentation

### Code Documentation
- Add comments for complex algorithms
- Document public APIs with Javadoc (Java)
- Add JSDoc comments (TypeScript)
- Keep comments up-to-date with code changes

### Project Documentation
- Update README.md for feature changes
- Update API.md for endpoint changes
- Update ARCHITECTURE.md for structural changes
- Add migration guide if there are breaking changes
- Update CHANGELOG.md in your PR

### Documentation Format
- Use clear, concise language
- Include examples where helpful
- Use code blocks for code examples
- Use lists for multiple items
- Include diagrams for architecture

---

## 🐛 Bug Reports

### Before Reporting
- Check existing issues
- Read the documentation
- Ensure your environment is set up correctly

### Reporting
Use the [bug report template](.github/ISSUE_TEMPLATE/bug_report.md):
- Provide a clear title
- Include steps to reproduce
- Include screenshots if applicable
- Include system information

---

## 💡 Feature Requests

### Before Requesting
- Check existing features
- Read the ROADMAP.md
- Consider if it aligns with project goals

### Requesting
Use the [feature request template](.github/ISSUE_TEMPLATE/feature_request.md):
- Provide a clear description
- Explain the problem it solves
- Include acceptance criteria
- Include mockups if applicable

---

## ❓ Questions?

- Open an issue with the `question` label
- Check existing discussions
- Review the documentation
- Contact maintainers

---

## 📝 Additional Notes

### Licensing
By contributing to Recipe App, you agree that your contributions will be licensed under its MIT License.

### Attribution
Contributors will be recognized in:
- CONTRIBUTORS.md
- Release notes
- GitHub contributor stats

### Code of Conduct Violations
Violations should be reported to [maintainer email]. All reports will be reviewed and investigated.

---

Thank you for contributing to Recipe App! 🙏

**Last Updated:** November 2, 2025
