package de.thowl.prog3.exam.service.impl;

import de.thowl.prog3.exam.service.NoteService;
import de.thowl.prog3.exam.storage.entities.Note;
import de.thowl.prog3.exam.storage.entities.User;
import de.thowl.prog3.exam.storage.repositories.NoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the NoteService interface
 * Provides methods to retrieve Notes from the repository
 */
@Slf4j
@Service
public class NoteServiceImpl implements NoteService {

    @Autowired // Automatically injects the NoteRepository bean
    private NoteRepository repository;

    /**
     * Retrieves a Note by its ID
     *
     * @param id The ID of the note
     * @return The Note object if found
     */
    @Override
    public Note getNote(long id) {
        log.debug("entering getNote(id={})", id);
        Optional<Note> result = this.repository.findNoteById(id);
        return result.orElseThrow(IllegalArgumentException::new);
    }

    /**
     * Retrieves a Note by their name
     *
     * @param name The name of the Note
     * @return The Note object if found
     */
    @Override
    public Note getNote(String name) {
        log.debug("entering getNote(title={})", name);
        Optional<Note> result = this.repository.findNoteByTitle(name);
        return result.orElseThrow(IllegalArgumentException::new);
    }

    /**
     * Retrieves a list of all Notes
     *
     * @return A list of all Note objects
     */
    @Override
    public List<Note> getAllNotes() {
        log.debug("entering getAllNotes()");
        ArrayList<Note> result = new ArrayList<>();
        for (Note n : this.repository.findAll()) {
            result.add(n);
        }
        return result;
    }

    /**
     * Retrieves all Notes belonging to a specific User
     *
     * @param userId The ID of the User
     * @return A list of Note objects belonging to a User
     */
    @Override
    public List<Note> getNoteByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    /**
     * Counts the number of Notes created by a specific User
     *
     * @param user The User whose Notes are counted
     * @return The number of Notes associated with a User
     */
    @Override
    public int countNotesByUser(User user) {
        log.debug("entering countNotesByUser(user={})", user);
        return repository.countByUser(user);
    }

    /**
     * Searches for Notes based on User ID, an optional keyword and an optional Category ID
     *
     * @param userId     The ID of the User
     * @param keyword    The keyword to search for
     * @param categoryId The ID of the Category (optional filter)
     * @return A list of Notes matching the criteria
     */
    public List<Note> searchNotes(Long userId, String keyword, Long categoryId) {
        return repository.findByUserIdAndFilters(userId, keyword, categoryId, Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public List<Note> searchNotesSorted(Long userId, String keyword, Long categoryId, Sort sort) {
        return repository.findByUserIdAndFilters(userId, keyword, categoryId, sort);
    }

}
