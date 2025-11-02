package com.recipeapp.service;

import com.recipeapp.exception.ResourceNotFoundException;
import com.recipeapp.model.Ingredient;
import com.recipeapp.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Service for ingredient-related operations
 * Manages ingredient master data and search
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    /**
     * Get all ingredients with pagination
     * @param pageable pagination info
     * @return Page of ingredients
     */
    public Page<Ingredient> getAllIngredients(Pageable pageable) {
        log.debug("Fetching all ingredients with pagination");
        return ingredientRepository.findAll(pageable);
    }

    /**
     * Get ingredient by ID
     * @param ingredientId the ingredient ID
     * @return Ingredient if found
     * @throws ResourceNotFoundException if ingredient not found
     */
    public Ingredient getIngredientById(UUID ingredientId) {
        log.debug("Fetching ingredient by ID: {}", ingredientId);
        return ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient", "id", ingredientId));
    }

    /**
     * Get ingredient by name
     * @param name the ingredient name
     * @return Ingredient if found
     * @throws ResourceNotFoundException if ingredient not found
     */
    public Ingredient getIngredientByName(String name) {
        log.debug("Fetching ingredient by name: {}", name);
        return ingredientRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient", "name", name));
    }

    /**
     * Search ingredients by name (partial match)
     * @param searchTerm the search term
     * @param pageable pagination info
     * @return Page of matching ingredients
     */
    public Page<Ingredient> searchByName(String searchTerm, Pageable pageable) {
        log.debug("Searching ingredients with term: {}", searchTerm);
        return ingredientRepository.searchByName(searchTerm, pageable);
    }

    /**
     * Get ingredients by category with pagination
     * @param category the category
     * @param pageable pagination info
     * @return Page of ingredients in that category
     */
    public Page<Ingredient> getIngredientsByCategory(String category, Pageable pageable) {
        log.debug("Fetching ingredients by category: {}", category);
        return ingredientRepository.findByCategory(category, pageable);
    }

    /**
     * Get all available categories
     * @return List of unique categories
     */
    public List<String> getAllCategories() {
        log.debug("Fetching all ingredient categories");
        return ingredientRepository.findDistinctCategories();
    }

    /**
     * Create a new ingredient
     * @param name ingredient name
     * @param category ingredient category
     * @param caloriesPerUnit calories per unit
     * @return created Ingredient
     * @throws IllegalArgumentException if ingredient name already exists
     */
    @Transactional
    public Ingredient createIngredient(String name, String category, Double caloriesPerUnit) {
        log.info("Creating new ingredient: {}", name);

        if (ingredientRepository.existsByName(name)) {
            log.warn("Ingredient creation failed: name {} already exists", name);
            throw new IllegalArgumentException("Ingredient name already exists");
        }

        Ingredient ingredient = Ingredient.builder()
                .name(name)
                .category(category)
                .caloriesPerUnit(caloriesPerUnit)
                .build();

        Ingredient savedIngredient = ingredientRepository.save(ingredient);
        log.info("Ingredient created successfully: {}", name);

        return savedIngredient;
    }

    /**
     * Update ingredient
     * @param ingredientId the ingredient ID
     * @param name new name (nullable)
     * @param category new category (nullable)
     * @param caloriesPerUnit new calories (nullable)
     * @return updated Ingredient
     * @throws ResourceNotFoundException if ingredient not found
     * @throws IllegalArgumentException if new name already exists
     */
    @Transactional
    public Ingredient updateIngredient(UUID ingredientId, String name, String category, Double caloriesPerUnit) {
        log.debug("Updating ingredient: {}", ingredientId);

        Ingredient ingredient = getIngredientById(ingredientId);

        if (name != null && !name.isBlank()) {
            if (!name.equals(ingredient.getName()) && ingredientRepository.existsByName(name)) {
                log.warn("Ingredient update failed: name {} already exists", name);
                throw new IllegalArgumentException("Ingredient name already exists");
            }
            ingredient.setName(name);
        }

        if (category != null && !category.isBlank()) {
            ingredient.setCategory(category);
        }

        if (caloriesPerUnit != null && caloriesPerUnit >= 0) {
            ingredient.setCaloriesPerUnit(caloriesPerUnit);
        }

        Ingredient updatedIngredient = ingredientRepository.save(ingredient);
        log.info("Ingredient updated successfully: {}", ingredientId);

        return updatedIngredient;
    }

    /**
     * Delete ingredient
     * @param ingredientId the ingredient ID
     * @throws ResourceNotFoundException if ingredient not found
     */
    @Transactional
    public void deleteIngredient(UUID ingredientId) {
        log.debug("Deleting ingredient: {}", ingredientId);

        if (!ingredientRepository.existsById(ingredientId)) {
            throw new ResourceNotFoundException("Ingredient", "id", ingredientId);
        }

        ingredientRepository.deleteById(ingredientId);
        log.info("Ingredient deleted successfully: {}", ingredientId);
    }

    /**
     * Check if ingredient exists
     * @param ingredientId the ingredient ID
     * @return true if exists, false otherwise
     */
    public boolean exists(UUID ingredientId) {
        return ingredientRepository.existsById(ingredientId);
    }

    /**
     * Get ingredient count
     * @return total number of ingredients
     */
    public long getIngredientCount() {
        return ingredientRepository.count();
    }

    /**
     * Check if ingredient exists by name
     * @param name the ingredient name
     * @return true if exists, false otherwise
     */
    public boolean existsByName(String name) {
        return ingredientRepository.existsByName(name);
    }

    /**
     * Get ingredients by list of IDs
     * @param ingredientIds list of ingredient IDs
     * @return List of ingredients
     */
    public List<Ingredient> getIngredientsByIds(List<UUID> ingredientIds) {
        log.debug("Fetching ingredients by IDs: {}", ingredientIds);
        return ingredientRepository.findAllById(ingredientIds);
    }

    /**
     * Get all ingredients sorted by name
     * @return List of all ingredients sorted
     */
    public List<Ingredient> getAllIngredientsSorted() {
        log.debug("Fetching all ingredients sorted by name");
        return ingredientRepository.findAllSorted();
    }
}
