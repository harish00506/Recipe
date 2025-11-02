-- ============================================================================
-- Recipe App - Complete Database Schema
-- Database: PostgreSQL 15+
-- ============================================================================

-- Drop existing tables if they exist (for fresh setup)
DROP TABLE IF EXISTS shopping_list_items CASCADE;
DROP TABLE IF EXISTS shopping_lists CASCADE;
DROP TABLE IF EXISTS recipe_ingredients CASCADE;
DROP TABLE IF EXISTS recipes CASCADE;
DROP TABLE IF EXISTS ingredients CASCADE;
DROP TABLE IF EXISTS cuisines CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- ============================================================================
-- 1. USERS TABLE - Store user authentication and profile information
-- ============================================================================
CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    
    -- Constraints
    CONSTRAINT email_not_empty CHECK (email != ''),
    CONSTRAINT name_not_empty CHECK (name != '')
);

-- Create index for frequent email lookups
CREATE INDEX idx_users_email ON users(email);

-- ============================================================================
-- 2. CUISINES TABLE - Store cuisine types/categories
-- ============================================================================
CREATE TABLE cuisines (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    
    -- Constraints
    CONSTRAINT cuisine_name_not_empty CHECK (name != '')
);

-- Create index for cuisine lookups
CREATE INDEX idx_cuisines_name ON cuisines(name);

-- Insert default cuisines
INSERT INTO cuisines (name, description) VALUES
    ('Italian', 'Mediterranean cuisine from Italy featuring pasta, olive oil, and tomatoes'),
    ('Indian', 'South Asian cuisine known for spices and curries'),
    ('Mexican', 'North American cuisine with beans, corn, and chili peppers'),
    ('Thai', 'Southeast Asian cuisine with balance of sweet, sour, salty, and spicy'),
    ('French', 'European cuisine known for sophisticated cooking techniques'),
    ('Chinese', 'East Asian cuisine with diverse regional flavors'),
    ('Mediterranean', 'Coastal European cuisine emphasizing fresh vegetables and seafood'),
    ('Japanese', 'East Asian cuisine featuring rice, seafood, and minimalist presentation'),
    ('American', 'North American cuisine with comfort food and BBQ traditions'),
    ('Vegetarian', 'Plant-based cuisine without meat or fish');

-- ============================================================================
-- 3. INGREDIENTS TABLE - Store available ingredients
-- ============================================================================
CREATE TABLE ingredients (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL UNIQUE,
    category VARCHAR(100) NOT NULL,
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    
    -- Constraints
    CONSTRAINT ingredient_name_not_empty CHECK (name != ''),
    CONSTRAINT ingredient_category_not_empty CHECK (category != '')
);

-- Create indexes for ingredient searches
CREATE INDEX idx_ingredients_name ON ingredients(name);
CREATE INDEX idx_ingredients_category ON ingredients(category);

-- ============================================================================
-- 4. RECIPES TABLE - Store recipe information
-- ============================================================================
CREATE TABLE recipes (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    instructions TEXT NOT NULL,
    servings INTEGER NOT NULL DEFAULT 1,
    prep_time_minutes INTEGER NOT NULL DEFAULT 0,
    cook_time_minutes INTEGER NOT NULL DEFAULT 0,
    cuisine_id UUID NOT NULL,
    image_url VARCHAR(500),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_public BOOLEAN NOT NULL DEFAULT FALSE,
    
    -- Foreign Keys
    CONSTRAINT fk_recipes_user_id FOREIGN KEY (user_id) 
        REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_recipes_cuisine_id FOREIGN KEY (cuisine_id) 
        REFERENCES cuisines(id) ON DELETE RESTRICT,
    
    -- Constraints
    CONSTRAINT title_not_empty CHECK (title != ''),
    CONSTRAINT instructions_not_empty CHECK (instructions != ''),
    CONSTRAINT servings_positive CHECK (servings > 0),
    CONSTRAINT prep_time_non_negative CHECK (prep_time_minutes >= 0),
    CONSTRAINT cook_time_non_negative CHECK (cook_time_minutes >= 0)
);

-- Create indexes for recipe searches
CREATE INDEX idx_recipes_user_id ON recipes(user_id);
CREATE INDEX idx_recipes_cuisine_id ON recipes(cuisine_id);
CREATE INDEX idx_recipes_title ON recipes USING GIN(to_tsvector('english', title));
CREATE INDEX idx_recipes_description ON recipes USING GIN(to_tsvector('english', description));
CREATE INDEX idx_recipes_created_at ON recipes(created_at DESC);

