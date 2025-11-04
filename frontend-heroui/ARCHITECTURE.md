# Frontend Architecture & File Structure

## Complete Directory Tree

```
frontend-heroui/
├── 📄 Configuration Files
│   ├── package.json           # Dependencies, scripts
│   ├── tsconfig.json          # TypeScript strict mode config
│   ├── tsconfig.node.json     # Node.js TypeScript config
│   ├── vite.config.ts         # Vite build configuration
│   ├── tailwind.config.ts     # Tailwind CSS v4 + HeroUI preset
│   ├── postcss.config.js      # PostCSS with Tailwind & autoprefixer
│   ├── eslint.config.js       # ESLint strict rules
│   ├── .prettierrc            # Prettier formatting
│   ├── .env                   # Environment variables
│   └── .gitignore            # Git ignore patterns
│
├── 📋 Documentation
│   ├── README.md              # Setup & usage guide
│   └── DELIVERY_SUMMARY.md    # Complete feature delivery
│
├── 🌐 Public Assets
│   └── index.html             # HTML entry point
│
└── 📦 Source Code (src/)
    ├── 🎨 Components (Reusable)
    │   └── layout/
    │       ├── AppLayout.tsx          # Main layout wrapper with theme support
    │       └── AppNavbar.tsx           # Top navbar with theme toggle, user menu
    │
    ├── 🎯 Features (Feature-specific)
    │   ├── auth/
    │   │   └── pages/
    │   │       ├── SignInPage.tsx      # Email/password login form
    │   │       └── SignUpPage.tsx      # Registration form with validation
    │   │
    │   ├── dashboard/
    │   │   └── pages/
    │   │       └── DashboardPage.tsx   # Main dashboard with stats, tables, filters
    │   │
    │   └── settings/
    │       └── pages/
    │           └── SettingsPage.tsx    # Settings with profile, preferences, theme tabs
    │
    ├── 🪝 Hooks (Custom React Hooks)
    │   └── useAuth.ts                  # Authentication state & methods
    │
    ├── 📚 Libraries (Utilities & API)
    │   └── api-client.ts               # Fetch-based API client with auth
    │
    ├── 🎨 Styles (Global & Theme)
    │   ├── globals.css                 # Tailwind imports, base styles
    │   └── hero.ts                     # HeroUI theme configuration
    │
    ├── 📝 Types (TypeScript Definitions)
    │   └── index.ts                    # All interfaces & types
    │
    ├── 🚀 App Entry
    │   ├── App.tsx                     # Main router with protected routes
    │   └── main.tsx                    # Entry point with HeroUIProvider
    │
    └── 📦 Generated (after build)
        └── dist/                       # Production build output

```

## Component Hierarchy

```
<HeroUIProvider>
  <App>
    <BrowserRouter>
      <Routes>
        ├─ Public Routes
        │  ├─ /auth/signin → <SignInPage />
        │  └─ /auth/signup → <SignUpPage />
        │
        └─ Protected Routes (ProtectedRoute)
           └─ <AppLayout>
              ├─ <AppNavbar />         # Theme toggle, user menu
              └─ <Outlet />            # Route content
                 ├─ / → <DashboardPage />
                 └─ /settings → <SettingsPage />
```

## Data Flow

```
User Input
    ↓
Component (Form)
    ↓
useAuth Hook (Login/Register)
    ↓
API Client (fetch + Bearer token)
    ↓
Backend (Spring Boot)
    ↓
Response → localStorage (token)
    ↓
State Update → Component Re-render
```

## State Management

```
┌─────────────────────────────────────┐
│      useAuth Hook (Custom)          │
├─────────────────────────────────────┤
│ • user: User | null                 │
│ • isAuthenticated: boolean          │
│ • isLoading: boolean                │
│ • error: string | null              │
├─────────────────────────────────────┤
│ Methods:                            │
│ • login(email, password)            │
│ • register(email, password, name)   │
│ • logout()                          │
│ • clearError()                      │
└─────────────────────────────────────┘
         ↓
    Component Props
         ↓
    JSX Rendering
```

## Build Output Structure

