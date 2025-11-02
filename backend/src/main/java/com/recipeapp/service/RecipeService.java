package com.recipeapp.service;

import com.recipeapp.exception.ResourceNotFoundException;
import com.recipeapp.model.Recipe;
import com.recipeapp.model.Cuisine;
import com.recipeapp.repository.RecipeRepository;
import com.recipeapp.repository.CuisineRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Service for recipe-related operations
 * Manages recipe creation, updates, searches, and filtering
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final CuisineRepository cuisineRepository;

    /**
     * Get all recipes with pagination
     * @param pageable pagination info
     * @return Page of recipes
     */
    public Page<Recipe> getAllRecipes(Pageable pageable) {
        log.debug("Fetching all recipes with pagination");
        return recipeRepository.findAll(pageable);
    }

    /**
     * Get recipe by ID
     * @param recipeId the recipe ID
     * @return Recipe if found
     * @throws ResourceNotFoundException if recipe not found
     */
    public Recipe getRecipeById(UUID recipeId) {
        log.debug("Fetching recipe by ID: {}", recipeId);
        return recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe", "id", recipeId));
    }

    /**
     * Get recipes by cuisine with pagination
     * @param cuisineId the cuisine ID
     * @param pageable pagination info
     * @return Page of recipes
     * @throws ResourceNotFoundException if cuisine not found
     */
    public Page<Recipe> getRecipesByCuisine(UUID cuisineId, Pageable pageable) {
        log.debug("Fetching recipes by cuisine: {}", cuisineId);
        
        if (!cuisineRepository.existsById(cuisineId)) {
            throw new ResourceNotFoundException("Cuisine", "id", cuisineId);
        }
        
        return recipeRepository.findByCuisineId(cuisineId, pageable);
    }

    /**
     * Search recipes by title (partial match)
     * @param searchTerm the search term
     * @param pageable pagination info
     * @return Page of matching recipes
     */
    public Page<Recipe> searchByTitle(String searchTerm, Pageable pageable) {
        log.debug("Searching recipes with term: {}", searchTerm);
        return recipeRepository.searchByTitle(searchTerm, pageable);
    }

    /**
     * Search recipes by description (partial match)
     * @param searchTerm the search term
     * @param pageable pagination info
     * @return Page of matching recipes
     */
    public Page<Recipe> searchByDescription(String searchTerm, Pageable pageable) {
        log.debug("Searching recipes by description with term: {}", searchTerm);
        return recipeRepository.searchByDescription(searchTerm, pageable);
    }

    /**
     * Get recipes by difficulty level
     * @param difficultyLevel the difficulty level
     * @param pageable pagination info
     * @return Page of recipes at that difficulty level
     */
    public Page<Recipe> getRecipesByDifficulty(String difficultyLevel, Pageable pageable) {
        log.debug("Fetching recipes by difficulty: {}", difficultyLevel);
        return recipeRepository.findByDifficultyLevel(difficultyLevel, pageable);
    }

    /**
     * Get recipes within cooking time range
     * @param minMinutes minimum cooking time in minutes
     * @param maxMinutes maximum cooking time in minutes
     * @param pageable pagination info
     * @return Page of recipes
     */
    public Page<Recipe> getRecipesByTimeRange(Integer minMinutes, Integer maxMinutes, Pageable pageable) {
        log.debug("Fetching recipes with cooking time between {} and {} minutes", minMinutes, maxMinutes);
        return recipeRepository.findByCookingTimeRange(minMinutes, maxMinutes, pageable);
    }

    /**
     * Create a new recipe
     * @param title recipe title
     * @param description recipe description
     * @param cuisineId cuisine ID
     * @param cookingTime cooking time in minutes
     * @param servings number of servings
     * @param difficultyLevel difficulty level
     * @return created Recipe
     * @throws ResourceNotFoundException if cuisine not found
     */
    @Transactional
    public Recipe createRecipe(String title, String description, UUID cuisineId, 
                               Integer cookingTime, Integer servings, String difficultyLevel) {
        log.info("Creating new recipe: {}", title);

        // Verify cuisine exists
        Cuisine cuisine = cuisineRepository.findById(cuisineId)
                .orElseThrow(() -> new ResourceNotFoundException("Cuisine", "id", cuisineId));

        Recipe recipe = Recipe.builder()
                .title(title)
                .description(description)
                .cuisine(cuisine)
                .cookingTime(cookingTime)
                .servings(servings)
                .difficultyLevel(difficultyLevel)
                .build();

        Recipe savedRecipe = recipeRepository.save(recipe);
        log.info("Recipe created successfully: {}", title);

        return savedRecipe;
    }

    /**
     * Update recipe
     * @param recipeId the recipe ID
     * @param title new title (nullable)
     * @param description new description (nullable)
     * @param cuisineId new cuisine ID (nullable)
     * @param cookingTime new cooking time (nullable)
     * @param servings new servings (nullable)
     * @param difficultyLevel new difficulty level (nullable)
     * @return updated Recipe
     * @throws ResourceNotFoundException if recipe or cuisine not found
     */
    @Transactional
    public Recipe updateRecipe(UUID recipeId, String title, String description, UUID cuisineId,
                               Integer cookingTime, Integer servings, String difficultyLevel) {
        log.debug("Updating recipe: {}", recipeId);

        Recipe recipe = getRecipeById(recipeId);

        if (title != null && !title.isBlank()) {
            recipe.setTitle(title);
        }

        if (description != null && !description.isBlank()) {
            recipe.setDescription(description);
        }

        if (cuisineId != null) {
            Cuisine cuisine = cuisineRepository.findById(cuisineId)
                    .orElseThrow(() -> new ResourceNotFoundException("Cuisine", "id", cuisineId));
            recipe.setCuisine(cuisine);
        }

        if (cookingTime != null && cookingTime > 0) {
            recipe.setCookingTime(cookingTime);
        }

        if (servings != null && servings > 0) {
            recipe.setServings(servings);
        }

        if (difficultyLevel != null && !difficultyLevel.isBlank()) {
            recipe.setDifficultyLevel(difficultyLevel);
        }

        Recipe updatedRecipe = recipeRepository.save(recipe);
        log.info("Recipe updated successfully: {}", recipeId);

        return updatedRecipe;
    }

    /**
     * Delete recipe
     * @param recipeId the recipe ID
     * @throws ResourceNotFoundException if recipe not found
     */
    @Transactional
    public void deleteRecipe(UUID recipeId) {
        log.debug("Deleting recipe: {}", recipeId);

        if (!recipeRepository.existsById(recipeId)) {
            throw new ResourceNotFoundException("Recipe", "id", recipeId);
        }

        recipeRepository.deleteById(recipeId);
        log.info("Recipe deleted successfully: {}", recipeId);
    }

    /**
     * Check if recipe exists
     * @param recipeId the recipe ID
     * @return true if exists, false otherwise
     */
    public boolean exists(UUID recipeId) {
        return recipeRepository.existsById(recipeId);
    }

    /**
     * Get recipe count
     * @return total number of recipes
     */
    public long getRecipeCount() {
        return recipeRepository.count();
    }

    /**
     * Get recipes by cuisine name
     * @param cuisineName the cuisine name
     * @param pageable pagination info
     * @return Page of recipes
     */
    public Page<Recipe> getRecipesByCuisineName(String cuisineName, Pageable pageable) {
        log.debug("Fetching recipes by cuisine name: {}", cuisineName);
        return recipeRepository.findByCuisineName(cuisineName, pageable);
    }
}
