package learn.springframework.section19recipemongodb.domain.documents;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Getter
@Setter
@Document(collection = "ingredients")
public class Ingredient {

    public Ingredient(){};
    @Builder
    public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom) {
        this.description = description;
        this.amount = amount;
        this.uom = uom;
    }

    @Id
    private String id;
    private String description;
    private BigDecimal amount;

    @DBRef
    private UnitOfMeasure uom;
    @DBRef
    private Recipe recipe;
}
