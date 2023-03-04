package learn.springframework.section7recipe.service;

import jakarta.transaction.Transactional;
import learn.springframework.section7recipe.commands.RecipeCommand;
import learn.springframework.section7recipe.converters.RecipeCommandToRecipe;
import learn.springframework.section7recipe.converters.RecipeToRecipeCommand;
import learn.springframework.section7recipe.domain.Recipe;
import learn.springframework.section7recipe.exceptions.NotFoundException;
import learn.springframework.section7recipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    private final RecipeToRecipeCommand recipeToRecipeCommand;
    private final RecipeCommandToRecipe recipeCommandToRecipe;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeToRecipeCommand recipeToRecipeCommand, RecipeCommandToRecipe recipeCommandToRecipe) {
        this.recipeRepository = recipeRepository;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
    }

    @Override
    public Recipe findById(Long id) {
        log.debug("RecipeServiceImpl.findById(Long)");
        Optional<Recipe> optionalRecipe = this.recipeRepository.findById(id);
        if(optionalRecipe.isEmpty()) {
            throw new NotFoundException("Recipe, id="+ id +", not found");
        }
        return optionalRecipe.get();
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("RecipeServiceImpl.getRecipes()");

        Set<Recipe> results = new HashSet<>();
        this.recipeRepository.findAll().iterator().forEachRemaining(results::add);
        return results;
    }

    @Override
    public RecipeCommand findCommandById(Long id) {
        return recipeToRecipeCommand.convert(findById(id));
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(recipeCommand);
        if(detachedRecipe == null) throw new RuntimeException("Recipe should not be null");

        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug("Saved recipe: " + savedRecipe.getId());
        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }
}
