package learn.springframework.section7recipe.service;

import learn.springframework.section7recipe.converters.RecipeCommandToRecipe;
import learn.springframework.section7recipe.converters.RecipeToRecipeCommand;
import learn.springframework.section7recipe.domain.Recipe;
import learn.springframework.section7recipe.exceptions.NotFoundException;
import learn.springframework.section7recipe.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        this.recipeService = new RecipeServiceImpl(recipeRepository, recipeToRecipeCommand, recipeCommandToRecipe);
    }

    @Test
    void getRecipeById() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        Recipe receivedRecipe = recipeService.findById(1L);

        assertNotNull(receivedRecipe);
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }


    @Test
    void getRecipesTest() {
        Recipe recipe = new Recipe();
        HashSet<Recipe> recipesData = new HashSet<>();
        recipesData.add(recipe);
        when(recipeRepository.findAll()).thenReturn(recipesData);

        Set<Recipe> recipes = this.recipeService.getRecipes();
        assertEquals(recipes.size(), 1);
        verify(recipeRepository, times(1)).findAll();
    }


    @Test
    void testDeleteById() throws Exception {
        Long idToDelete = Long.parseLong("1");
        recipeService.deleteById(idToDelete);
        // no when, since method has void return type

        verify(recipeRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void testRecipeByIdNotFound() throws Exception {
        Optional<Recipe> recipeOptional = Optional.empty();
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            Recipe recipe = recipeService.findById(1L);
        }, "NotFoundException was expected");

        assertEquals("Recipe, id=1, not found", exception.getMessage());
    }


}