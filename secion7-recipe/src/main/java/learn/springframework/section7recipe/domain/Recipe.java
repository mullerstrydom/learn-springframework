package learn.springframework.section7recipe.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"notes", "categories", "ingredients"})
@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    @Lob
    private String directions;
    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;

    @Lob
    private Byte[] image;
    @OneToOne(cascade = CascadeType.ALL)
    private Note notes;

    @ManyToMany
    @JoinTable(name = "recipe_category",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients = new HashSet<>();

    public void setNotes(Note notes) {
        this.notes = notes;
        notes.setRecipe(this);
    }
    public Recipe addIngredient(Ingredient ingredient){
        ingredient.setRecipe(this);
        this.getIngredients().add(ingredient);
        return this;
    }

}
