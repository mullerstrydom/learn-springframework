package learn.springframework.section19recipemongodb.repository.reactive;

import learn.springframework.section19recipemongodb.domain.documents.Ingredient;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface IngredientReactiveRepository extends ReactiveMongoRepository<Ingredient, String> {

}
