package com.recipeapp.service;

import com.recipeapp.exception.ResourceNotFoundException;
import com.recipeapp.model.Cuisine;
import com.recipeapp.repository.CuisineRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Service for cuisine-related operations
 * Manages cuisine categorization for recipes
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CuisineService {

    private final CuisineRepository cuisineRepository;

    /**
     * Get all cuisines sorted by name
     * @return List of all cuisines
     */
    public List<Cuisine> getAllCuisines() {
        log.debug("Fetching all cuisines");
        return cuisineRepository.findAllSorted();
    }

    /**
     * Get cuisine by ID
     * @param cuisineId the cuisine ID
     * @return Cuisine if found
     * @throws ResourceNotFoundException if cuisine not found
     */
    public Cuisine getCuisineById(UUID cuisineId) {
        log.debug("Fetching cuisine by ID: {}", cuisineId);
        return cuisineRepository.findById(cuisineId)
                .orElseThrow(() -> new ResourceNotFoundException("Cuisine", "id", cuisineId));
    }

    /**
     * Get cuisine by name
     * @param name the cuisine name
     * @return Cuisine if found
     * @throws ResourceNotFoundException if cuisine not found
     */
    public Cuisine getCuisineByName(String name) {
        log.debug("Fetching cuisine by name: {}", name);
        return cuisineRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Cuisine", "name", name));
    }

    /**
     * Search cuisines by name (partial match)
     * @param searchTerm the search term
     * @return List of matching cuisines
     */
    public List<Cuisine> searchByName(String searchTerm) {
        log.debug("Searching cuisines with term: {}", searchTerm);
        return cuisineRepository.searchByName(searchTerm);
    }

    /**
     * Create a new cuisine
     * @param name the cuisine name
     * @param description the cuisine description
     * @return created Cuisine
     * @throws IllegalArgumentException if cuisine name already exists
     */
    @Transactional
    public Cuisine createCuisine(String name, String description) {
        log.info("Creating new cuisine: {}", name);

        if (cuisineRepository.existsByName(name)) {
            log.warn("Cuisine creation failed: name {} already exists", name);
            throw new IllegalArgumentException("Cuisine name already exists");
        }

        Cuisine cuisine = Cuisine.builder()
                .name(name)
                .description(description)
                .build();

        Cuisine savedCuisine = cuisineRepository.save(cuisine);
        log.info("Cuisine created successfully: {}", name);

        return savedCuisine;
    }

    /**
     * Update cuisine
     * @param cuisineId the cuisine ID
     * @param name new name (nullable)
     * @param description new description (nullable)
     * @return updated Cuisine
     * @throws ResourceNotFoundException if cuisine not found
     */
    @Transactional
    public Cuisine updateCuisine(UUID cuisineId, String name, String description) {
        log.debug("Updating cuisine: {}", cuisineId);

        Cuisine cuisine = getCuisineById(cuisineId);

        if (name != null && !name.isBlank()) {
            if (!name.equals(cuisine.getName()) && cuisineRepository.existsByName(name)) {
                log.warn("Cuisine update failed: name {} already exists", name);
                throw new IllegalArgumentException("Cuisine name already exists");
            }
            cuisine.setName(name);
        }

        if (description != null && !description.isBlank()) {
            cuisine.setDescription(description);
        }

        Cuisine updatedCuisine = cuisineRepository.save(cuisine);
        log.info("Cuisine updated successfully: {}", cuisineId);

        return updatedCuisine;
    }

    /**
     * Delete cuisine
     * @param cuisineId the cuisine ID
     * @throws ResourceNotFoundException if cuisine not found
     */
    @Transactional
    public void deleteCuisine(UUID cuisineId) {
        log.debug("Deleting cuisine: {}", cuisineId);

        if (!cuisineRepository.existsById(cuisineId)) {
            throw new ResourceNotFoundException("Cuisine", "id", cuisineId);
        }

        cuisineRepository.deleteById(cuisineId);
        log.info("Cuisine deleted successfully: {}", cuisineId);
    }

    /**
     * Check if cuisine exists
     * @param cuisineId the cuisine ID
     * @return true if exists, false otherwise
     */
    public boolean exists(UUID cuisineId) {
        return cuisineRepository.existsById(cuisineId);
    }
}
