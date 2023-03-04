package learn.springframework.section7recipe.converters;

import learn.springframework.section7recipe.commands.RecipeCommand;
import learn.springframework.section7recipe.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    private final NoteToNoteCommand notesConverter;
    private final CategoryToCategoryCommand categoryConverter;
    private final IngredientToIngredientCommand ingredientConverter;

    public RecipeToRecipeCommand(NoteToNoteCommand notesConverter, CategoryToCategoryCommand categoryConverter, IngredientToIngredientCommand ingredientConverter) {
        this.notesConverter = notesConverter;
        this.categoryConverter = categoryConverter;
        this.ingredientConverter = ingredientConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe source) {
        if(source == null) return null;
        RecipeCommand command = new RecipeCommand();
        command.setId(source.getId());
        command.setCookTime(source.getCookTime());
        command.setPrepTime(source.getPrepTime());
        command.setDescription(source.getDescription());
        command.setDifficulty(source.getDifficulty());
        command.setDirections(source.getDirections());
        command.setServings(source.getServings());
        command.setSource(source.getSource());
        command.setUrl(source.getUrl());
        command.setImage(source.getImage());

        if(source.getNotes() != null) {
            command.setNotes(notesConverter.convert(source.getNotes()));
        }

        if(source.getCategories().size() > 0){
            source.getCategories().forEach(category -> command.getCategories().add(categoryConverter.convert(category)));
        }
        if(source.getIngredients().size() > 0){
            source.getIngredients().forEach(ingredient -> command.getIngredients().add(ingredientConverter.convert(ingredient)));
        }

        return command;
    }
}
