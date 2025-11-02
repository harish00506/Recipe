package com.recipeapp.controller;

import com.recipeapp.dto.IngredientRequest;
import com.recipeapp.dto.IngredientResponse;
import com.recipeapp.model.Ingredient;
import com.recipeapp.service.IngredientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

/**
 * REST Controller for Ingredient operations
 * Provides endpoints for managing recipe ingredients
 */
@RestController
@RequestMapping("/api/ingredients")
@RequiredArgsConstructor
@Slf4j
public class IngredientController {
    
    private final IngredientService ingredientService;
    
    /**
     * GET /api/ingredients
     * Retrieve all ingredients with pagination
     *
     * @param page Page number (default: 0)
     * @param size Page size (default: 10)
     * @return Page of IngredientResponse
     */
    @GetMapping
    public ResponseEntity<Page<IngredientResponse>> getAllIngredients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.debug("Getting all ingredients - page: {}, size: {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<Ingredient> ingredients = ingredientService.getAllIngredients(pageable);
        Page<IngredientResponse> responses = ingredients.map(this::toIngredientResponse);
        log.info("Retrieved {} ingredients", ingredients.getTotalElements());
        return ResponseEntity.ok(responses);
    }
    
    /**
     * GET /api/ingredients/{id}
     * Retrieve a specific ingredient by ID
     *
     * @param id Ingredient ID
     * @return IngredientResponse
     */
    @GetMapping("/{id}")
    public ResponseEntity<IngredientResponse> getIngredientById(@PathVariable UUID id) {
        log.debug("Getting ingredient by ID: {}", id);
        Ingredient ingredient = ingredientService.getIngredientById(id);
        log.info("Retrieved ingredient: {}", ingredient.getName());
        return ResponseEntity.ok(toIngredientResponse(ingredient));
    }
    
    /**
     * GET /api/ingredients/search
     * Search ingredients by name
     *
     * @param name Ingredient name to search for
     * @return List of matching IngredientResponse
     */
    @GetMapping("/search")
    public ResponseEntity<List<IngredientResponse>> searchByName(@RequestParam String name) {
        log.debug("Searching ingredients by name: {}", name);
        List<Ingredient> ingredients = ingredientService.searchByName(name);
        List<IngredientResponse> responses = ingredients.stream()
                .map(this::toIngredientResponse)
                .toList();
        log.info("Found {} ingredients matching: {}", responses.size(), name);
        return ResponseEntity.ok(responses);
    }
    
    /**
     * GET /api/ingredients/category/{category}
     * Get ingredients by category
     *
     * @param category Ingredient category
     * @return List of IngredientResponse
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<IngredientResponse>> getIngredientsByCategory(@PathVariable String category) {
        log.debug("Getting ingredients by category: {}", category);
        List<Ingredient> ingredients = ingredientService.getIngredientsByCategory(category);
        List<IngredientResponse> responses = ingredients.stream()
                .map(this::toIngredientResponse)
                .toList();
        log.info("Found {} ingredients in category: {}", responses.size(), category);
        return ResponseEntity.ok(responses);
    }
    
    /**
     * POST /api/ingredients
     * Create a new ingredient
     *
     * @param request IngredientRequest with name, category, unit, calories, protein, fat, carbs
     * @return Created IngredientResponse
     */
    @PostMapping
    public ResponseEntity<IngredientResponse> createIngredient(@Valid @RequestBody IngredientRequest request) {
        log.debug("Creating ingredient: {}", request.getName());
        Ingredient ingredient = Ingredient.builder()
                .name(request.getName())
                .category(request.getCategory())
                .unit(request.getUnit())
                .calories(request.getCalories())
                .protein(request.getProtein())
                .fat(request.getFat())
                .carbs(request.getCarbs())
                .build();
        
        Ingredient created = ingredientService.createIngredient(ingredient);
        log.info("Ingredient created: {} with ID: {}", created.getName(), created.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(toIngredientResponse(created));
    }
    
    /**
     * PUT /api/ingredients/{id}
     * Update an existing ingredient
     *
     * @param id Ingredient ID
     * @param request Updated IngredientRequest
     * @return Updated IngredientResponse
     */
    @PutMapping("/{id}")
    public ResponseEntity<IngredientResponse> updateIngredient(
            @PathVariable UUID id,
            @Valid @RequestBody IngredientRequest request) {
        log.debug("Updating ingredient: {}", id);
        Ingredient ingredient = ingredientService.getIngredientById(id);
        ingredient.setName(request.getName());
        ingredient.setCategory(request.getCategory());
        ingredient.setUnit(request.getUnit());
        ingredient.setCalories(request.getCalories());
        ingredient.setProtein(request.getProtein());
        ingredient.setFat(request.getFat());
        ingredient.setCarbs(request.getCarbs());
        
        Ingredient updated = ingredientService.updateIngredient(id, ingredient);
        log.info("Ingredient updated: {}", updated.getName());
        return ResponseEntity.ok(toIngredientResponse(updated));
    }
    
    /**
     * DELETE /api/ingredients/{id}
     * Delete an ingredient
     *
     * @param id Ingredient ID
     * @return No content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable UUID id) {
        log.debug("Deleting ingredient: {}", id);
        ingredientService.deleteIngredient(id);
        log.info("Ingredient deleted: {}", id);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Convert Ingredient entity to IngredientResponse DTO
     */
    private IngredientResponse toIngredientResponse(Ingredient ingredient) {
        return IngredientResponse.builder()
                .id(ingredient.getId())
                .name(ingredient.getName())
                .category(ingredient.getCategory())
                .unit(ingredient.getUnit())
                .calories(ingredient.getCalories())
                .protein(ingredient.getProtein())
                .fat(ingredient.getFat())
                .carbs(ingredient.getCarbs())
                .build();
    }
}
