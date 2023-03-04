package learn.springframework.section19recipemongodb.controllers;

import jakarta.validation.Valid;
import learn.springframework.section19recipemongodb.domain.documents.Recipe;
import learn.springframework.section19recipemongodb.services.RecipeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("recipe")
public class RecipeController {

    private RecipeService recipeService;

    @GetMapping("/{id}/show")
    public String showById(@PathVariable String id, Model model) {
        model.addAttribute("recipe", this.recipeService.findById(id).block());
        return "recipe/show";
    }

    @GetMapping("/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new Recipe());
        return "recipe/recipeform";
    }

    @GetMapping("/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", this.recipeService.findById(id).block());
        return "recipe/recipeform";
    }

    @GetMapping("/{id}/delete")
    public String deleteRecipe(@PathVariable String id) {
        log.debug("Deleting recipe ID: " + id);
        this.recipeService.deleteById(id).block();
        return "redirect:/";
    }

    @PostMapping({"", "/"})
    public String saveOrUpdate(@Valid @ModelAttribute("recipe") Recipe recipe, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> {
                log.debug(error.toString());
            });
            return "recipe/recipeform";
        }
        Mono<Recipe> savedCommand = this.recipeService.save(recipe);
        Optional<Recipe> recipeOptional = savedCommand.blockOptional();
        return recipeOptional.map(value -> "redirect:/recipe/" + value.getId() + "/show").orElse("index");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(Exception.class)
    public ModelAndView handleNotFound(Exception ex) {
        log.error("Handler: NotFoundException");
        log.error(ex.getMessage());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404error");
        modelAndView.addObject("exception", ex);

        return modelAndView;
    }
}
