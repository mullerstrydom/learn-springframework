package learn.springframework.section7recipe.repositories;

import learn.springframework.section7recipe.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UnitOfMeasureRepositoryTestIT {  // the *IT stands for integration test

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @BeforeEach
    public void setup() {
    }

    @Test
    @DirtiesContext
    void findByDescription() {
        Optional<UnitOfMeasure> optionalUnitOfMeasure = this.unitOfMeasureRepository.findByDescription("Teaspoon");
        assertEquals("Teaspoon", optionalUnitOfMeasure.get().getDescription());
    }

    @Test
    void findByDescriptionCup() {
        Optional<UnitOfMeasure> optionalUnitOfMeasure = this.unitOfMeasureRepository.findByDescription("Cup");
        assertEquals("Cup", optionalUnitOfMeasure.get().getDescription());
    }
}