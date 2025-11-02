package com.recipeapp.model;

import lombok.*;
import jakarta.persistence.*;
import java.util.UUID;

/**
 * RecipeIngredient Entity - Junction table for Recipe and Ingredient many-to-many relationship
 */
@Entity
@Table(name = "recipe_ingredients")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeIngredient {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;
    
    @Column(nullable = false)
    private Double quantity;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", nullable = false)
    private Unit unit;
    
    @Column(nullable = true, length = 500)
    private String notes;
}
