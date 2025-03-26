package de.thowl.prog3.exam.service;

import de.thowl.prog3.exam.storage.entities.Note;
import de.thowl.prog3.exam.storage.entities.User;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * Interface for managing and retrieving Notes
 */
public interface NoteService {
    /**
     * Retrieves a single Note by its Id
     *
     * @param id Id of a Note
     */
    public Note getNote(long id);

    public Note getNote(String title);

    public List<Note> getAllNotes();

    List<Note> getNoteByUserId(Long userId);

    int countNotesByUser(User user);

    /**
     * Searches for Notes based on User ID, keyword, and Category ID
     *
     * @param userId     Id of a User
     * @param keyword    String of searched characters in Notesearch
     * @param categoryId Id of a Category
     */
    List<Note> searchNotes(Long userId, String keyword, Long categoryId);

    List<Note> searchNotesSorted(Long userId, String keyword, Long categoryId, Sort sort);

}