-- ============================================================================
-- 5. RECIPE_INGREDIENTS TABLE - Join table for recipes and ingredients
-- ============================================================================
CREATE TABLE recipe_ingredients (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    recipe_id UUID NOT NULL,
    ingredient_id UUID NOT NULL,
    quantity DECIMAL(10, 2) NOT NULL,
    unit VARCHAR(50) NOT NULL,
    notes TEXT,
    display_order INTEGER NOT NULL DEFAULT 0,
    
    -- Foreign Keys
    CONSTRAINT fk_recipe_ingredients_recipe_id FOREIGN KEY (recipe_id) 
        REFERENCES recipes(id) ON DELETE CASCADE,
    CONSTRAINT fk_recipe_ingredients_ingredient_id FOREIGN KEY (ingredient_id) 
        REFERENCES ingredients(id) ON DELETE RESTRICT,
    
    -- Constraints
    CONSTRAINT quantity_positive CHECK (quantity > 0),
    CONSTRAINT unit_not_empty CHECK (unit != ''),
    CONSTRAINT unique_recipe_ingredient UNIQUE (recipe_id, ingredient_id)
);

-- Create indexes for joins
CREATE INDEX idx_recipe_ingredients_recipe_id ON recipe_ingredients(recipe_id);
CREATE INDEX idx_recipe_ingredients_ingredient_id ON recipe_ingredients(ingredient_id);

-- ============================================================================
-- 6. SHOPPING_LISTS TABLE - Store shopping list metadata
-- ============================================================================
CREATE TABLE shopping_lists (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_completed BOOLEAN NOT NULL DEFAULT FALSE,
    exported_at TIMESTAMP,
    
    -- Foreign Keys
    CONSTRAINT fk_shopping_lists_user_id FOREIGN KEY (user_id) 
        REFERENCES users(id) ON DELETE CASCADE,
    
    -- Constraints
    CONSTRAINT name_not_empty CHECK (name != '')
);

-- Create indexes for shopping list queries
CREATE INDEX idx_shopping_lists_user_id ON shopping_lists(user_id);
CREATE INDEX idx_shopping_lists_created_at ON shopping_lists(created_at DESC);
CREATE INDEX idx_shopping_lists_is_completed ON shopping_lists(is_completed);

-- ============================================================================
-- 7. SHOPPING_LIST_ITEMS TABLE - Store individual items in shopping lists
-- ============================================================================
CREATE TABLE shopping_list_items (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    shopping_list_id UUID NOT NULL,
    ingredient_id UUID NOT NULL,
    quantity DECIMAL(10, 2) NOT NULL,
    unit VARCHAR(50) NOT NULL,
    is_checked BOOLEAN NOT NULL DEFAULT FALSE,
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    
    -- Foreign Keys
    CONSTRAINT fk_shopping_list_items_shopping_list_id FOREIGN KEY (shopping_list_id) 
        REFERENCES shopping_lists(id) ON DELETE CASCADE,
    CONSTRAINT fk_shopping_list_items_ingredient_id FOREIGN KEY (ingredient_id) 
        REFERENCES ingredients(id) ON DELETE RESTRICT,
    
    -- Constraints
    CONSTRAINT quantity_positive CHECK (quantity > 0),
    CONSTRAINT unit_not_empty CHECK (unit != '')
);

-- Create indexes for shopping list item queries
CREATE INDEX idx_shopping_list_items_shopping_list_id ON shopping_list_items(shopping_list_id);
CREATE INDEX idx_shopping_list_items_ingredient_id ON shopping_list_items(ingredient_id);
CREATE INDEX idx_shopping_list_items_is_checked ON shopping_list_items(is_checked);

-- ============================================================================
-- ADDITIONAL UTILITIES
-- ============================================================================

-- Function to update updated_at timestamp
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Triggers for updated_at
CREATE TRIGGER update_users_updated_at
    BEFORE UPDATE ON users
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_recipes_updated_at
    BEFORE UPDATE ON recipes
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_shopping_lists_updated_at
    BEFORE UPDATE ON shopping_lists
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_shopping_list_items_updated_at
    BEFORE UPDATE ON shopping_list_items
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

-- ============================================================================
-- COMMENTS AND DOCUMENTATION
-- ============================================================================

COMMENT ON TABLE users IS 'Stores user account information for authentication and profile';
COMMENT ON TABLE cuisines IS 'Predefined cuisine types for recipe categorization';
COMMENT ON TABLE ingredients IS 'Master list of available ingredients with categories';
COMMENT ON TABLE recipes IS 'User-created recipes with instructions and metadata';
COMMENT ON TABLE recipe_ingredients IS 'Junction table linking recipes to their ingredients with quantities';
COMMENT ON TABLE shopping_lists IS 'User shopping lists generated from selected recipes';
COMMENT ON TABLE shopping_list_items IS 'Individual items in shopping lists with quantities and status';

COMMENT ON COLUMN users.is_active IS 'Soft delete flag to deactivate accounts without deleting data';
COMMENT ON COLUMN recipes.is_public IS 'Flag to control recipe visibility to other users';
COMMENT ON COLUMN shopping_list_items.is_checked IS 'Track which items have been checked off during shopping';

-- ============================================================================
-- END OF SCHEMA
-- ============================================================================