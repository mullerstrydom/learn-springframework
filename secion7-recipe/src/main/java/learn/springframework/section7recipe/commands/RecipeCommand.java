package learn.springframework.section7recipe.commands;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import learn.springframework.section7recipe.domain.Difficulty;
import learn.springframework.section7recipe.domain.Note;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {

    private Long id;

    @NotBlank
    @Size(min = 3, max = 255)
    private String description;

    @Min(1)
    @Max(999)
    private Integer prepTime;

    @Min(1)
    @Max(999)
    private Integer cookTime;

    @Min(1)
    @Max(100)
    private Integer servings;

    private String source;

    @URL
    private String url;

    @NotBlank
    private String directions;

    private Difficulty difficulty;
    private Byte[] image;
    private NoteCommand notes;
    private Set<CategoryCommand> categories = new HashSet<>();
    private Set<IngredientCommand> ingredients = new HashSet<>();

}
