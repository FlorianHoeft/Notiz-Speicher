package de.thowl.prog3.exam.web.api;

import de.thowl.prog3.exam.service.NoteService;
import de.thowl.prog3.exam.storage.entities.Note;
import de.thowl.prog3.exam.web.mapper.NoteMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Rest controller that handles HTTP requests related to Notes
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/notes")// Base URL path for all user-related endpoints
public class NoteController {

    @Autowired
    @Qualifier("notemapper")
    private NoteMapper mapper = new NoteMapper();// Converts Note entities to DTOs

    @Autowired
    private NoteService service;

    public NoteController() {
        log.debug("entering ctor");
    }

    /**
     * Handles GET requests to /api/v1/notes/
     * Retrieves a list of all Notes and returns them as DTOs
     *
     * @return A list of User DTOs
     */
    @GetMapping("/")
    public List<de.thowl.prog3.exam.web.dto.Note> getNotes() {
        log.debug("entering getNotes");
        List<de.thowl.prog3.exam.web.dto.Note> result = new ArrayList<>();
        List<Note> notes = this.service.getAllNotes();
        for (Note n : notes) {
            result.add(this.mapper.map(n));
        }
        return result;
    }

    /**
     * Handles GET requests to /api/v1/notes/{id}
     * Retrieves a single Note by their ID
     *
     * @param id The ID of the Notes to retrieve
     * @return The Notes DTOs corresponding to the Note
     */
    @GetMapping("/{id}")
    public de.thowl.prog3.exam.web.dto.Note getNotebyId(@PathVariable Long id) {
        log.debug("entering getNoteById, id={}", id);
        Note n = this.service.getNote(id);
        return this.mapper.map(n);
    }

    /**
     * Handles GET requests to /api/v1/notes/{id}
     * Retrieves a List of Notes corresponding to a User
     *
     * @param userId The ID of the User to retrieve
     * @return The Note DTOs corresponding to the User
     */
    @GetMapping("/user/{userId}")
    public List<Note> getNoteByUserId(@PathVariable Long userId) {
        return service.getNoteByUserId(userId);
    }
}
