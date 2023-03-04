package learn.springframework.section7recipe.controllers;

import learn.springframework.section7recipe.commands.IngredientCommand;
import learn.springframework.section7recipe.commands.RecipeCommand;
import learn.springframework.section7recipe.commands.UnitOfMeasureCommand;
import learn.springframework.section7recipe.service.IngredientService;
import learn.springframework.section7recipe.service.RecipeService;
import learn.springframework.section7recipe.service.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/recipe/{recipeId}/ingredient")
@Controller
public class IngredientController {

    private final IngredientService ingredientService;
    private final RecipeService recipeService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(IngredientService ingredientService, RecipeService recipeService, UnitOfMeasureService unitOfMeasureService) {
        this.ingredientService = ingredientService;
        this.recipeService = recipeService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping({"", "/"})
    public String listIngredients(@PathVariable String recipeId, Model model) {
        log.debug("Getting ingredient list for recipe id: " + recipeId);

        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(recipeId)));
        return "recipe/ingredient/list";
    }

    @GetMapping({"/{ingredientId}/show"})
    public String showExistingIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model){
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(
                Long.valueOf(recipeId), Long.valueOf(ingredientId)));
        return "recipe/ingredient/show";
    }


    @GetMapping("/new")
    public String getNewIngredientForm(@PathVariable String recipeId, Model model){

        //make sure we have a good id value
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));
        //todo raise exception if null

        //need to return back parent id for hidden form property
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(Long.valueOf(recipeId));
        model.addAttribute("ingredient", ingredientCommand);

        //init uom
        ingredientCommand.setUom(new UnitOfMeasureCommand());

        model.addAttribute("uomList",  unitOfMeasureService.listAllUoms());

        return "recipe/ingredient/ingredient_form";
    }

    @GetMapping({"/{ingredientId}/update", "/{ingredientId}/update/"})
    public String getUpdateIngredientForm(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {

        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(
                Long.valueOf(recipeId), Long.valueOf(ingredientId)));
        model.addAttribute("uomList",  unitOfMeasureService.listAllUoms());

        return "recipe/ingredient/ingredient_form";
    }

    @PostMapping({"", "/"})
    public String saveOrUpdateIngredient(@ModelAttribute IngredientCommand ingredientCommand) {
        IngredientCommand savedIngredientCommand = ingredientService.saveIngredient(ingredientCommand);

        log.debug("saved recipe id: " + savedIngredientCommand.getRecipeId());
        log.debug("saved ingredient id: " + savedIngredientCommand.getId());

        return "redirect:/recipe/" + savedIngredientCommand.getRecipeId() + "/ingredient/" + savedIngredientCommand.getId() + "/show";
    }

    @GetMapping({"{ingredientId}/delete"})
    public String deleteIngredient(@PathVariable String recipeId, @PathVariable String ingredientId) {
        ingredientService.deleteById(Long.valueOf(recipeId), Long.valueOf(ingredientId));
        return "redirect:/recipe/" + recipeId + "/ingredient/";
    }
}
