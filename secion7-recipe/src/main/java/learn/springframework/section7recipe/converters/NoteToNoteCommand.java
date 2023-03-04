package learn.springframework.section7recipe.converters;

import learn.springframework.section7recipe.commands.NoteCommand;
import learn.springframework.section7recipe.domain.Note;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NoteToNoteCommand implements Converter<Note, NoteCommand> {
    @Synchronized
    @Nullable
    @Override
    public NoteCommand convert(Note source) {
        if(source == null) return null;

        NoteCommand command = new NoteCommand();
        command.setId(source.getId());
        command.setNotes(source.getNotes());
        return command;
    }
}
