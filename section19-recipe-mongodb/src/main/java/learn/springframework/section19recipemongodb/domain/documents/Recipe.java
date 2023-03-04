package learn.springframework.section19recipemongodb.domain.documents;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document
public class Recipe {

    private String id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    @DBRef
    private List<Ingredient> ingredients = new ArrayList<>();
    private Byte[] image;
    private Difficulty difficulty;
    @DBRef
    private Note note;
    @DBRef
    private List<Category> categories = new ArrayList<>();
}
