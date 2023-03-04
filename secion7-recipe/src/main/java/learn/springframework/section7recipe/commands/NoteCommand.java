package learn.springframework.section7recipe.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NoteCommand {

    private Long id;
    private String notes;

}
