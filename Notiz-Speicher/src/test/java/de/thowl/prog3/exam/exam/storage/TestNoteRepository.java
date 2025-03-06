package de.thowl.prog3.exam.exam.storage;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import de.thowl.prog3.exam.storage.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import de.thowl.prog3.exam.storage.entities.Note;
import de.thowl.prog3.exam.storage.repositories.NoteRepository;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@DataJpaTest
public class TestNoteRepository {

    private static final int noteid = 1;

    @Autowired
    private NoteRepository repository;

    @Test
    public void testGetNoteByID() {
        log.info("Starting testGetNoteByID");

        Optional<Note> n = this.repository.findNoteById(noteid);
        assertTrue(n.isPresent(), "Unexpected empty result");
        log.debug("Got user {}", n.get());
        assertTrue(n.get().getTitle().equals("Erste_Notiz"), "Username is wrong");
    }

    @Test
    public void testGetNoteByTitle() {
        log.info("Starting testGetUserByName");

        Optional<Note> n = this.repository.findNoteByTitle("Erste_Notiz");
        assertTrue(n.isPresent(), "Unexpected empty result");
        log.debug("Got user {}", n.get());
        assertTrue(n.get().getTitle().equals("Erste_Notiz"), "Title is wrong");
        assertTrue(n.get().getId() == noteid, "noteid has wrong ID");
    }
}
