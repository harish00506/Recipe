package com.recipeapp.model;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Recipe Entity - Represents a recipe with cooking instructions
 */
@Entity
@Table(name = "recipes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(nullable = false, length = 255)
    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(columnDefinition = "TEXT", nullable = false)
    private String instructions;
    
    @Column(nullable = false)
    private Integer servings;
    
    @Column(nullable = false)
    private Integer prepTimeMinutes;
    
    @Column(nullable = false)
    private Integer cookTimeMinutes;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuisine_id", nullable = false)
    private Cuisine cuisine;
    
    @Column(length = 500)
    private String imageUrl;
    
    @Column(nullable = false)
    private Boolean isPublic;
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    @Column(length = 50)
    private String difficultyLevel;
    
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<RecipeIngredient> recipeIngredients = new ArrayList<>();
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (isPublic == null) {
            isPublic = false;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
