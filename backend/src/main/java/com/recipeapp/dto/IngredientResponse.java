package com.recipeapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Response DTO for Ingredient
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IngredientResponse {
    private UUID id;
    private String name;
    private String category;
    private String unit;
    private Double calories;
    private Double protein;
    private Double fat;
    private Double carbs;
}
