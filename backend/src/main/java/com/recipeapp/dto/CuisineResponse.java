package com.recipeapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Response DTO for Cuisine
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CuisineResponse {
    private UUID id;
    private String name;
    private String description;
}
