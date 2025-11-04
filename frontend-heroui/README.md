# Recipe App - Modern Frontend (Vite + HeroUI)

A production-ready, modern UI/UX frontend built with React 18, Vite, TypeScript, HeroUI (Tailwind-based), and React Router.

## Tech Stack

- **Framework:** React 18+ with Vite
- **Language:** TypeScript (strict mode)
- **UI Library:** HeroUI (Tailwind CSS based)
- **Routing:** React Router v6
- **State Management:** React Hooks + Context
- **Forms:** React Hook Form + Zod validation
- **Styling:** Tailwind CSS v4 + HeroUI components
- **Testing:** Vitest + React Testing Library
- **Linting:** ESLint (strict) + Prettier
- **Build:** Vite with code-splitting and optimization

## Features

✅ **Responsive Design** - Mobile-first, tested at 360px, 768px, 1280px  
✅ **Accessibility** - WCAG-friendly, keyboard navigable, ARIA labels  
✅ **Performance** - Route-based code-splitting, optimized bundles  
✅ **Dark Mode** - Light/dark theme with HeroUI tokens  
✅ **Type Safe** - Full TypeScript strict mode, no `any`  
✅ **Testing** - Vitest + RTL tests for components and pages  
✅ **Production Ready** - Linting, type checking, build optimization  

## Project Structure

```
src/
  components/
    ui/                # HeroUI-wrapped primitives
    layout/            # Navbar, Sidebar, Footer
  features/
    dashboard/         # Dashboard feature
    auth/             # Authentication (Sign In/Up)
    settings/         # Settings & preferences
  hooks/              # Custom React hooks
  lib/                # API client, utilities, constants
  styles/             # Global CSS, hero.ts theme
  types/              # TypeScript type definitions
  App.tsx
  main.tsx
```

## Getting Started

### Prerequisites

- Node.js 18+
- pnpm (or npm/yarn)

### Installation

```bash
cd frontend-heroui
pnpm install
```

### Development

```bash
pnpm dev
```

Opens at `http://localhost:3000`

### Build

```bash
pnpm build       # Production build
pnpm preview     # Preview build locally
```

### Linting & Type Checking

```bash
pnpm lint        # Run ESLint
pnpm typecheck   # TypeScript type check
pnpm format      # Prettier format
```

### Testing

```bash
pnpm test        # Run Vitest
```

## Key Components

### Layout Components

- **`AppNavbar`** - Top navigation with theme toggle, user menu
- **`AppSidebar`** - Navigation sidebar (collapsible on mobile)
- **`AppFooter`** - Footer with links
- **`AppLayout`** - Main layout wrapper

### UI Components

- **`FormTextField`** - Text input with label, validation, error states
- **`SelectField`** - Select dropdown with HeroUI styling
- **`ModalDialog`** - Modal with proper a11y
- **`Toast`** - Toast notifications
- **`DataTable`** - Table with sorting, pagination, loading states
- **`Button`** - HeroUI Button with variants
- **`Card`** - HeroUI Card container

### Pages

- **Dashboard** - Main dashboard with overview cards, recipe grid, filters
- **Auth (Sign In/Up)** - Authentication forms with validation
- **Settings** - User settings with tabs (profile, preferences, theme)

## Theme System

Light/dark theme with HeroUI tokens and persisted user preference:

```typescript
// src/styles/hero.ts - Theme definitions
// Light and dark themes with custom colors
```

Theme toggle in AppNavbar switches between light/dark and saves preference.

## Form Validation

Using React Hook Form + Zod:

```typescript
// Zod schema
const signInSchema = z.object({
  email: z.string().email(),
  password: z.string().min(8),
});

// In component
const { register, handleSubmit, formState: { errors } } = useForm({
  resolver: zodResolver(signInSchema),
});
```

## API Integration

```typescript
// src/lib/api-client.ts
const response = await apiClient.getRecipes(page, limit);
const recipe = await apiClient.createRecipe(recipeData);
```

Token-based auth with automatic 401 redirect.

## Performance

- **Code Splitting:** Route-based lazy loading with Suspense
- **Tree Shaking:** HeroUI components only bundled if used
- **Optimization:** Terser minification, asset optimization
- **Lazy Loading:** Images and components

Lighthouse targets: **≥90** on Performance & Accessibility

## Accessibility

- ✅ Semantic HTML
- ✅ ARIA labels on interactive elements
- ✅ Keyboard navigation (Tab, Enter, Esc)
- ✅ Focus management and visible focus rings
- ✅ Color contrast compliance (WCAG AA)
- ✅ Proper heading hierarchy

## Testing

```typescript
// Example test
describe('SignInPage', () => {
  it('submits form with valid credentials', async () => {
    render(<SignInPage />);
    const emailInput = screen.getByLabelText(/email/i);
    await userEvent.type(emailInput, 'test@example.com');
    // ...assertions
  });
});
```

## Environment Variables

```
VITE_API_BASE_URL=http://localhost:8080/api
VITE_APP_NAME=Recipe App
VITE_APP_ENV=development
```

## Browser Support

- Chrome/Edge 90+
- Firefox 88+
- Safari 14+

## Contributing

1. Follow TypeScript + ESLint rules (strict mode)
2. Add tests for new components/pages
3. Use Prettier for formatting
4. Document complex functions with JSDoc

## Troubleshooting

### Port 3000 already in use?

```bash
pnpm dev -- --port 3001
```

### Clear cache & reinstall

```bash
rm -rf node_modules pnpm-lock.yaml
pnpm install
```

### Type errors after package update

```bash
pnpm typecheck
```

## License

MIT

## Support

For issues, features, or questions, contact the team or open an issue.
