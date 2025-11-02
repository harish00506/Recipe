package com.recipeapp.repository;

import com.recipeapp.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, UUID> {
    Optional<Ingredient> findByName(String name);
    @Query("SELECT i FROM Ingredient i WHERE LOWER(i.name) LIKE LOWER(CONCAT('%', ?1, '%')) ORDER BY i.name")
    List<Ingredient> searchByName(String name);
    @Query("SELECT i FROM Ingredient i WHERE LOWER(i.category) = LOWER(?1) ORDER BY i.name")
    List<Ingredient> findByCategory(String category);
    @Query("SELECT i FROM Ingredient i WHERE LOWER(i.category) LIKE LOWER(CONCAT('%', ?1, '%')) ORDER BY i.name")
    List<Ingredient> searchByCategory(String category);
    @Query("SELECT DISTINCT i.category FROM Ingredient i ORDER BY i.category")
    List<String> findAllCategories();
    boolean existsByName(String name);
    @Query("SELECT i FROM Ingredient i ORDER BY i.name ASC")
    List<Ingredient> findAllSorted();
    @Query("SELECT i FROM Ingredient i WHERE i.name IN :names")
    List<Ingredient> findByNames(@Param("names") List<String> names);
}
