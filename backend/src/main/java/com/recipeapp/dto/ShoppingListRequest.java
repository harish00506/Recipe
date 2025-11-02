package com.recipeapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for Shopping List
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShoppingListRequest {
    private String name;
}
