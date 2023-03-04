package learn.springframework.section7recipe.controllers;

import learn.springframework.section7recipe.domain.Category;
import learn.springframework.section7recipe.domain.Recipe;
import learn.springframework.section7recipe.domain.UnitOfMeasure;
import learn.springframework.section7recipe.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;
import java.util.Set;

@Slf4j
@Controller
public class IndexController {

    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    @GetMapping({"/", "", "/index.html"})
    public String getIndexPage(Model model) {
        log.debug("Accessing: getIndexPage()");
        Set<Recipe> recipes = this.recipeService.getRecipes();
        model.addAttribute("recipes", recipes);
        return "index";
    }
}
