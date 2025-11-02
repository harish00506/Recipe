package com.recipeapp.repository;

import com.recipeapp.model.Cuisine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for Cuisine entity
 * Provides CRUD and custom query operations for cuisine management
 */
@Repository
public interface CuisineRepository extends JpaRepository<Cuisine, UUID> {

    /**
     * Find cuisine by name
     * @param name the cuisine name
     * @return Optional containing cuisine if found
     */
    Optional<Cuisine> findByName(String name);

    /**
     * Find cuisines by name containing (case-insensitive)
     * @param name partial name to search
     * @return List of matching cuisines
     */
    @Query("SELECT c FROM Cuisine c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', ?1, '%')) ORDER BY c.name")
    List<Cuisine> searchByName(String name);

    /**
     * Check if cuisine with given name exists
     * @param name the cuisine name
     * @return true if cuisine exists
     */
    boolean existsByName(String name);

    /**
     * Get all cuisines sorted by name
     * @return List of all cuisines in alphabetical order
     */
    @Query("SELECT c FROM Cuisine c ORDER BY c.name ASC")
    List<Cuisine> findAllSorted();
}
