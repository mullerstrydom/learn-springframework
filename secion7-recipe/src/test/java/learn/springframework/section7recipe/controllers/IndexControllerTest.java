package learn.springframework.section7recipe.controllers;

import learn.springframework.section7recipe.converters.RecipeCommandToRecipe;
import learn.springframework.section7recipe.converters.RecipeToRecipeCommand;
import learn.springframework.section7recipe.domain.Recipe;
import learn.springframework.section7recipe.repositories.RecipeRepository;
import learn.springframework.section7recipe.service.RecipeService;
import learn.springframework.section7recipe.service.RecipeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class IndexControllerTest {

    IndexController indexController;
    RecipeService recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    Model uiModel;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.recipeService = new RecipeServiceImpl(this.recipeRepository, null, null);
        this.indexController = new IndexController(this.recipeService);
    }

    @Test
    void getIndexPage() {
        // setup fake data
        Recipe recipe = new Recipe();
        HashSet<Recipe> recipesData = new HashSet<>();
        recipesData.add(recipe);
        Mockito.when(recipeRepository.findAll()).thenReturn(recipesData);

        String result = this.indexController.getIndexPage(this.uiModel);
        assertEquals(result, "index");
//        assertTrue(this.uiModel.containsAttribute("recipes"));
        Mockito.verify(recipeRepository, Mockito.times(1)).findAll();
    }
}


class IndexController2Test { // what the guys in the class did

    IndexController indexController;
    @Mock
    RecipeService recipeService;

    @Mock
    Model uiModel;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.indexController = new IndexController(this.recipeService);
    }

    @Test
    void getIndexPage() {
        // given
        HashSet<Recipe> recipesData = new HashSet<>();

        Recipe recipe1 = new Recipe();
        recipe1.setId(1L);
        Recipe recipe2 = new Recipe();
        recipe2.setId(2L);
        recipesData.add(recipe1);
        recipesData.add(recipe2);

        Mockito.when(recipeService.getRecipes()).thenReturn(recipesData);
        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        // when
        String result = this.indexController.getIndexPage(this.uiModel);

        // then
        assertEquals(result, "index");
        Mockito.verify(recipeService, Mockito.times(1)).getRecipes();
        Mockito.verify(uiModel, Mockito.times(1)).addAttribute(Mockito.eq("recipes"), argumentCaptor.capture());

        Set<Recipe> setInController = argumentCaptor.getValue();
        assertEquals(2, setInController.size());
    }

    @Test
    public void testMockMVC() throws Exception {
        // mock request - very fast instead of loading the SpringFramework for each unit test
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }
}