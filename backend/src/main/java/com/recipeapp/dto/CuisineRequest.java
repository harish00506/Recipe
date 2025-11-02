package com.recipeapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for Cuisine
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CuisineRequest {
    private String name;
    private String description;
}
