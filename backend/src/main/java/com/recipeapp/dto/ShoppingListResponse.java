package com.recipeapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Response DTO for Shopping List
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShoppingListResponse {
    private UUID id;
    private String name;
    private int itemCount;
    private int checkedCount;
}
