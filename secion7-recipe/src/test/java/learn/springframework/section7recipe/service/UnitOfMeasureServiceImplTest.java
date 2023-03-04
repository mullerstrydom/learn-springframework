package learn.springframework.section7recipe.service;

import learn.springframework.section7recipe.commands.UnitOfMeasureCommand;
import learn.springframework.section7recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import learn.springframework.section7recipe.domain.UnitOfMeasure;
import learn.springframework.section7recipe.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UnitOfMeasureServiceImplTest {

    @Mock
    private UnitOfMeasureRepository unitOfMeasureRepository;

    UnitOfMeasureService unitOfMeasureService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureRepository,
                new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @Test
    void listAllUoms() {
        // given
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setId(1L);
        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setId(2L);
        UnitOfMeasure uom3 = new UnitOfMeasure();
        uom3.setId(3L);

        Set<UnitOfMeasure> data = new HashSet<>();
        data.add(uom1);
        data.add(uom2);
        data.add(uom3);

        when(unitOfMeasureRepository.findAll()).thenReturn(data);

        // when
        Set<UnitOfMeasureCommand> result = this.unitOfMeasureService.listAllUoms();

        //then
        assertEquals(result.size(), 3);
        assertNotNull(result.stream().filter(uom -> uom.getId().equals(1L)).findFirst());
        assertNotNull(result.stream().filter(uom -> uom.getId().equals(2L)).findFirst());
        assertNotNull(result.stream().filter(uom -> uom.getId().equals(3L)).findFirst());
        verify(this.unitOfMeasureRepository, times(1)).findAll();
    }
}