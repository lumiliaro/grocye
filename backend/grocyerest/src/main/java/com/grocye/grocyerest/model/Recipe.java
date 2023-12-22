package com.grocye.grocyerest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "recipe")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description",length = 10000, nullable = false)
    private String description;


    // Liste von Zutaten als eingebettete Objekte
    @ElementCollection
    @CollectionTable(name = "recipe_ingredient", joinColumns = @JoinColumn(name = "recipe_id"))
    private List<Ingredient> ingredients;

    // Anweisungen
    @ElementCollection
    @CollectionTable(name = "recipe_instruction", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "instruction")
    private List<String> instructions;

    @Column(name = "prep_time")
    private String prepTime;

    @Column(name = "cook_time")
    private String cookTime;

    @Column(name = "total_time")
    private String totalTime;

    @Column(name = "servings")
    private String servings;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty; // Easy, Intermediate, Advanced

    @Column(name = "cuisine")
    private String cuisine;

    @Column(name = "course")
    private String course;

    @ElementCollection
    @CollectionTable(name = "recipe_tags", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "tag")
    private Set<String> tags;

    @Embedded
    private NutritionalInformation nutritionalInformation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Konstruktoren, Getter und Setter...

    // Enum für Schwierigkeitsgrade
    public enum Difficulty {
        Easy, Intermediate, Advanced
    }

    // Embedded-Klasse für Zutaten
    @Data
    @Embeddable
    public static class Ingredient {
        private String name;
        private String quantity;
    }

    // Embedded-Klasse für Nährwertinformationen
    @Data
    @Embeddable
    public static class NutritionalInformation {
        private String calories;
        private String protein;
        private String carbohydrates;
        private String fat;
    }
}
