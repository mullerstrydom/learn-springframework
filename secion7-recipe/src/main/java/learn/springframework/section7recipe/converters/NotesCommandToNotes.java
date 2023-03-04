package learn.springframework.section7recipe.converters;

import learn.springframework.section7recipe.commands.NoteCommand;
import learn.springframework.section7recipe.domain.Note;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesCommandToNotes implements Converter<NoteCommand, Note> {

    @Synchronized
    @Nullable
    @Override
    public Note convert(NoteCommand source) {
        if (source == null) return null;
        final Note note = new Note();
        note.setId(source.getId());
        note.setNotes(source.getNotes());
        return note;
    }
}
