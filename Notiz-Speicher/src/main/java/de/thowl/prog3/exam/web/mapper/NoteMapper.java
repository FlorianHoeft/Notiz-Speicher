package de.thowl.prog3.exam.web.mapper;

import de.thowl.prog3.exam.storage.entities.Note;
import de.thowl.prog3.exam.storage.entities.User;
import org.springframework.stereotype.Component;
@Component("notemapper")
public class NoteMapper {

    public de.thowl.prog3.exam.web.dto.Note map(Note in) {
        return new de.thowl.prog3.exam.web.dto.Note(in.getId(), in.getTitle(), in.getContent());
    }
}
