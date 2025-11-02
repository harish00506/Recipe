package com.recipeapp.repository;

import com.recipeapp.model.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, UUID> {
    Page<Recipe> findByUserIdOrderByCreatedAtDesc(UUID userId, Pageable pageable);
    List<Recipe> findByUserIdOrderByCreatedAtDesc(UUID userId);
    @Query("SELECT r FROM Recipe r WHERE r.isPublic = true ORDER BY r.createdAt DESC")
    Page<Recipe> findAllPublic(Pageable pageable);
    @Query("SELECT r FROM Recipe r WHERE r.user.id = ?1 AND LOWER(r.title) LIKE LOWER(CONCAT('%', ?2, '%'))")
    Page<Recipe> searchByTitle(UUID userId, String searchTerm, Pageable pageable);
    @Query("SELECT r FROM Recipe r WHERE r.user.id = ?1 AND r.cuisine.id = ?2 ORDER BY r.createdAt DESC")
    Page<Recipe> findByCuisine(UUID userId, UUID cuisineId, Pageable pageable);
    @Query("SELECT DISTINCT r FROM Recipe r JOIN r.ingredients ri WHERE r.user.id = ?1 AND ri.ingredient.id = ?2 ORDER BY r.createdAt DESC")
    Page<Recipe> findByIngredient(UUID userId, UUID ingredientId, Pageable pageable);
    @Query("SELECT DISTINCT r FROM Recipe r JOIN r.ingredients ri WHERE r.user.id = ?1 AND ri.ingredient.id IN ?2 ORDER BY r.createdAt DESC")
    Page<Recipe> findByAnyIngredient(UUID userId, List<UUID> ingredientIds, Pageable pageable);
    @Query("SELECT r FROM Recipe r WHERE r.user.id = ?1 AND (SELECT COUNT(DISTINCT ri.ingredient.id) FROM RecipeIngredient ri WHERE ri.recipe.id = r.id AND ri.ingredient.id IN ?2) = ?3 ORDER BY r.createdAt DESC")
    Page<Recipe> findByAllIngredients(UUID userId, List<UUID> ingredientIds, long ingredientCount, Pageable pageable);
    long countByUserId(UUID userId);
    @Query("SELECT r FROM Recipe r WHERE r.id = ?1 AND r.user.id = ?2")
    Optional<Recipe> findByIdAndUserId(UUID id, UUID userId);
    @Query(value = "SELECT * FROM recipes ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Optional<Recipe> findRandomRecipe();
}
