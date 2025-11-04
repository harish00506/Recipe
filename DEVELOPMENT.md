# 🛠️ Development Guide

Guidelines for contributing and developing features for the Recipe App.

## Development Environment

### Prerequisites
- Node.js 16+
- Java 17+
- MySQL 8+
- Git
- IDE: VS Code or IntelliJ IDEA

### Initial Setup

```bash
# Clone repository
git clone <repo-url>
cd recipe-app

# Start database
docker-compose up -d

# Backend setup
cd backend
mvn clean install

# Frontend setup
cd ../frontend
npm install
```

## Running Development Server

### All Services (3 terminals)

**Terminal 1 - Database**:
```bash
docker-compose up -d
```

**Terminal 2 - Backend**:
```bash
cd backend
mvn spring-boot:run
```

**Terminal 3 - Frontend**:
```bash
cd frontend
npm start
```

## Project Structure

### Backend (Spring Boot)

```
backend/src/main/java/com/recipeapp/
├── controller/          REST Controllers
├── dto/                 Data Transfer Objects
├── exception/           Exception Handlers
├── model/               JPA Entity Models
├── repository/          Data Access Layer
├── security/            JWT & Security
├── service/             Business Logic
└── RecipeAppApplication.java
```

### Frontend (React)

```
frontend/src/
├── components/          React Components
├── pages/               Page Components
├── services/            API Client
├── store/               Zustand Stores
├── hooks/               Custom Hooks
├── types/               TypeScript Definitions
├── styles/              Global CSS
├── App.tsx
└── index.tsx
```

## Code Standards

### Java Backend

#### Naming Conventions
- Classes: PascalCase (e.g., `UserController`)
- Methods: camelCase (e.g., `getUserById`)
- Variables: camelCase (e.g., `userName`)
- Constants: UPPER_SNAKE_CASE (e.g., `MAX_ATTEMPTS`)

#### File Organization
```java
public class UserController {
    // 1. Dependencies/Autowired fields
    @Autowired
    private UserService userService;

    // 2. Constructor
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 3. Public methods
    @GetMapping
    public List<UserDTO> getAllUsers() {
        // implementation
    }

    // 4. Private helper methods
    private void validateInput() {
        // implementation
    }
}
```

#### Annotations
- Use `@Slf4j` for logging
- Use `@Validated` for validation
- Use `@Cacheable` for caching
- Use `@Async` for async operations

### TypeScript Frontend

#### Naming Conventions
- Components: PascalCase (e.g., `UserCard.tsx`)
- Files: kebab-case (e.g., `user-card.tsx`)
- Interfaces: PascalCase with `I` prefix (e.g., `IUser`)
- Functions: camelCase (e.g., `getUserById`)
- Constants: UPPER_SNAKE_CASE (e.g., `MAX_ATTEMPTS`)

#### File Organization
```typescript
// 1. Imports
import React, { useState } from 'react';
import { useAuth } from '@hooks';
import { User } from '@types';
import './user-card.css';

// 2. Type definitions
interface IUserCardProps {
  user: User;
  onSelect?: (user: User) => void;
}

// 3. Component
const UserCard: React.FC<IUserCardProps> = ({ user, onSelect }) => {
  const [isSelected, setIsSelected] = useState(false);

  // handlers, effects, etc.

  return (
    <div>
      {/* JSX */}
    </div>
  );
};

// 4. Exports
export default UserCard;
```

## Git Workflow

### Commit Message Format

```
<type>(<scope>): <subject>

<body>

<footer>
```

### Types
- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation
- `style`: Code style (no logic change)
- `refactor`: Code refactoring
- `test`: Tests
- `chore`: Build, dependencies, etc.

### Examples

```bash
git commit -m "feat(recipe): add recipe search functionality"
git commit -m "fix(auth): resolve JWT token expiration issue"
git commit -m "docs(setup): update installation instructions"
git commit -m "refactor(store): optimize state management"
```

### Branch Naming

```
feature/<feature-name>
fix/<issue-description>
docs/<document-name>
refactor/<component-name>
```

### Workflow

```bash
# 1. Create branch
git checkout -b feature/user-profiles

# 2. Make changes and commit
git add .
git commit -m "feat(users): add user profile page"

# 3. Push to remote
git push origin feature/user-profiles

# 4. Create Pull Request on GitHub
# Fill in PR template with description, tests, etc.

# 5. After review and approval, merge
git checkout main
git pull origin main
git merge feature/user-profiles
git push origin main
```

## API Development

### Adding a New Endpoint

#### 1. Create DTO (if needed)
```java
// UserDTO.java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
}
```

#### 2. Create Controller Endpoint
```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
    
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(userService.createUser(userDTO));
    }
}
```

#### 3. Create Service
```java
@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    private UserDTO convertToDTO(User user) {
        return new UserDTO(user.getId(), user.getEmail(), ...);
    }
}
```

