package com.recipeapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Request DTO for Shopping List Item
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShoppingListItemRequest {
    private UUID ingredientId;
    private Double quantity;
    private String unit;
}
