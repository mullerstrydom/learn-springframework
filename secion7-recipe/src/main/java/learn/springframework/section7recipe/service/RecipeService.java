package learn.springframework.section7recipe.service;

import learn.springframework.section7recipe.commands.RecipeCommand;
import learn.springframework.section7recipe.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Recipe findById(Long id);

    Set<Recipe> getRecipes();

    RecipeCommand findCommandById(Long id);

    RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);

    void deleteById(Long id);
}
