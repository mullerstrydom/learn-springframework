package learn.springframework.section7recipe.controllers;

import jakarta.validation.Valid;
import learn.springframework.section7recipe.commands.RecipeCommand;
import learn.springframework.section7recipe.exceptions.NotFoundException;
import learn.springframework.section7recipe.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService recipeService;


    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{id}/show")
    public String showById(@PathVariable String id, Model model) {
        model.addAttribute("recipe", this.recipeService.findById(Long.parseLong(id)));
        return "recipe/show";
    }

    @GetMapping("/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());
        return "recipe/recipeform";
    }

    @GetMapping("/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", this.recipeService.findById(Long.parseLong(id)));
        return "recipe/recipeform";
    }

    @GetMapping("/{id}/delete")
    public String deleteRecipe(@PathVariable String id) {
        log.debug("Deleting recipe ID: " + id);
        this.recipeService.deleteById(Long.parseLong(id));
        return "redirect:/";
    }

    @PostMapping({"", "/"})
//    @RequestMapping(value = "", method = RequestMethod.POST)
    public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand recipeCommand, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> {
                log.debug(error.toString());
            });
            return "recipe/recipeform";
        }
        RecipeCommand savedCommand = this.recipeService.saveRecipeCommand(recipeCommand);
        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception ex) {
        log.error("Handler: NotFoundException");
        log.error(ex.getMessage());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404error");
        modelAndView.addObject("exception", ex);

        return modelAndView;
    }
}
