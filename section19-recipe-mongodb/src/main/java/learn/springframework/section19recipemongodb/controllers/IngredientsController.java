package learn.springframework.section19recipemongodb.controllers;

import learn.springframework.section19recipemongodb.domain.documents.Ingredient;
import learn.springframework.section19recipemongodb.domain.documents.Recipe;
import learn.springframework.section19recipemongodb.domain.documents.UnitOfMeasure;
import learn.springframework.section19recipemongodb.repository.reactive.IngredientReactiveRepository;
import learn.springframework.section19recipemongodb.repository.reactive.UnitOfMeasureReactiveRepository;
import learn.springframework.section19recipemongodb.services.RecipeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("recipe/{recipeId}/ingredient")
public class IngredientsController {

    private final RecipeService recipeService;
    private final IngredientReactiveRepository ingredientReactiveRepository;
    private final UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    @GetMapping({"", "/"})
    public String listIngredients(@PathVariable String recipeId, Model model) {
        log.debug("Getting ingredient list for recipe id: " + recipeId);
        model.addAttribute("recipe", recipeService.findById(recipeId).block());
        return "recipe/ingredient/list";
    }

    @GetMapping({"/{ingredientId}/show"})
    public String showExistingIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
        Ingredient ingredient = recipeService.findIngredientByRecipeIdAndIngredientId(recipeId, ingredientId).block();
        model.addAttribute("ingredient", ingredient);
        return "recipe/ingredient/show";
    }

    @GetMapping("/new")
    public String getNewIngredientForm(@PathVariable String recipeId, Model model){

        //make sure we have a good id value
        Recipe recipe = recipeService.findById(recipeId).block();

        //need to return back parent id for hidden form property
        Ingredient ingredient = new Ingredient();
        ingredient.setRecipe(recipe);
        model.addAttribute("ingredient", ingredient);

        //init uom
        ingredient.setUom(UnitOfMeasure.builder().build());

        model.addAttribute("uomList",  unitOfMeasureReactiveRepository.findAll().collectList().block());

        return "recipe/ingredient/ingredient_form";
    }

    @GetMapping({"/{ingredientId}/update", "/{ingredientId}/update/"})
    public String getUpdateIngredientForm(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
        model.addAttribute("ingredient", recipeService.findIngredientByRecipeIdAndIngredientId(recipeId, ingredientId).block());
        model.addAttribute("uomList",  unitOfMeasureReactiveRepository.findAll().collectList().block());
        return "recipe/ingredient/ingredient_form";
    }

    @PostMapping({"", "/"})
    public String saveOrUpdateIngredient(@PathVariable String recipeId, @ModelAttribute Ingredient ingredient) {
        boolean isNewIngredient = "".equals(ingredient.getId());
        if(isNewIngredient) ingredient.setId(null);
        Recipe recipe = recipeService.findById(recipeId).block();
        assert recipe != null;

        ingredient.setRecipe(recipe);
        Ingredient savedIngredientCommand = ingredientReactiveRepository.save(ingredient).block();

        if(isNewIngredient) {
            recipe.getIngredients().add(savedIngredientCommand);
            recipeService.save(recipe).block();
        }

        assert savedIngredientCommand != null;
        log.debug("saved recipe id: " + savedIngredientCommand.getRecipe().getId());
        log.debug("saved ingredient id: " + savedIngredientCommand.getId());

        return "redirect:/recipe/" + savedIngredientCommand.getRecipe().getId() + "/ingredient/" + savedIngredientCommand.getId() + "/show";
    }

    @GetMapping({"{ingredientId}/delete"})
    public String deleteIngredient(@PathVariable String recipeId, @PathVariable String ingredientId) {
        recipeService.deleteIngredientByRecipeIdAndIngredientId(recipeId, ingredientId).block();
        return "redirect:/recipe/" + recipeId + "/ingredient";
    }
}