```
dist/
├── index.html                         # 0.81 kB
├── assets/
│   ├── index-mcKPG3hU.css            # 10.54 kB (gzip: 2.95 kB)
│   ├── form-CUYAdP6I.js              # 0.03 kB (lazy-loaded)
│   ├── index-rLfDeReK.js             # 20.14 kB (routes)
│   ├── vendor-B5s8fvkc.js            # 160.79 kB (React, Router, etc)
│   └── heroui-BdU4Igdm.js            # 533.03 kB (HeroUI + deps)
└── vite.svg                          # Icon

Total: ~704 kB (gzip: ~206 kB)
Chunks: 5 (optimized code-splitting)
```

## API Client Methods

```typescript
// Authentication
await apiClient.login(email, password)
await apiClient.register(email, password, name)
await apiClient.getCurrentUser()

// Recipes (CRUD)
await apiClient.getRecipes(page, limit)
await apiClient.getRecipe(id)
await apiClient.createRecipe(recipe)
await apiClient.updateRecipe(id, recipe)
await apiClient.deleteRecipe(id)

// Shopping Lists
await apiClient.getShoppingLists()
await apiClient.getShoppingList(id)
await apiClient.createShoppingList(name)
await apiClient.deleteShoppingList(id)

// Cuisines
await apiClient.getCuisines()
```

## TypeScript Interface Examples

```typescript
interface User {
  id: string;
  email: string;
  name: string;
  avatar?: string;
  role: 'user' | 'admin';
}

interface Recipe {
  id: string;
  name: string;
  description: string;
  cuisine: string;
  prepTime: number;
  cookTime: number;
  servings: number;
  ingredients: Ingredient[];
  instructions: string;
  imageUrl?: string;
  createdAt: string;
  updatedAt: string;
}

interface Ingredient {
  id: string;
  name: string;
  quantity: number;
  unit: string;
}
```

## Theme System Details

### Light Mode (Default)
- Primary: #0ea5e9 (Sky Blue)
- Secondary: #8b5cf6 (Purple)
- Success: #10b981 (Green)
- Warning: #f59e0b (Amber)
- Error: #ef4444 (Red)

### Dark Mode (Toggle via AppNavbar)
- Primary: #0ea5e9 (same)
- Secondary: #a78bfa (lighter purple)
- Success: #34d399 (lighter green)
- Warning: #fbbf24 (lighter amber)
- Error: #f87171 (lighter red)

**Persistence:** Saved in `localStorage.getItem('theme')`

## Responsive Breakpoints

```
Mobile:    320px - 640px   (1 column)
Tablet:    641px - 1024px  (2 columns)
Desktop:   1025px+         (3 columns)
```

## Performance Optimization

| Technique | Status | Details |
|-----------|--------|---------|
| Code Splitting | ✅ | Vendor, HeroUI, form libs in separate chunks |
| Tree Shaking | ✅ | HeroUI unused components removed |
| Minification | ✅ | Terser + CSS minification |
| Lazy Loading | ✅ | Route-based code split ready |
| Image Optimization | ⏳ | Ready for Vite's image handling |
| Caching | ✅ | Hashed assets in dist/ |

## Security Features

```
✅ Bearer Token Auth
✅ localStorage for tokens (safe)
✅ 401 Auto-redirect
✅ No credentials in URLs
✅ HTTPS ready
✅ CORS configured backend
✅ Input validation
✅ Error handling
```

## Accessibility Checklist

```
✅ Semantic HTML
✅ ARIA Labels
✅ Keyboard Navigation (Tab, Enter, Esc)
✅ Focus Visible Rings
✅ Color Contrast (WCAG AA)
✅ Proper Heading Hierarchy
✅ Screen Reader Support
✅ Touch Targets ≥ 44px
```

## Development Workflow

1. **Start:** `npm run dev`
2. **Edit:** Modify `.tsx` files in `src/`
3. **Hot Reload:** Vite auto-reloads on save
4. **Type Check:** `npm run typecheck`
5. **Lint:** `npm run lint`
6. **Format:** `npm run format`
7. **Build:** `npm run build`
8. **Preview:** `npm run preview`

---

**Created:** November 4, 2025  
**Technology:** React 18 + Vite + TypeScript + HeroUI + Tailwind CSS v4