#### 4. Create Repository
```java
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
```

### Testing Endpoints

**Using curl**:
```bash
# GET
curl http://localhost:8080/api/users

# POST
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"email":"user@example.com","firstName":"John"}'

# GET with ID
curl http://localhost:8080/api/users/1

# PUT
curl -X PUT http://localhost:8080/api/users/1 \
  -H "Content-Type: application/json" \
  -d '{"firstName":"Jane"}'

# DELETE
curl -X DELETE http://localhost:8080/api/users/1
```

**Using Postman**:
1. Create collection "Recipe App API"
2. Add requests for each endpoint
3. Set up environment variables for base URL
4. Use pre-request scripts for auth tokens
5. Export collection for team use

## Frontend Development

### Adding a New Page

#### 1. Create Page Component
```typescript
// frontend/src/pages/UserProfilePage.tsx
import React from 'react';
import { useParams } from 'react-router-dom';
import { useAuth } from '@hooks';

interface IUserProfilePageProps {}

const UserProfilePage: React.FC<IUserProfilePageProps> = () => {
  const { id } = useParams<{ id: string }>();
  const { user } = useAuth();

  return (
    <div className="container">
      <h1>User Profile</h1>
      {/* Component content */}
    </div>
  );
};

export default UserProfilePage;
```

#### 2. Add Route in App.tsx
```typescript
import UserProfilePage from './pages/UserProfilePage';

const App: React.FC = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route element={<Layout />}>
          <Route path="/profile/:id" element={<UserProfilePage />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
};
```

#### 3. Create API Service Method
```typescript
// frontend/src/services/apiClient.ts
export class APIClient {
  async getUserProfile(id: string): Promise<User> {
    return this.get(`/users/${id}`);
  }
}
```

#### 4. Use in Component
```typescript
const UserProfilePage: React.FC = () => {
  const [user, setUser] = useState<User | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchProfile = async () => {
      try {
        const data = await apiClient.getUserProfile(id);
        setUser(data);
      } catch (error) {
        console.error('Failed to fetch profile', error);
      } finally {
        setLoading(false);
      }
    };

    fetchProfile();
  }, [id]);

  if (loading) return <div>Loading...</div>;
  if (!user) return <div>User not found</div>;

  return <div>{user.firstName} {user.lastName}</div>;
};
```

## Testing

### Backend Unit Tests

```bash
cd backend
mvn test
```

### Frontend Unit Tests

```bash
cd frontend
npm test
```

### E2E Tests (if set up)

```bash
npm run e2e
```

## Building for Production

### Backend

```bash
cd backend
mvn clean package
# Creates: backend/target/recipe-app.jar
```

### Frontend

```bash
cd frontend
npm run build
# Creates: frontend/build/
```

### Docker

```bash
docker-compose up --build -d
```

## Troubleshooting

### Backend Issues

**Port 8080 in use**:
```bash
# Find and kill process
lsof -ti:8080 | xargs kill -9
```

**Database connection error**:
```bash
# Check MySQL is running
docker-compose ps

# Restart MySQL
docker-compose restart mysql
```

**Maven build fails**:
```bash
# Clear cache
mvn clean
mvn install
```

### Frontend Issues

**Port 3000 in use**:
```bash
# Kill process on port 3000
lsof -ti:3000 | xargs kill -9
```

**Module not found**:
```bash
# Clear cache and reinstall
rm -rf node_modules package-lock.json
npm install
```

**TypeScript errors**:
```bash
# Check TypeScript compilation
npx tsc --noEmit
```

## Performance Optimization

### Backend
- Use caching (@Cacheable)
- Index frequently queried columns
- Implement pagination
- Use DTOs to limit data transfer

### Frontend
- Code splitting with React.lazy()
- Image optimization
- Memoization with React.memo()
- Lazy loading of components

## Security Best Practices

- Never commit `.env` files
- Use environment variables for secrets
- Validate all user inputs
- Use HTTPS in production
- Keep dependencies updated
- Use prepared statements (prevent SQL injection)
- Implement proper CORS
- Use strong password hashing

## Code Review Checklist

- [ ] Code follows naming conventions
- [ ] No hardcoded values/secrets
- [ ] Proper error handling
- [ ] Adequate comments for complex logic
- [ ] Tests included (if applicable)
- [ ] No console.log in production code
- [ ] Performance considered
- [ ] Security best practices followed
- [ ] Documentation updated

## Resources

- [Spring Boot Docs](https://spring.io/projects/spring-boot)
- [React Docs](https://react.dev)
- [TypeScript Handbook](https://www.typescriptlang.org/docs/)
- [Git Documentation](https://git-scm.com/doc)
- [REST API Best Practices](https://restfulapi.net/)

## Questions?

- Check existing code for patterns
- Review pull requests for examples
- Ask in team chat or create an issue
- Update this guide with new learnings

---

**Last Updated**: November 2025  
**Maintained by**: Development Team
