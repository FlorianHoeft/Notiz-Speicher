package de.thowl.prog3.exam.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.thowl.prog3.exam.storage.entities.Note;
import de.thowl.prog3.exam.storage.repositories.NoteRepository;
import de.thowl.prog3.exam.storage.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.thowl.prog3.exam.service.NoteService;
import de.thowl.prog3.exam.storage.entities.Note;
import de.thowl.prog3.exam.storage.repositories.NoteRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteRepository repository;

    @Override
    public Note getNote(long id) {
        log.debug("entering getNote(id={})", id);
        Optional<Note> result = this.repository.findNoteById(id);
        return result.orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public Note getNote(String name) {
        log.debug("entering getNote(title={})", name);
        Optional<Note> result = this.repository.findNoteByTitle(name);
        return result.orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public List<Note> getAllNotes() {
        log.debug("entering getAllNotes()");
        ArrayList<Note> result = new ArrayList<>();
        for (Note n : this.repository.findAll()) {
            result.add(n);
        }
        return result;
    }

    public List<Note> getNoteByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

}
