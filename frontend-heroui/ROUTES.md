# 🗺️ App Routes & Navigation Guide

## Frontend Routes

The app is now running at `http://localhost:3000`

### Public Routes (No Login Required)

| Route | Page | Purpose |
|-------|------|---------|
| `/landing` | Landing Page | 🎉 Welcome page with features overview |
| `/auth/signin` | Sign In | 🔑 Login with email & password |
| `/auth/signup` | Sign Up | ✍️ Create new account |

### Protected Routes (Login Required)

| Route | Page | Purpose |
|-------|------|---------|
| `/` | Dashboard | 📊 View recipes, statistics |
| `/settings` | Settings | ⚙️ Profile, preferences, theme |

---

## Navigation Flow

### For New Users

```
1. Visit http://localhost:3000
   ↓
2. See Landing Page 🎉
   ↓
3. Click "Get Started" → Sign Up Page
   ↓
4. Create Account (Name, Email, Password)
   ↓
5. Redirected to Dashboard 📊
```

### For Existing Users

```
1. Visit http://localhost:3000
   ↓
2. See Landing Page 🎉
   ↓
3. Click "Sign In" → Sign In Page
   ↓
4. Enter Email & Password
   ↓
5. Redirected to Dashboard 📊
```

### Inside the App (After Login)

```
Dashboard (/):
├── Click "Settings" → Settings Page (/settings)
└── Click Logo → Back to Dashboard

Settings (/settings):
├── Profile Tab → Edit name, password
├── Preferences Tab → Notifications, language
├── Theme Tab → Choose light/dark mode
└── Click Logo → Back to Dashboard

Theme Toggle:
├── Click Sun/Moon icon in navbar → Toggle theme
└── Change saved to localStorage
```

---

## Default Route Behavior

### If NOT Logged In:
- Visit any URL → Landing page (`/landing`)
- Landing page → Can access `/auth/signin` or `/auth/signup`

### If Logged In:
- Visit `/landing` → Redirected to Dashboard (`/`)
- Visit any auth page → Redirected to Dashboard (`/`)
- Dashboard accessible → Shows recipes, settings, etc.

---

## Component Navigation

### Landing Page `/landing`

```
┌─────────────────────────────────────┐
│ RecipeApp Logo  [Sign In] [Get Started] │
├─────────────────────────────────────┤
│                                       │
│  Hero Section                         │
│  "Discover Your Next Favorite Recipe" │
│  [Start Free] [Sign In]              │
│                                       │
│  Feature Cards (3 columns)            │
│  - 1000+ Recipes                     │
│  - Save & Organize                   │
│  - Smart Shopping Lists              │
│                                       │
│  Why Choose RecipeApp (3 sections)    │
│  - Curated Recipes                   │
│  - Easy Organization                 │
│  - Smart Shopping                    │
│                                       │
│  CTA: "Create Your Account"          │
│                                       │
│  Footer                              │
└─────────────────────────────────────┘
```

### Sign Up Page `/auth/signup`

```
┌──────────────────────────────┐
│ Create Account               │
│ Join our community          │
├──────────────────────────────┤
│ [Error Message if any]      │
│ [Full Name Input]           │
│ [Email Input]               │
│ [Password Input]            │
│ [Confirm Password Input]    │
│ [Create Account Button]     │
│ Already have account?       │
│ [Sign In Link]              │
└──────────────────────────────┘
```

### Sign In Page `/auth/signin`

```
┌──────────────────────────────┐
│ Welcome Back                 │
│ Sign in to your account     │
├──────────────────────────────┤
│ [Error Message if any]      │
│ [Email Input]               │
│ [Password Input]            │
│ [Sign In Button]            │
│ Don't have an account?      │
│ [Sign Up Link]              │
└──────────────────────────────┘
```

### Dashboard Page `/`

```
┌────────────────────────────────────────┐
│ RecipeApp  [Search] [Theme] [Avatar▼] │
├────────────────────────────────────────┤
│                                         │
│ Welcome to Recipe App 👨‍🍳              │
│                                         │
│ ┌─────────┐ ┌─────────┐ ┌─────────┐  │
│ │ 42      │ │ 12      │ │ 28 min  │  │
│ │ Recipes │ │Favorites│ │Avg Time │  │
│ └─────────┘ └─────────┘ └─────────┘  │
│                                         │
│ [Add Recipe]                           │
│ ┌─────────────────────────────────┐   │
│ │ Recipe Name │ Cuisine │ Time    │   │
│ ├─────────────────────────────────┤   │
│ │ Pasta...    │ Italian │ 30 min  │   │
│ │ Pad Thai... │ Thai    │ 20 min  │   │
│ │ Curry...    │ Indian  │ 45 min  │   │
│ ├─────────────────────────────────┤   │
│ │ 1 2 3 4 5                       │   │
│ └─────────────────────────────────┘   │
└────────────────────────────────────────┘
```

