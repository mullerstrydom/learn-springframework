package learn.springframework.section7recipe.service;

import learn.springframework.section7recipe.commands.IngredientCommand;
import learn.springframework.section7recipe.converters.IngredientCommandToIngredient;
import learn.springframework.section7recipe.converters.IngredientToIngredientCommand;
import learn.springframework.section7recipe.domain.Ingredient;
import learn.springframework.section7recipe.domain.Recipe;
import learn.springframework.section7recipe.repositories.RecipeRepository;
import learn.springframework.section7recipe.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient,
                                 RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if(recipeOptional.isEmpty()) {
            log.error("Recipe could not be found by Id: " + recipeId);
            throw new RuntimeException();
        }

        Recipe recipe = recipeOptional.get();
        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient))
                .findFirst();

        if(ingredientCommandOptional.isEmpty()) {
            log.error("Ingredient for recipe, " + recipeId + ", could not be found by id:" + ingredientId);
            throw new RuntimeException();
        }
        return ingredientCommandOptional.get();
    }

    @Override
    public IngredientCommand saveIngredient(IngredientCommand ingredientCommand) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());
        if(recipeOptional.isEmpty()) {
            log.error("Recipe could not be found by id: " + ingredientCommand.getRecipeId());
            throw new RuntimeException();
        }

        Recipe recipe = recipeOptional.get();
        Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                .findFirst();

        if(ingredientOptional.isPresent()) {
            Ingredient ingredient = ingredientOptional.get();
            ingredient.setDescription(ingredientCommand.getDescription());
            ingredient.setAmount(ingredientCommand.getAmount());
            ingredient.setUom(unitOfMeasureRepository
                    .findById(ingredientCommand.getUom().getId())
                    .orElseThrow(()-> new RuntimeException("UnitOfMeasure not found by Id: " + ingredient.getUom().getId())));
        } else {
            Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
            ingredient.setRecipe(recipe);
            recipe.addIngredient(ingredient);
        }

        Recipe savedRecipe = recipeRepository.save(recipe);

        Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                .findFirst();

        // if it was a new ingredient the command would not have the id
        if(savedIngredientOptional.isEmpty()) {
            savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getDescription().equals(ingredientCommand.getDescription()))
                    .filter(ingredient -> ingredient.getAmount().equals(ingredientCommand.getAmount()))
                    .filter(ingredient -> ingredient.getUom().getId().equals(ingredientCommand.getUom().getId()))
                    .findFirst();
        }
        return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
    }

    @Override
    public void deleteById(Long recipeId, Long idToDelete) {
        log.debug("Deleting ingredient (recipe: "+ recipeId +", ingredient: " + idToDelete + ")");

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if(recipeOptional.isEmpty()) {
            log.debug("> Cannot delete ingredient from recipe, as recipe not found by id");
            return;
        }

        Recipe recipe = recipeOptional.get();
        log.debug("> (1/3) Found recipe");

        Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(idToDelete))
                .findFirst();

        if(ingredientOptional.isEmpty()) {
            log.debug("> Cannot delete ingredient from recipe, as the ingredient could not be found by id");
            return;
        }

        Ingredient ingredientToDelete = ingredientOptional.get();
        log.debug("> (2/3) Found Ingredient");
        ingredientToDelete.setRecipe(null);
        recipe.getIngredients().remove(ingredientToDelete);
        recipeRepository.save(recipe);
        log.debug("> (3/3) Ingredient removed from recipe");
    }
}
