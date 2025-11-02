package com.recipeapp.model;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * ShoppingListItem Entity - Represents an item on a shopping list
 */
@Entity
@Table(name = "shopping_list_items")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingListItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopping_list_id", nullable = false)
    private ShoppingList shoppingList;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;
    
    @Column(nullable = false)
    private Double quantity;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", nullable = false)
    private Unit unit;
    
    @Column(nullable = false)
    private Boolean purchased;
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (purchased == null) {
            purchased = false;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
