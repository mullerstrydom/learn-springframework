package learn.springframework.section7recipe.converters;

import learn.springframework.section7recipe.commands.UnitOfMeasureCommand;
import learn.springframework.section7recipe.domain.UnitOfMeasure;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {

    @Override
    public UnitOfMeasureCommand convert(UnitOfMeasure source) {
        if (source == null) return null;

        UnitOfMeasureCommand unitOfMeasure = new UnitOfMeasureCommand();
        unitOfMeasure.setId(source.getId());
        unitOfMeasure.setDescription(source.getDescription());
        return unitOfMeasure;
    }
}
