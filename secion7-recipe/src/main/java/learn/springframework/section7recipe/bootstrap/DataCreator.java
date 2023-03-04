package learn.springframework.section7recipe.bootstrap;

import jakarta.transaction.Transactional;
import learn.springframework.section7recipe.domain.*;
import learn.springframework.section7recipe.repositories.CategoryRepository;
import learn.springframework.section7recipe.repositories.RecipeRepository;
import learn.springframework.section7recipe.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Slf4j
@Component
public class DataCreator implements CommandLineRunner {


    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    private final RecipeRepository recipeRepository;

    public DataCreator(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository, RecipeRepository recipeRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        log.debug("Bootstrapping application using DataCreator");

//        createCategories();
//        createUnitOfMeasure();
        this.recipeRepository.saveAll(getRecipes());

        log.debug(" - Bootstrapping Done");
    }

    private List<Recipe> getRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        Optional<UnitOfMeasure> eachUomOptional = this.unitOfMeasureRepository.findByDescription("Each");
        if(eachUomOptional.isEmpty()) throw new RuntimeException("Expected UoM not found.");
        Optional<UnitOfMeasure> tablespoonUomOptional = this.unitOfMeasureRepository.findByDescription("Tablespoon");
        if(tablespoonUomOptional.isEmpty()) throw new RuntimeException("Expected UoM not found.");
        Optional<UnitOfMeasure> teaspoonUomOptional = this.unitOfMeasureRepository.findByDescription("Teaspoon");
        if(teaspoonUomOptional.isEmpty()) throw new RuntimeException("Expected UoM not found.");
        Optional<UnitOfMeasure> dashUomOptional = this.unitOfMeasureRepository.findByDescription("Dash");
        if(dashUomOptional.isEmpty()) throw new RuntimeException("Expected UoM not found.");
        Optional<UnitOfMeasure> pintUomOptional = this.unitOfMeasureRepository.findByDescription("Pint");
        if(pintUomOptional.isEmpty()) throw new RuntimeException("Expected UoM not found.");
        Optional<UnitOfMeasure> cupUomOptional = this.unitOfMeasureRepository.findByDescription("Cup");
        if(cupUomOptional.isEmpty()) throw new RuntimeException("Expected UoM not found.");

        UnitOfMeasure eachUom = eachUomOptional.get();
        UnitOfMeasure tablespoonUom = tablespoonUomOptional.get();
        UnitOfMeasure teaspoonUom = teaspoonUomOptional.get();
        UnitOfMeasure dashUom = dashUomOptional.get();
        UnitOfMeasure pintUom = pintUomOptional.get();
        UnitOfMeasure cupUom = cupUomOptional.get();


        Optional<Category> americanCategoryOptional = this.categoryRepository.findByDescription("American");
        if(americanCategoryOptional.isEmpty()) throw new RuntimeException("Expected Category not found");

        Optional<Category> mexicanCategoryOptional = this.categoryRepository.findByDescription("Mexican");
        if(mexicanCategoryOptional.isEmpty()) throw new RuntimeException("Expected Category not found");

        Category americanCategory = americanCategoryOptional.get();
        Category mexicanCategory = mexicanCategoryOptional.get();

        Recipe guacRecipe = new Recipe();
        guacRecipe.setDescription("Perfect Guacamole");
        guacRecipe.setPrepTime(10);
        guacRecipe.setCookTime(0);
        guacRecipe.setDifficulty(Difficulty.EASY);
        guacRecipe.setDirections("1 Cut the avocado:\n" +
                "Cut the avocados in half. Remove the pit. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl.\n" +
                "\n" +
                "2 How to make guacamole - scoring avocado\n" +
                "Mash the avocado flesh:\n" +
                "Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)\n" +
                "\n" +
                "3 How to make guacamole - smashing avocado with fork\n" +
                "Add the remaining ingredients to taste:\n" +
                "Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "\n" +
                "Add the chopped onion, cilantro, black pepper, and chilis. Chili peppers vary individually in their spiciness. So, start with a half of one chili pepper and add more to the guacamole to your desired degree of heat.\n" +
                "\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                "\n" +
                "4 Serve immediately:\n" +
                "If making a few hours ahead, place plastic wrap on the surface of the guacamole and press down to cover it to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.)\n" +
                "\n" +
                "Garnish with slices of red radish or jigama strips. Serve with your choice of store-bought tortilla chips or make your own homemade tortilla chips.\n" +
                "\n" +
                "Refrigerate leftover guacamole up to 3 days.\n" +
                "\n" +
                "Note: Chilling tomatoes hurts their flavor. So, if you want to add chopped tomato to your guacamole, add it just before serving.");
        Note note = new Note();
        note.setNotes("How To Store Guacamole\n" +
                "Guacamole is best eaten right after it's made. Like apples, avocados start to oxidize and turn brown once they've been cut. That said, the acid in the lime juice you add to guacamole can help slow down that process. And if you store the guacamole properly, you can easily make it a few hours ahead if you are preparing for a party.\n" +
                "\n" +
                "The trick to keeping guacamole green is to make sure air doesn't touch it! Transfer it to a container, cover with plastic wrap, and press down on the plastic wrap to squeeze out any air pockets. Make sure any exposed surface of the guacamole is touching the plastic wrap, not air. This will keep the amount of browning to a minimum.\n" +
                "\n" +
                "You can store the guacamole in the fridge this way for up to three days. If the guacamole develops discoloration, you can either scrape off the brown parts and discard, or stir into the rest of the guacamole before serving.\n" +
                "\n");
        guacRecipe.setNotes(note);

