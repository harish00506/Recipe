package com.recipeapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Request DTO for Recipe
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeRequest {
    private String title;
    private String description;
    private UUID cuisineId;
    private Integer cookingTime;
    private Integer servings;
    private String difficultyLevel;
}
