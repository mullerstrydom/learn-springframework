package learn.springframework.section19recipemongodb.bootstrap;

import learn.springframework.section19recipemongodb.domain.documents.*;
import learn.springframework.section19recipemongodb.repository.reactive.*;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryReactiveRepository categoryReactiveRepository;
    private final RecipeReactiveRepository recipeReactiveRepository;
    private final UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;
    private final IngredientReactiveRepository ingredientReactiveRepository;
    private final NoteReactiveRepository noteReactiveRepository;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("+++++++++++++++++++ ApplicationEvent +++++++++++++++++++");
        System.out.println("--ApplicationEvent: " + event.toString());
        loadCategories();
        loadUom();
        loadRecipes();

        System.out.println("Number of categories: " + categoryReactiveRepository.count().block());
        System.out.println("Number of UOM: " + unitOfMeasureReactiveRepository.count().block());
        System.out.println("Number of recipes: " + recipeReactiveRepository.count().block());

    }

    private void loadRecipes() {
        Mono<Recipe> recipeMono = recipeReactiveRepository.findByDescription("Perfect Guacamole");
        if(recipeMono.blockOptional().isEmpty()) {
            UnitOfMeasure eachUom = this.unitOfMeasureReactiveRepository.findByDescription("Each").block();
            UnitOfMeasure tablespoonUom = this.unitOfMeasureReactiveRepository.findByDescription("Tablespoon").block();
            UnitOfMeasure teaspoonUom = this.unitOfMeasureReactiveRepository.findByDescription("Teaspoon").block();
            UnitOfMeasure pintUom = this.unitOfMeasureReactiveRepository.findByDescription("Pint").block();

            Category americanCategory = this.categoryReactiveRepository.findByDescription("American").block();
            Category mexicanCategory = this.categoryReactiveRepository.findByDescription("Mexican").block();

            Recipe guacRecipe = new Recipe();
            guacRecipe.setDescription("Perfect Guacamole");
            guacRecipe.setPrepTime(10);
            guacRecipe.setCookTime(0);
            guacRecipe.setDifficulty(Difficulty.EASY);
            guacRecipe.setDirections("""
                    1 Cut the avocado:
                    Cut the avocados in half. Remove the pit. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl.

                    2 How to make guacamole - scoring avocado
                    Mash the avocado flesh:
                    Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)

                    3 How to make guacamole - smashing avocado with fork
                    Add the remaining ingredients to taste:
                    Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.

                    Add the chopped onion, cilantro, black pepper, and chilis. Chili peppers vary individually in their spiciness. So, start with a half of one chili pepper and add more to the guacamole to your desired degree of heat.

                    Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.

                    4 Serve immediately:
                    If making a few hours ahead, place plastic wrap on the surface of the guacamole and press down to cover it to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.)

                    Garnish with slices of red radish or jigama strips. Serve with your choice of store-bought tortilla chips or make your own homemade tortilla chips.

                    Refrigerate leftover guacamole up to 3 days.

                    Note: Chilling tomatoes hurts their flavor. So, if you want to add chopped tomato to your guacamole, add it just before serving.""");
            Note note = new Note();
            note.setNote("""
                    How To Store Guacamole
                    Guacamole is best eaten right after it's made. Like apples, avocados start to oxidize and turn brown once they've been cut. That said, the acid in the lime juice you add to guacamole can help slow down that process. And if you store the guacamole properly, you can easily make it a few hours ahead if you are preparing for a party.

                    The trick to keeping guacamole green is to make sure air doesn't touch it! Transfer it to a container, cover with plastic wrap, and press down on the plastic wrap to squeeze out any air pockets. Make sure any exposed surface of the guacamole is touching the plastic wrap, not air. This will keep the amount of browning to a minimum.

                    You can store the guacamole in the fridge this way for up to three days. If the guacamole develops discoloration, you can either scrape off the brown parts and discard, or stir into the rest of the guacamole before serving.

                    """);
            Note savedNote = noteReactiveRepository.save(note).block();
            guacRecipe.setNote(savedNote);

            List<Ingredient> ingredientList = new ArrayList<>();
            ingredientList.add(new Ingredient("ripe avocados", new BigDecimal(2), eachUom));
            ingredientList.add(new Ingredient("kosher salt", new BigDecimal("0.25"), teaspoonUom));
            ingredientList.add(new Ingredient("fresh lime or lemon juice", new BigDecimal(1), tablespoonUom));
            ingredientList.add(new Ingredient("minced red onion or thinly sliced green onion", new BigDecimal(3), tablespoonUom));
            ingredientList.add(new Ingredient("serrano (or jalapeno) chilis, stems and seeds removed, minced", new BigDecimal(2), eachUom));
            ingredientList.add(new Ingredient("cilantro (leaves and tender stems), finely chopped", new BigDecimal(2), tablespoonUom));
            ingredientList.add(new Ingredient("freshly ground black pepper", new BigDecimal(1), pintUom));
            ingredientList.add(new Ingredient("ripe tomato, chopped (optional)", new BigDecimal("0.5"), eachUom));
            ingredientList.add(new Ingredient("Red radish or jicama slices for garnish", new BigDecimal(1), eachUom));
            ingredientList.add(new Ingredient("Tortilla chips", new BigDecimal(1), eachUom));
            List<Ingredient> savedIngredients = ingredientList.stream().map(ingredient -> ingredientReactiveRepository.save(ingredient).block()).toList();
            guacRecipe.getIngredients().addAll(savedIngredients);

            guacRecipe.getCategories().add(americanCategory);
            guacRecipe.getCategories().add(mexicanCategory);
            final Recipe savedGuacRecipe = recipeReactiveRepository.save(guacRecipe).block();

            savedIngredients.forEach(ingredient -> {
                ingredient.setRecipe(savedGuacRecipe);
                ingredientReactiveRepository.save(ingredient).block();
            });

            assert savedNote != null;
            savedNote.setRecipe(savedGuacRecipe);
            noteReactiveRepository.save(savedNote).block();

            System.out.println("Created 1 recipe");
        }
    }

    private void loadUom() {
        boolean isEmpty = unitOfMeasureReactiveRepository.isEmpty().blockOptional().orElse(false);
        if(isEmpty){
            unitOfMeasureReactiveRepository.save(UnitOfMeasure.builder().description("Teaspoon").build()).block();
            unitOfMeasureReactiveRepository.save(UnitOfMeasure.builder().description("Tablespoon").build()).block();
            unitOfMeasureReactiveRepository.save(UnitOfMeasure.builder().description("Cup").build()).block();
            unitOfMeasureReactiveRepository.save(UnitOfMeasure.builder().description("Dash").build()).block();
            unitOfMeasureReactiveRepository.save(UnitOfMeasure.builder().description("Pint").build()).block();
            unitOfMeasureReactiveRepository.save(UnitOfMeasure.builder().description("Each").build()).block();
            unitOfMeasureReactiveRepository.save(UnitOfMeasure.builder().description("Ounce").build()).block();
            System.out.println("Created 7 unit of measures");
        }
    }

    private void loadCategories() {
        boolean isEmpty = categoryReactiveRepository.isEmpty().blockOptional().orElse(false);
        if (isEmpty) {
            categoryReactiveRepository.save(Category.builder().description("American").build()).block();
            categoryReactiveRepository.save(Category.builder().description("Italian").build()).block();
            categoryReactiveRepository.save(Category.builder().description("Mexican").build()).block();
            categoryReactiveRepository.save(Category.builder().description("Fast food").build()).block();
            System.out.println("Created 4 categories");
        }
    }
}
