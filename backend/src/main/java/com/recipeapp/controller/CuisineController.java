package com.recipeapp.controller;

import com.recipeapp.dto.CuisineRequest;
import com.recipeapp.dto.CuisineResponse;
import com.recipeapp.model.Cuisine;
import com.recipeapp.service.CuisineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * REST Controller for cuisine endpoints
 * Handles cuisine CRUD operations
 */
@RestController
@RequestMapping("/api/cuisines")
@RequiredArgsConstructor
@Slf4j
public class CuisineController {

    private final CuisineService cuisineService;

    /**
     * Get all cuisines sorted
     * GET /api/cuisines
     */
    @GetMapping
    public ResponseEntity<List<CuisineResponse>> getAllCuisines() {
        log.debug("Fetching all cuisines");
        List<Cuisine> cuisines = cuisineService.getAllCuisines();
        List<CuisineResponse> responses = cuisines.stream()
                .map(this::toCuisineResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    /**
     * Get cuisine by ID
     * GET /api/cuisines/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<CuisineResponse> getCuisineById(@PathVariable UUID id) {
        log.debug("Fetching cuisine by ID: {}", id);
        Cuisine cuisine = cuisineService.getCuisineById(id);
        return ResponseEntity.ok(toCuisineResponse(cuisine));
    }

    /**
     * Search cuisines by name
     * GET /api/cuisines/search?name=Italian
     */
    @GetMapping("/search")
    public ResponseEntity<List<CuisineResponse>> searchByName(@RequestParam String name) {
        log.debug("Searching cuisines by name: {}", name);
        List<Cuisine> cuisines = cuisineService.searchByName(name);
        List<CuisineResponse> responses = cuisines.stream()
                .map(this::toCuisineResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    /**
     * Create new cuisine
     * POST /api/cuisines
     */
    @PostMapping
    public ResponseEntity<CuisineResponse> createCuisine(@Valid @RequestBody CuisineRequest cuisineRequest) {
        log.info("Creating new cuisine: {}", cuisineRequest.getName());
        
        Cuisine cuisine = cuisineService.createCuisine(
                cuisineRequest.getName(),
                cuisineRequest.getDescription()
        );
        
        return new ResponseEntity<>(toCuisineResponse(cuisine), HttpStatus.CREATED);
    }

    /**
     * Update cuisine
     * PUT /api/cuisines/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<CuisineResponse> updateCuisine(
            @PathVariable UUID id,
            @Valid @RequestBody CuisineRequest cuisineRequest) {
        
        log.info("Updating cuisine: {}", id);
        
        Cuisine cuisine = cuisineService.updateCuisine(
                id,
                cuisineRequest.getName(),
                cuisineRequest.getDescription()
        );
        
        return ResponseEntity.ok(toCuisineResponse(cuisine));
    }

    /**
     * Delete cuisine
     * DELETE /api/cuisines/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCuisine(@PathVariable UUID id) {
        log.info("Deleting cuisine: {}", id);
        cuisineService.deleteCuisine(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Convert Cuisine to CuisineResponse DTO
     */
    private CuisineResponse toCuisineResponse(Cuisine cuisine) {
        return CuisineResponse.builder()
                .id(cuisine.getId())
                .name(cuisine.getName())
                .description(cuisine.getDescription())
                .build();
    }
}
