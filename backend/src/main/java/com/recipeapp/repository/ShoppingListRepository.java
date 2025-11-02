package com.recipeapp.repository;

import com.recipeapp.model.ShoppingList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ShoppingListRepository extends JpaRepository<ShoppingList, UUID> {
    Page<ShoppingList> findByUserIdOrderByCreatedAtDesc(UUID userId, Pageable pageable);
    List<ShoppingList> findByUserIdOrderByCreatedAtDesc(UUID userId);
    @Query("SELECT sl FROM ShoppingList sl WHERE sl.id = ?1 AND sl.user.id = ?2")
    Optional<ShoppingList> findByIdAndUserId(UUID id, UUID userId);
    @Query("SELECT sl FROM ShoppingList sl WHERE sl.user.id = ?1 AND sl.isCompleted = ?2 ORDER BY sl.createdAt DESC")
    Page<ShoppingList> findByCompletionStatus(UUID userId, Boolean isCompleted, Pageable pageable);
    @Query("SELECT sl FROM ShoppingList sl WHERE sl.user.id = ?1 AND sl.isCompleted = false ORDER BY sl.createdAt DESC")
    List<ShoppingList> findIncompleteByUserId(UUID userId);
    @Query("SELECT sl FROM ShoppingList sl WHERE sl.user.id = ?1 AND sl.isCompleted = true ORDER BY sl.createdAt DESC")
    List<ShoppingList> findCompletedByUserId(UUID userId);
    @Query("SELECT sl FROM ShoppingList sl WHERE sl.user.id = ?1 AND LOWER(sl.name) LIKE LOWER(CONCAT('%', ?2, '%')) ORDER BY sl.createdAt DESC")
    Page<ShoppingList> searchByName(UUID userId, String searchTerm, Pageable pageable);
    @Query("SELECT sl FROM ShoppingList sl WHERE sl.user.id = ?1 AND sl.exportedAt IS NOT NULL AND sl.exportedAt >= ?2 ORDER BY sl.exportedAt DESC")
    List<ShoppingList> findRecentlyExported(UUID userId, LocalDateTime since);
    long countByUserId(UUID userId);
    @Query("SELECT COUNT(sl) FROM ShoppingList sl WHERE sl.user.id = ?1 AND sl.isCompleted = false")
    long countIncompleteByUserId(UUID userId);
}
