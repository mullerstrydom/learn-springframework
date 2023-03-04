package learn.springframework.section19recipemongodb.controllers;

import learn.springframework.section19recipemongodb.domain.documents.Recipe;
import learn.springframework.section19recipemongodb.services.RecipeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;

@Slf4j
@Controller
@AllArgsConstructor
public class IndexController {

    private RecipeService recipeService;

    @GetMapping("/")
    public String getIndexPage(Model model) {
        log.debug("Accessing: index");
        Flux<Recipe> recipeFlux = recipeService.findAll();
        model.addAttribute("recipes", recipeFlux.collectList().block());
        return "index";
    }
}
