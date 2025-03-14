package de.thowl.prog3.exam.exam.storage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Optional;

import de.thowl.prog3.exam.service.NoteService;
import de.thowl.prog3.exam.storage.entities.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;
import de.thowl.prog3.exam.storage.entities.Note;
import de.thowl.prog3.exam.storage.repositories.NoteRepository;
import lombok.extern.slf4j.Slf4j;



@Slf4j
@SpringBootTest
public class TestNoteRepository {

    private static final int noteid = 1;

    @Autowired
    private NoteRepository repository;

    @Autowired
    private NoteService service;

    @Test
    public void testGetNoteByID() {
        log.info("Starting testGetNoteByID");

        Optional<Note> n = this.repository.findNoteById(2);
        assertTrue(n.isPresent(), "Unexpected empty result");
        log.debug("Got Note {}", n.get());
        assertTrue(n.get().getTitle().equals("Notiz nr1"), "Username is wrong");
    }

    @Test
    public void testGetNoteByTitle() {
        log.info("Starting testGetNoteByName");

        Optional<Note> n = this.repository.findNoteByTitle("Notiz nr1");
        assertTrue(n.isPresent(), "Unexpected empty result");
        log.debug("Got Note {}", n.get());
        assertTrue(n.get().getTitle().equals("Notiz nr1"), "Title is wrong");
        assertTrue(n.get().getId() == 2, "noteid has wrong ID");
    }

    @Test
    public void testGetNoteByContent() {
        log.info("Starting testGetNoteByName");

        Optional<Note> n = this.repository.findNoteByContent("Test notiz1 User 2");
        assertTrue(n.isPresent(), "Unexpected empty result");
        log.debug("Got Note {}", n.get());
        assertTrue(n.get().getTitle().equals("Test notiz1 User 2"), "Title is wrong");
        assertTrue(n.get().getId() == 2, "noteid has wrong ID");
    }


    @Test
    public void testGetNoteByFavorite() {
        log.info("Starting testGetNoteByFavorite");

        List<Note> n = this.repository.findNoteByFavorite(true);
        //assertFalse(n.isEmpty(), "Unexpected empty result");
        for (Note note : n) {
            log.debug("Got Note {}", note);
        }
        System.out.println(n.get(0).getContent());

    }

    @Test
    public void testGetNoteByUserId() {
        log.info("Starting testGetNoteByUserId");
        List<Note> n = service.getNoteByUserId(1L);
        for (Note note : n) {
            log.debug("Got Note {}", n);
        }
    }










}
