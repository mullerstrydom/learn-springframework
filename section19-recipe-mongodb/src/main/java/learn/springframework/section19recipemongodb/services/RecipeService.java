package learn.springframework.section19recipemongodb.services;

import learn.springframework.section19recipemongodb.domain.documents.Ingredient;
import learn.springframework.section19recipemongodb.domain.documents.Recipe;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RecipeService {

    Flux<Recipe> findAll();

    Mono<Recipe> findById(String id);

    Mono<Void> deleteById(String id);

    Mono<Recipe> save(Recipe recipe);

    Mono<Ingredient> findIngredientByRecipeIdAndIngredientId(String recipeId, String ingredientId);

    Mono<Void> deleteIngredientByRecipeIdAndIngredientId(String recipeId, String ingredientId);
}
