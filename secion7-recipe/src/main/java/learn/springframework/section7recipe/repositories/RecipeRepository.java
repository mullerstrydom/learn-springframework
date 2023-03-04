package learn.springframework.section7recipe.repositories;

import learn.springframework.section7recipe.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
