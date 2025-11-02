package com.recipeapp.model;

import lombok.*;
import jakarta.persistence.*;
import java.util.UUID;

/**
 * Unit Entity - Represents measurement units (e.g., cup, tablespoon, gram)
 */
@Entity
@Table(name = "units")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Unit {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(nullable = false, unique = true, length = 50)
    private String name;
    
    @Column(nullable = false, unique = true, length = 20)
    private String abbreviation;
}
