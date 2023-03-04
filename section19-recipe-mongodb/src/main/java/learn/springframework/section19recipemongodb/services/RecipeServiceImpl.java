package learn.springframework.section19recipemongodb.services;

import learn.springframework.section19recipemongodb.domain.documents.Ingredient;
import learn.springframework.section19recipemongodb.domain.documents.Recipe;
import learn.springframework.section19recipemongodb.repository.reactive.IngredientReactiveRepository;
import learn.springframework.section19recipemongodb.repository.reactive.RecipeReactiveRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@AllArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private RecipeReactiveRepository recipeReactiveRepository;

    private IngredientReactiveRepository ingredientReactiveRepository;

    @Override
    public Flux<Recipe> findAll() {
        return recipeReactiveRepository.findAll();
    }

    @Override
    public Mono<Recipe> findById(String id) {
        return recipeReactiveRepository.findById(id);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return recipeReactiveRepository.deleteById(id);
    }

    @Override
    public Mono<Recipe> save(Recipe recipe) {
        return recipeReactiveRepository.save(recipe);
    }

    @Override
    public Mono<Ingredient> findIngredientByRecipeIdAndIngredientId(String recipeId, String ingredientId) {
        Mono<Recipe> recipe = recipeReactiveRepository.findById(recipeId);
        return recipe.mapNotNull(r -> r.getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .findFirst().orElse(null));
    }

    @Override
    public Mono<Void> deleteIngredientByRecipeIdAndIngredientId(String recipeId, String ingredientId) {
        return findById(recipeId).flatMap(recipe -> {
           recipe.getIngredients().removeIf(ingredient -> ingredientId.equals(ingredient.getId()));
           return save(recipe)
                   .and(ingredientReactiveRepository.deleteById(ingredientId));
        });
    }
}
