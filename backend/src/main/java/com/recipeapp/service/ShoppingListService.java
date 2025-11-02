package com.recipeapp.service;

import com.recipeapp.exception.ResourceNotFoundException;
import com.recipeapp.model.ShoppingList;
import com.recipeapp.model.ShoppingListItem;
import com.recipeapp.model.User;
import com.recipeapp.repository.ShoppingListRepository;
import com.recipeapp.repository.ShoppingListItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Service for shopping list-related operations
 * Manages shopping list creation, items, and consolidation
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;
    private final ShoppingListItemRepository shoppingListItemRepository;

    /**
     * Get all shopping lists for a user with pagination
     * @param userId user ID
     * @param pageable pagination info
     * @return Page of shopping lists
     */
    public Page<ShoppingList> getShoppingListsByUser(UUID userId, Pageable pageable) {
        log.debug("Fetching shopping lists for user: {}", userId);
        return shoppingListRepository.findByUserId(userId, pageable);
    }

    /**
     * Get shopping list by ID
     * @param shoppingListId the shopping list ID
     * @return ShoppingList if found
     * @throws ResourceNotFoundException if shopping list not found
     */
    public ShoppingList getShoppingListById(UUID shoppingListId) {
        log.debug("Fetching shopping list by ID: {}", shoppingListId);
        return shoppingListRepository.findById(shoppingListId)
                .orElseThrow(() -> new ResourceNotFoundException("ShoppingList", "id", shoppingListId));
    }

    /**
     * Create a new shopping list
     * @param user the user
     * @param name shopping list name
     * @return created ShoppingList
     */
    @Transactional
    public ShoppingList createShoppingList(User user, String name) {
        log.info("Creating new shopping list: {} for user: {}", name, user.getId());

        ShoppingList shoppingList = ShoppingList.builder()
                .user(user)
                .name(name)
                .build();

        ShoppingList savedShoppingList = shoppingListRepository.save(shoppingList);
        log.info("Shopping list created successfully: {}", name);

        return savedShoppingList;
    }

    /**
     * Update shopping list name
     * @param shoppingListId the shopping list ID
     * @param name new name
     * @return updated ShoppingList
     * @throws ResourceNotFoundException if shopping list not found
     */
    @Transactional
    public ShoppingList updateShoppingListName(UUID shoppingListId, String name) {
        log.debug("Updating shopping list name: {}", shoppingListId);

        ShoppingList shoppingList = getShoppingListById(shoppingListId);

        if (name != null && !name.isBlank()) {
            shoppingList.setName(name);
        }

        ShoppingList updatedShoppingList = shoppingListRepository.save(shoppingList);
        log.info("Shopping list name updated: {}", shoppingListId);

        return updatedShoppingList;
    }

    /**
     * Delete shopping list
     * @param shoppingListId the shopping list ID
     * @throws ResourceNotFoundException if shopping list not found
     */
    @Transactional
    public void deleteShoppingList(UUID shoppingListId) {
        log.debug("Deleting shopping list: {}", shoppingListId);

        if (!shoppingListRepository.existsById(shoppingListId)) {
            throw new ResourceNotFoundException("ShoppingList", "id", shoppingListId);
        }

        shoppingListRepository.deleteById(shoppingListId);
        log.info("Shopping list deleted successfully: {}", shoppingListId);
    }

    /**
     * Add item to shopping list
     * @param shoppingListId the shopping list ID
     * @param ingredientId the ingredient ID
     * @param quantity quantity needed
     * @param unit unit of measurement
     * @return created ShoppingListItem
     * @throws ResourceNotFoundException if shopping list not found
     */
    @Transactional
    public ShoppingListItem addItemToShoppingList(UUID shoppingListId, UUID ingredientId, 
                                                   Double quantity, String unit) {
        log.debug("Adding item to shopping list: {}", shoppingListId);

        ShoppingList shoppingList = getShoppingListById(shoppingListId);

        ShoppingListItem item = ShoppingListItem.builder()
                .shoppingList(shoppingList)
                .ingredientId(ingredientId)
                .quantity(quantity)
                .unit(unit)
                .isChecked(false)
                .build();

        ShoppingListItem savedItem = shoppingListItemRepository.save(item);
        log.info("Item added to shopping list: {}", shoppingListId);

        return savedItem;
    }

    /**
     * Get all items in a shopping list
     * @param shoppingListId the shopping list ID
     * @return List of shopping list items
     */
    public List<ShoppingListItem> getShoppingListItems(UUID shoppingListId) {
        log.debug("Fetching items for shopping list: {}", shoppingListId);
        return shoppingListItemRepository.findByShoppingListId(shoppingListId);
    }

    /**
     * Update shopping list item
     * @param itemId the item ID
     * @param quantity new quantity
     * @param unit new unit
     * @return updated ShoppingListItem
     * @throws ResourceNotFoundException if item not found
     */
    @Transactional
    public ShoppingListItem updateShoppingListItem(UUID itemId, Double quantity, String unit) {
        log.debug("Updating shopping list item: {}", itemId);

        ShoppingListItem item = shoppingListItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("ShoppingListItem", "id", itemId));

        if (quantity != null && quantity > 0) {
            item.setQuantity(quantity);
        }

        if (unit != null && !unit.isBlank()) {
            item.setUnit(unit);
        }

        ShoppingListItem updatedItem = shoppingListItemRepository.save(item);
        log.info("Shopping list item updated: {}", itemId);

        return updatedItem;
    }

    /**
     * Mark item as checked/unchecked
     * @param itemId the item ID
     * @param isChecked checked status
     * @return updated ShoppingListItem
     * @throws ResourceNotFoundException if item not found
     */
    @Transactional
    public ShoppingListItem toggleItemChecked(UUID itemId, Boolean isChecked) {
        log.debug("Toggling checked status for item: {}", itemId);

        ShoppingListItem item = shoppingListItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("ShoppingListItem", "id", itemId));

        if (isChecked != null) {
            item.setIsChecked(isChecked);
        }

        ShoppingListItem updatedItem = shoppingListItemRepository.save(item);
        log.info("Item checked status toggled: {}", itemId);

        return updatedItem;
    }

    /**
     * Remove item from shopping list
     * @param itemId the item ID
     * @throws ResourceNotFoundException if item not found
     */
    @Transactional
    public void removeItemFromShoppingList(UUID itemId) {
        log.debug("Removing item from shopping list: {}", itemId);

        if (!shoppingListItemRepository.existsById(itemId)) {
            throw new ResourceNotFoundException("ShoppingListItem", "id", itemId);
        }

        shoppingListItemRepository.deleteById(itemId);
        log.info("Item removed from shopping list: {}", itemId);
    }

    /**
     * Get unchecked items in shopping list
     * @param shoppingListId the shopping list ID
     * @return List of unchecked items
     */
    public List<ShoppingListItem> getUncheckedItems(UUID shoppingListId) {
        log.debug("Fetching unchecked items for shopping list: {}", shoppingListId);
        return shoppingListItemRepository.findByShoppingListIdAndIsCheckedFalse(shoppingListId);
    }

    /**
     * Get checked items in shopping list
     * @param shoppingListId the shopping list ID
     * @return List of checked items
     */
    public List<ShoppingListItem> getCheckedItems(UUID shoppingListId) {
        log.debug("Fetching checked items for shopping list: {}", shoppingListId);
        return shoppingListItemRepository.findByShoppingListIdAndIsCheckedTrue(shoppingListId);
    }

    /**
     * Clear all checked items from shopping list
     * @param shoppingListId the shopping list ID
     */
    @Transactional
    public void clearCheckedItems(UUID shoppingListId) {
        log.debug("Clearing checked items from shopping list: {}", shoppingListId);
        shoppingListItemRepository.deleteByShoppingListIdAndIsCheckedTrue(shoppingListId);
        log.info("Checked items cleared from shopping list: {}", shoppingListId);
    }

    /**
     * Check if shopping list exists
     * @param shoppingListId the shopping list ID
     * @return true if exists, false otherwise
     */
    public boolean exists(UUID shoppingListId) {
        return shoppingListRepository.existsById(shoppingListId);
    }

    /**
     * Get shopping list item count
     * @param shoppingListId the shopping list ID
     * @return total number of items in the list
     */
    public long getItemCount(UUID shoppingListId) {
        return shoppingListItemRepository.countByShoppingListId(shoppingListId);
    }

    /**
     * Get unchecked item count
     * @param shoppingListId the shopping list ID
     * @return count of unchecked items
     */
    public long getUncheckedItemCount(UUID shoppingListId) {
        return shoppingListItemRepository.countByShoppingListIdAndIsCheckedFalse(shoppingListId);
    }

    /**
     * Clear entire shopping list
     * @param shoppingListId the shopping list ID
     */
    @Transactional
    public void clearShoppingList(UUID shoppingListId) {
        log.debug("Clearing all items from shopping list: {}", shoppingListId);
        shoppingListItemRepository.deleteByShoppingListId(shoppingListId);
        log.info("Shopping list cleared: {}", shoppingListId);
    }
}
