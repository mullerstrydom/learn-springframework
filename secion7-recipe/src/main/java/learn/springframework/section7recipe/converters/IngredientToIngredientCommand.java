package learn.springframework.section7recipe.converters;

import learn.springframework.section7recipe.commands.IngredientCommand;
import learn.springframework.section7recipe.domain.Ingredient;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

    private final UnitOfMeasureToUnitOfMeasureCommand uomConverter;

    public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Override
    public IngredientCommand convert(Ingredient source) {
        if (source == null) return null;

        IngredientCommand command = new IngredientCommand();
        command.setId(source.getId());
        command.setDescription(source.getDescription());
        command.setAmount(source.getAmount());
        command.setUom(uomConverter.convert(source.getUom()));
        if(source.getRecipe() != null) {
            command.setRecipeId(source.getRecipe().getId());
        }
        return command;
    }
}
