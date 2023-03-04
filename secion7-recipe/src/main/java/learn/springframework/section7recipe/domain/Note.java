package learn.springframework.section7recipe.domain;

import jakarta.persistence.*;
import lombok.*;

@Data
@EqualsAndHashCode(exclude = {"recipe"})
@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Recipe recipe;
    @Lob
    private String notes;

}
