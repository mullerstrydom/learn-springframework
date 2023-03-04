package learn.springframework.section7recipe.service;

import learn.springframework.section7recipe.commands.IngredientCommand;

public interface IngredientService {

    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

    IngredientCommand saveIngredient(IngredientCommand ingredientCommand);

    void deleteById(Long recipeId, Long idToDelete);

}
