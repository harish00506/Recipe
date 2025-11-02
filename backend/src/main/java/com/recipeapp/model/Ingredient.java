package com.recipeapp.model;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Ingredient Entity - Represents an ingredient used in recipes
 */
@Entity
@Table(name = "ingredients")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(nullable = false, unique = true, length = 255)
    private String name;
    
    @Column(nullable = false, length = 100)
    private String category;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<RecipeIngredient> recipeIngredients = new ArrayList<>();
    
    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ShoppingListItem> shoppingListItems = new ArrayList<>();
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