        guacRecipe.addIngredient(new Ingredient("ripe avocados", new BigDecimal(2), eachUom));
        guacRecipe.addIngredient(new Ingredient("kosher salt", new BigDecimal("0.25"), teaspoonUom));
        guacRecipe.addIngredient(new Ingredient("fresh lime or lemon juice", new BigDecimal(1), tablespoonUom));
        guacRecipe.addIngredient(new Ingredient("minced red onion or thinly sliced green onion", new BigDecimal(3), tablespoonUom));
        guacRecipe.addIngredient(new Ingredient("serrano (or jalapeno) chilis, stems and seeds removed, minced", new BigDecimal(2), eachUom));
        guacRecipe.addIngredient(new Ingredient("cilantro (leaves and tender stems), finely chopped", new BigDecimal(2), tablespoonUom));
        guacRecipe.addIngredient(new Ingredient("freshly ground black pepper", new BigDecimal(1), pintUom));
        guacRecipe.addIngredient(new Ingredient("ripe tomato, chopped (optional)", new BigDecimal("0.5"), eachUom));
        guacRecipe.addIngredient(new Ingredient("Red radish or jicama slices for garnish", new BigDecimal(1), eachUom));
        guacRecipe.addIngredient(new Ingredient("Tortilla chips", new BigDecimal(1), eachUom));

        guacRecipe.getCategories().add(americanCategory);
        guacRecipe.getCategories().add(mexicanCategory);

        recipes.add(guacRecipe);
        return recipes;
    }

    private void createUnitOfMeasure() {
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setDescription("Teaspoon");
        this.unitOfMeasureRepository.save(uom1);

        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setDescription("Tablespoon");
        this.unitOfMeasureRepository.save(uom2);

        UnitOfMeasure uom3 = new UnitOfMeasure();
        uom3.setDescription("Cup");
        this.unitOfMeasureRepository.save(uom3);

        UnitOfMeasure uom4 = new UnitOfMeasure();
        uom4.setDescription("Pinch");
        this.unitOfMeasureRepository.save(uom4);

        UnitOfMeasure uom5 = new UnitOfMeasure();
        uom5.setDescription("Ounce");
        this.unitOfMeasureRepository.save(uom5);

        UnitOfMeasure uom6 = new UnitOfMeasure();
        uom6.setDescription("Each");
        this.unitOfMeasureRepository.save(uom6);

        UnitOfMeasure uom7 = new UnitOfMeasure();
        uom7.setDescription("Pint");
        this.unitOfMeasureRepository.save(uom7);

        UnitOfMeasure uom8 = new UnitOfMeasure();
        uom8.setDescription("Dash");
        this.unitOfMeasureRepository.save(uom8);

        System.out.println("Number of unitOfMeasure: " + StreamSupport.stream(this.unitOfMeasureRepository.findAll().spliterator()
                , false).count());
    }

    private void createCategories() {
        Category category1 = new Category();
        category1.setDescription("American");
        this.categoryRepository.save(category1);

        Category category2 = new Category();
        category2.setDescription("Italian");
        this.categoryRepository.save(category2);

        Category category3 = new Category();
        category3.setDescription("Mexican");
        this.categoryRepository.save(category3);

        Category category4 = new Category();
        category4.setDescription("Fastfood");
        this.categoryRepository.save(category4);
        System.out.println("Number of categories: " + StreamSupport.stream(this.categoryRepository.findAll().spliterator()
                , false).count());
    }
}
