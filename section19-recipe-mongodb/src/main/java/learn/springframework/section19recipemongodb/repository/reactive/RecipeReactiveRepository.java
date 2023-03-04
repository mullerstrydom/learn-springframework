package learn.springframework.section19recipemongodb.repository.reactive;

import learn.springframework.section19recipemongodb.domain.documents.Ingredient;
import learn.springframework.section19recipemongodb.domain.documents.Recipe;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface RecipeReactiveRepository extends ReactiveMongoRepository<Recipe, String> {

    Mono<Recipe> findByDescription(String description);

    Mono<Recipe> findByIdAndIngredientsId(String recipeId, String ingredientId);
}
