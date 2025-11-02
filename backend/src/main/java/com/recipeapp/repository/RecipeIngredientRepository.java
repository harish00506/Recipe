package com.recipeapp.repository;

import com.recipeapp.model.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, UUID> {
    List<RecipeIngredient> findByRecipeIdOrderByDisplayOrderAsc(UUID recipeId);
    @Query("SELECT ri FROM RecipeIngredient ri WHERE ri.recipe.id = ?1 AND ri.ingredient.id = ?2")
    Optional<RecipeIngredient> findByRecipeAndIngredient(UUID recipeId, UUID ingredientId);
    long countByRecipeId(UUID recipeId);
    void deleteByRecipeId(UUID recipeId);
    List<RecipeIngredient> findByIngredientId(UUID ingredientId);
    @Query("SELECT DISTINCT ri.recipe.id FROM RecipeIngredient ri WHERE ri.ingredient.id = ?1")
    List<UUID> findRecipeIdsUsingIngredient(UUID ingredientId);
}
