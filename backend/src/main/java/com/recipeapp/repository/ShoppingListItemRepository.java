package com.recipeapp.repository;

import com.recipeapp.model.ShoppingListItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ShoppingListItemRepository extends JpaRepository<ShoppingListItem, UUID> {
    List<ShoppingListItem> findByShoppingListIdOrderByCreatedAtAsc(UUID shoppingListId);
    @Query("SELECT sli FROM ShoppingListItem sli WHERE sli.shoppingList.id = ?1 AND sli.isChecked = true")
    List<ShoppingListItem> findCheckedItems(UUID shoppingListId);
    @Query("SELECT sli FROM ShoppingListItem sli WHERE sli.shoppingList.id = ?1 AND sli.isChecked = false")
    List<ShoppingListItem> findUncheckedItems(UUID shoppingListId);
    long countByShoppingListId(UUID shoppingListId);
    @Query("SELECT COUNT(sli) FROM ShoppingListItem sli WHERE sli.shoppingList.id = ?1 AND sli.isChecked = true")
    long countCheckedItems(UUID shoppingListId);
    @Query("SELECT sli FROM ShoppingListItem sli WHERE sli.shoppingList.id = ?1 AND sli.ingredient.id = ?2")
    Optional<ShoppingListItem> findByShoppingListAndIngredient(UUID shoppingListId, UUID ingredientId);
    void deleteByShoppingListId(UUID shoppingListId);
    List<ShoppingListItem> findByIngredientId(UUID ingredientId);
}
