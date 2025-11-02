package com.recipeapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for Ingredient
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IngredientRequest {
    private String name;
    private String category;
    private String unit;
    private Double calories;
    private Double protein;
    private Double fat;
    private Double carbs;
}
