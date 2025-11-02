package com.recipeapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Response DTO for Recipe
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeResponse {
    private UUID id;
    private String title;
    private String description;
    private UUID cuisineId;
    private String cuisineName;
    private Integer cookingTime;
    private Integer servings;
    private String difficultyLevel;
    private LocalDateTime createdAt;
}
