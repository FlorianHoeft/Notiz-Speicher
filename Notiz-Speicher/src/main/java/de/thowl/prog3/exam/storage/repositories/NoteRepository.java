package de.thowl.prog3.exam.storage.repositories;

import java.util.Optional;

import de.thowl.prog3.exam.storage.entities.Note;

import de.thowl.prog3.exam.storage.entities.User;
import org.springframework.data.repository.CrudRepository;


public interface NoteRepository extends CrudRepository<Note, Long> {

    public Optional<Note> findNoteById(long id);

    public Optional<Note> findNoteByTitle(String title);

    public Optional<Note> findNoteByContent(String content);

}
