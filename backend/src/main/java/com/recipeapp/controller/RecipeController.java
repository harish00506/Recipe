package com.recipeapp.controller;

import com.recipeapp.dto.RecipeRequest;
import com.recipeapp.dto.RecipeResponse;
import com.recipeapp.model.Recipe;
import com.recipeapp.service.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

/**
 * REST Controller for recipe endpoints
 * Handles recipe CRUD operations and search
 */
@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
@Slf4j
public class RecipeController {

    private final RecipeService recipeService;

    /**
     * Get all recipes with pagination
     * GET /api/recipes?page=0&size=10&sort=title,asc
     */
    @GetMapping
    public ResponseEntity<Page<RecipeResponse>> getAllRecipes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy) {
        
        log.debug("Fetching all recipes - page: {}, size: {}", page, size);
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Recipe> recipes = recipeService.getAllRecipes(pageable);
        Page<RecipeResponse> responses = recipes.map(this::toRecipeResponse);
        
        return ResponseEntity.ok(responses);
    }

    /**
     * Get recipe by ID
     * GET /api/recipes/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponse> getRecipeById(@PathVariable UUID id) {
        log.debug("Fetching recipe by ID: {}", id);
        Recipe recipe = recipeService.getRecipeById(id);
        return ResponseEntity.ok(toRecipeResponse(recipe));
    }

    /**
     * Search recipes by title
     * GET /api/recipes/search?title=pasta
     */
    @GetMapping("/search/title")
    public ResponseEntity<Page<RecipeResponse>> searchByTitle(
            @RequestParam String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        log.debug("Searching recipes by title: {}", title);
        Pageable pageable = PageRequest.of(page, size);
        Page<Recipe> recipes = recipeService.searchByTitle(title, pageable);
        Page<RecipeResponse> responses = recipes.map(this::toRecipeResponse);
        
        return ResponseEntity.ok(responses);
    }

    /**
     * Get recipes by cuisine
     * GET /api/recipes/cuisine/{cuisineId}
     */
    @GetMapping("/cuisine/{cuisineId}")
    public ResponseEntity<Page<RecipeResponse>> getRecipesByCuisine(
            @PathVariable UUID cuisineId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        log.debug("Fetching recipes by cuisine: {}", cuisineId);
        Pageable pageable = PageRequest.of(page, size);
        Page<Recipe> recipes = recipeService.getRecipesByCuisine(cuisineId, pageable);
        Page<RecipeResponse> responses = recipes.map(this::toRecipeResponse);
        
        return ResponseEntity.ok(responses);
    }

    /**
     * Get recipes by difficulty
     * GET /api/recipes/difficulty/{level}
     */
    @GetMapping("/difficulty/{level}")
    public ResponseEntity<Page<RecipeResponse>> getRecipesByDifficulty(
            @PathVariable String level,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        log.debug("Fetching recipes by difficulty: {}", level);
        Pageable pageable = PageRequest.of(page, size);
        Page<Recipe> recipes = recipeService.getRecipesByDifficulty(level, pageable);
        Page<RecipeResponse> responses = recipes.map(this::toRecipeResponse);
        
        return ResponseEntity.ok(responses);
    }

    /**
     * Create new recipe
     * POST /api/recipes
     */
    @PostMapping
    public ResponseEntity<RecipeResponse> createRecipe(@Valid @RequestBody RecipeRequest recipeRequest) {
        log.info("Creating new recipe: {}", recipeRequest.getTitle());
        
        Recipe recipe = recipeService.createRecipe(
                recipeRequest.getTitle(),
                recipeRequest.getDescription(),
                recipeRequest.getCuisineId(),
                recipeRequest.getCookingTime(),
                recipeRequest.getServings(),
                recipeRequest.getDifficultyLevel()
        );
        
        return new ResponseEntity<>(toRecipeResponse(recipe), HttpStatus.CREATED);
    }

    /**
     * Update recipe
     * PUT /api/recipes/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<RecipeResponse> updateRecipe(
            @PathVariable UUID id,
            @Valid @RequestBody RecipeRequest recipeRequest) {
        
        log.info("Updating recipe: {}", id);
        
        Recipe recipe = recipeService.updateRecipe(
                id,
                recipeRequest.getTitle(),
                recipeRequest.getDescription(),
                recipeRequest.getCuisineId(),
                recipeRequest.getCookingTime(),
                recipeRequest.getServings(),
                recipeRequest.getDifficultyLevel()
        );
        
        return ResponseEntity.ok(toRecipeResponse(recipe));
    }

    /**
     * Delete recipe
     * DELETE /api/recipes/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable UUID id) {
        log.info("Deleting recipe: {}", id);
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Convert Recipe to RecipeResponse DTO
     */
    private RecipeResponse toRecipeResponse(Recipe recipe) {
        return RecipeResponse.builder()
                .id(recipe.getId())
                .title(recipe.getTitle())
                .description(recipe.getDescription())
                .cuisineId(recipe.getCuisine().getId())
                .cuisineName(recipe.getCuisine().getName())
                .cookingTime(recipe.getCookingTime())
                .servings(recipe.getServings())
                .difficultyLevel(recipe.getDifficultyLevel())
                .createdAt(recipe.getCreatedAt())
                .build();
    }
}
