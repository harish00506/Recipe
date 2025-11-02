package com.recipeapp.controller;

import com.recipeapp.dto.ShoppingListItemRequest;
import com.recipeapp.dto.ShoppingListRequest;
import com.recipeapp.dto.ShoppingListResponse;
import com.recipeapp.model.ShoppingList;
import com.recipeapp.model.ShoppingListItem;
import com.recipeapp.service.ShoppingListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

/**
 * REST Controller for Shopping List operations
 * Provides endpoints for managing user shopping lists and items
 */
@RestController
@RequestMapping("/api/shopping-lists")
@RequiredArgsConstructor
@Slf4j
public class ShoppingListController {
    
    private final ShoppingListService shoppingListService;
    
    /**
     * GET /api/shopping-lists
     * Retrieve all shopping lists for the authenticated user
     *
     * @return List of ShoppingListResponse
     */
    @GetMapping
    public ResponseEntity<List<ShoppingListResponse>> getUserShoppingLists() {
        log.debug("Getting shopping lists for user");
        // In production, extract user from SecurityContext
        List<ShoppingList> lists = shoppingListService.getUserShoppingLists();
        List<ShoppingListResponse> responses = lists.stream()
                .map(this::toShoppingListResponse)
                .toList();
        log.info("Retrieved {} shopping lists", responses.size());
        return ResponseEntity.ok(responses);
    }
    
    /**
     * GET /api/shopping-lists/{id}
     * Retrieve a specific shopping list by ID
     *
     * @param id Shopping List ID
     * @return ShoppingListResponse
     */
    @GetMapping("/{id}")
    public ResponseEntity<ShoppingListResponse> getShoppingListById(@PathVariable UUID id) {
        log.debug("Getting shopping list by ID: {}", id);
        ShoppingList list = shoppingListService.getShoppingListById(id);
        log.info("Retrieved shopping list: {}", list.getName());
        return ResponseEntity.ok(toShoppingListResponse(list));
    }
    
    /**
     * POST /api/shopping-lists
     * Create a new shopping list
     *
     * @param request ShoppingListRequest with name
     * @return Created ShoppingListResponse
     */
    @PostMapping
    public ResponseEntity<ShoppingListResponse> createShoppingList(
            @Valid @RequestBody ShoppingListRequest request) {
        log.debug("Creating shopping list: {}", request.getName());
        ShoppingList list = ShoppingList.builder()
                .name(request.getName())
                .build();
        
        ShoppingList created = shoppingListService.createShoppingList(list);
        log.info("Shopping list created: {} with ID: {}", created.getName(), created.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(toShoppingListResponse(created));
    }
    
    /**
     * PUT /api/shopping-lists/{id}
     * Update shopping list name
     *
     * @param id Shopping List ID
     * @param request Updated ShoppingListRequest
     * @return Updated ShoppingListResponse
     */
    @PutMapping("/{id}")
    public ResponseEntity<ShoppingListResponse> updateShoppingList(
            @PathVariable UUID id,
            @Valid @RequestBody ShoppingListRequest request) {
        log.debug("Updating shopping list: {}", id);
        ShoppingList list = shoppingListService.getShoppingListById(id);
        list.setName(request.getName());
        
        ShoppingList updated = shoppingListService.updateShoppingList(id, list);
        log.info("Shopping list updated: {}", updated.getName());
        return ResponseEntity.ok(toShoppingListResponse(updated));
    }
    
    /**
     * DELETE /api/shopping-lists/{id}
     * Delete a shopping list
     *
     * @param id Shopping List ID
     * @return No content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShoppingList(@PathVariable UUID id) {
        log.debug("Deleting shopping list: {}", id);
        shoppingListService.deleteShoppingList(id);
        log.info("Shopping list deleted: {}", id);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * POST /api/shopping-lists/{id}/items
     * Add an item to a shopping list
     *
     * @param id Shopping List ID
     * @param request ShoppingListItemRequest
     * @return Updated ShoppingListResponse
     */
    @PostMapping("/{id}/items")
    public ResponseEntity<ShoppingListResponse> addItemToShoppingList(
            @PathVariable UUID id,
            @Valid @RequestBody ShoppingListItemRequest request) {
        log.debug("Adding item to shopping list: {}", id);
        ShoppingListItem item = ShoppingListItem.builder()
                .ingredientId(request.getIngredientId())
                .quantity(request.getQuantity())
                .unit(request.getUnit())
                .isChecked(false)
                .build();
        
        ShoppingList updated = shoppingListService.addItemToShoppingList(id, item);
        log.info("Item added to shopping list: {}", id);
        return ResponseEntity.ok(toShoppingListResponse(updated));
    }
    
    /**
     * PUT /api/shopping-lists/{id}/items/{itemId}
     * Update a shopping list item
     *
     * @param id Shopping List ID
     * @param itemId Item ID
     * @param request Updated ShoppingListItemRequest
     * @return Updated ShoppingListResponse
     */
    @PutMapping("/{id}/items/{itemId}")
    public ResponseEntity<ShoppingListResponse> updateShoppingListItem(
            @PathVariable UUID id,
            @PathVariable UUID itemId,
            @Valid @RequestBody ShoppingListItemRequest request) {
        log.debug("Updating item {} in shopping list: {}", itemId, id);
        ShoppingList list = shoppingListService.getShoppingListById(id);
        ShoppingListItem item = list.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));
        
        item.setQuantity(request.getQuantity());
        item.setUnit(request.getUnit());
        
        ShoppingList updated = shoppingListService.updateShoppingList(id, list);
        log.info("Item updated in shopping list: {}", id);
        return ResponseEntity.ok(toShoppingListResponse(updated));
    }
    
    /**
     * DELETE /api/shopping-lists/{id}/items/{itemId}
     * Remove an item from a shopping list
     *
     * @param id Shopping List ID
     * @param itemId Item ID
     * @return Updated ShoppingListResponse
     */
    @DeleteMapping("/{id}/items/{itemId}")
    public ResponseEntity<ShoppingListResponse> removeItemFromShoppingList(
            @PathVariable UUID id,
            @PathVariable UUID itemId) {
        log.debug("Removing item {} from shopping list: {}", itemId, id);
        ShoppingList updated = shoppingListService.removeItemFromShoppingList(id, itemId);
        log.info("Item removed from shopping list: {}", id);
        return ResponseEntity.ok(toShoppingListResponse(updated));
    }
    
    /**
     * PATCH /api/shopping-lists/{id}/items/{itemId}/check
     * Toggle checked status of a shopping list item
     *
     * @param id Shopping List ID
     * @param itemId Item ID
     * @return Updated ShoppingListResponse
     */
    @PatchMapping("/{id}/items/{itemId}/check")
    public ResponseEntity<ShoppingListResponse> toggleItemChecked(
            @PathVariable UUID id,
            @PathVariable UUID itemId) {
        log.debug("Toggling item {} checked status in shopping list: {}", itemId, id);
        ShoppingList updated = shoppingListService.toggleItemChecked(id, itemId);
        log.info("Item checked status toggled in shopping list: {}", id);
        return ResponseEntity.ok(toShoppingListResponse(updated));
    }
    
    /**
     * Convert ShoppingList entity to ShoppingListResponse DTO
     */
    private ShoppingListResponse toShoppingListResponse(ShoppingList list) {
        return ShoppingListResponse.builder()
                .id(list.getId())
                .name(list.getName())
                .itemCount(list.getItems() != null ? list.getItems().size() : 0)
                .checkedCount((int) (list.getItems() != null ? 
                        list.getItems().stream().filter(ShoppingListItem::isChecked).count() : 0))
                .build();
    }
}
