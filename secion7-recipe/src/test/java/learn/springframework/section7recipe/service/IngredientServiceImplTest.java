package learn.springframework.section7recipe.service;

import learn.springframework.section7recipe.commands.IngredientCommand;
import learn.springframework.section7recipe.converters.IngredientCommandToIngredient;
import learn.springframework.section7recipe.converters.IngredientToIngredientCommand;
import learn.springframework.section7recipe.converters.UnitOfMeasureCommandToUnitOfMeasure;
import learn.springframework.section7recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import learn.springframework.section7recipe.domain.Ingredient;
import learn.springframework.section7recipe.domain.Recipe;
import learn.springframework.section7recipe.repositories.RecipeRepository;
import learn.springframework.section7recipe.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class IngredientServiceImplTest {

    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    IngredientService ingredientService;

    IngredientServiceImplTest() {
        this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    }


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ingredientService = new IngredientServiceImpl(ingredientToIngredientCommand, ingredientCommandToIngredient,
                recipeRepository, unitOfMeasureRepository);
    }

    @Test
    void findByRecipeIdAndIngredientId() {
        // given
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(11L);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(12L);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(13L);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        // when
        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(1L, 12L);

        // then
        assertEquals(12L, ingredientCommand.getId());
        assertEquals(1L, ingredientCommand.getRecipeId());
        verify(recipeRepository, times(1)).findById(anyLong());
    }

    @Test
    void saveIngredient() {
        // given
        IngredientCommand command = new IngredientCommand();
        command.setId(3L);
        command.setRecipeId(2L);

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId(3L);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        // when
        IngredientCommand savedIngredientCommand = ingredientService.saveIngredient(command);

        // then
        assertEquals(savedIngredientCommand.getId(), 3L);
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    void deleteById() {
        Long recipeId = 1L;
        Long ingredientId = 12L;

        // given
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientId);
        Recipe recipe = new Recipe();
        recipe.setId(recipeId);
        recipe.addIngredient(ingredient);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        // when
        ingredientService.deleteById(recipeId, ingredientId);

        // then
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class)); // ingredient is removed and not self deleted
    }

    @Test
    void deleteByIdButRecipeNotFound() {
        Long recipeId = 1L;
        Long ingredientId = 12L;

        // given
        Optional<Recipe> recipeOptional = Optional.empty();
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        // when
        ingredientService.deleteById(recipeId, ingredientId);

        // then
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(0)).save(any(Recipe.class)); // should not be called
    }

    @Test
    void deleteByIdButIngredientNotFound() {
        Long recipeId = 1L;
        Long ingredientId = 12L;

        // given
        Recipe recipe = new Recipe();
        recipe.setId(recipeId);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        // when
        ingredientService.deleteById(recipeId, ingredientId);

        // then
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(0)).save(any(Recipe.class)); // ingredient is removed and not self deleted
    }
}