### Settings Page `/settings`

```
┌────────────────────────────────────────┐
│ RecipeApp  [Search] [Theme] [Avatar▼] │
├────────────────────────────────────────┤
│ Settings                               │
│                                         │
│ [Profile] [Preferences] [Theme]        │
│                                         │
│ PROFILE TAB:                           │
│ ├─ Full Name: [Input]                 │
│ ├─ Email: user@example.com (disabled) │
│ ├─ Change Password: [Input]           │
│ └─ [Save Changes]                     │
│                                         │
│ PREFERENCES TAB:                       │
│ ├─ Email Notifications [Toggle]       │
│ ├─ Push Notifications [Toggle]        │
│ ├─ Dark Mode [Toggle]                 │
│ ├─ Language: [English ▼]              │
│ └─ [Save Preferences]                 │
│                                         │
│ THEME TAB:                             │
│ ├─ [Light Theme Card] [Apply]         │
│ ├─ [Dark Theme Card] [Apply]          │
│ └─ Preview colors                     │
└────────────────────────────────────────┘
```

---

## Quick Navigation Tips

### Using Navbar
- **Logo/Title** → Back to Dashboard
- **Sun/Moon Icon** → Toggle Dark/Light mode
- **Avatar Dropdown** → See user name, Settings, Logout

### Using Buttons
- **"Add Recipe"** → Ready for new recipe form (will be added)
- **"Save Changes"** → Updates settings
- **"Sign Out"** → Logs out and returns to landing page

### Using Links
- **"Sign Up"** → From landing page or signin page
- **"Sign In"** → From landing page or signup page
- **Settings Link** → In avatar dropdown menu

---

## Auth Flow Diagram

```
START
  │
  ├─→ Not Logged In
  │    │
  │    ├─→ Landing Page
  │    │    ├─→ "Get Started" → Sign Up
  │    │    │    ├─→ Create Account
  │    │    │    └─→ Logged In ✅
  │    │    │
  │    │    └─→ "Sign In" → Sign In
  │    │         ├─→ Enter Credentials
  │    │         └─→ Logged In ✅
  │    │
  │    └─→ Any Other URL → Redirect to Landing
  │
  └─→ Logged In ✅
       │
       ├─→ Dashboard (Main Page)
       │    ├─→ View Recipes
       │    └─→ Manage Shopping Lists
       │
       ├─→ Settings
       │    ├─→ Edit Profile
       │    ├─→ Change Preferences
       │    └─→ Toggle Theme
       │
       └─→ Logout
            └─→ Back to Landing Page
```

---

## URL Cheat Sheet

| What to Do | URL | Status |
|-----------|-----|--------|
| See landing page | `http://localhost:3000/landing` | 🎉 New! |
| Sign up | `http://localhost:3000/auth/signup` | ✅ Works |
| Sign in | `http://localhost:3000/auth/signin` | ✅ Works |
| Dashboard | `http://localhost:3000/` | ✅ Protected |
| Settings | `http://localhost:3000/settings` | ✅ Protected |

---

## Testing Navigation

### Test 1: New User Journey
```
1. Open http://localhost:3000
   Expected: See landing page
   
2. Click "Get Started"
   Expected: Redirected to signup
   
3. Fill form & click "Create Account"
   Expected: Account created, redirected to dashboard
```

### Test 2: Returning User Journey
```
1. Open http://localhost:3000
   Expected: See landing page
   
2. Click "Sign In"
   Expected: Redirected to signin
   
3. Enter credentials & click "Sign In"
   Expected: Logged in, redirected to dashboard
```

### Test 3: Protected Routes
```
1. Try to visit http://localhost:3000/settings (not logged in)
   Expected: Redirected to landing page
   
2. Log in first
   Expected: Can now visit /settings
   
3. Click settings in navbar
   Expected: Settings page loads
```

---

## Remember

- ✅ **Landing page** is the entry point (not login!)
- ✅ **All routes work** on desktop and mobile
- ✅ **Dark mode toggle** in navbar works everywhere
- ✅ **Logout** in avatar menu
- ✅ **Settings** available after login
- 🔌 **Backend** must be running for auth to work

---

**Status:** ✅ All routes ready and working!
