package de.thowl.prog3.exam.web.gui;

import de.thowl.prog3.exam.service.CategoryService;
import de.thowl.prog3.exam.storage.entities.*;
import de.thowl.prog3.exam.storage.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import de.thowl.prog3.exam.service.NoteService;
import de.thowl.prog3.exam.web.mapper.NoteMapper;
import de.thowl.prog3.exam.service.AuthService;
import de.thowl.prog3.exam.storage.entities.Note;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

/**
 * Controller for handling Note-related forms
 * Provides functionality to create, edit, save, and delete Notes
 */
@Controller
public class NoteFormController {

    private static final Logger log = LoggerFactory.getLogger(NoteFormController.class);
    @Autowired private AuthService authService;
    @Autowired private NoteRepository noteRepository;
    @Autowired private CategoryService categoryService;
    @Autowired
    @Qualifier("notemapper")
    private NoteMapper mapper = new NoteMapper();

    @Autowired
    NoteService svc;

    /**
     * Adds the currently authenticated user's name to the model
     *
     * @param userDetails The authenticated User details
     * @return The username or "Gast" if the user is not found
     */
    @ModelAttribute("username")
    public String addUserToModel(@AuthenticationPrincipal UserDetails userDetails) {
        return authService.findUserByEmail(userDetails.getUsername())
                .map(User::getName)
                .orElse("Gast");
    }
    /**
     * Displays the form to create a new Note
     *
     * @param userDetails The authenticated User details
     * @param model The model to store attributes
     * @return The note creation form view
     */
    @GetMapping("/user/notes/new")
    public String showNewNoteForm(@AuthenticationPrincipal UserDetails userDetails,Model model) {
        log.debug("entering showNewNoteForm");

        if (userDetails == null) return "redirect:/user/login";

        String email = userDetails.getUsername();
        authService.findUserByEmail(email).ifPresentOrElse(
                user -> {
                    //List<Category> c = categoryService.getCategoryByUserId(user.getId());
                    List<Category> c = categoryService.getCategoryByUserIdOrGlobal(user.getId());
                    model.addAttribute("categories", c);
                    model.addAttribute("note", new Note());
                },
                () -> model.addAttribute("error", "Benutzer nicht gefunden.")
        );
        return "note-form";
    }

    /**
     * Displays the form to edit an existing Note
     *
     * @param id The ID of the Note to edit
     * @param model The model to store attributes
     * @return The Note editing form view
     */
    @GetMapping("/user/notes/{id}")
    public String showEditNoteForm(@PathVariable("id") Long id,@AuthenticationPrincipal UserDetails userDetails, Model model) {
        log.debug("entering showEditNoteForm for note id: {}", id);


        if (userDetails == null) return "redirect:/user/login";

        String email = userDetails.getUsername();
        authService.findUserByEmail(email).ifPresentOrElse(
                user -> {
                    List<Category> c = categoryService.getCategoryByUserIdOrGlobal(user.getId());
                    model.addAttribute("categories", c);
                    model.addAttribute("note", new Note());
                    noteRepository.findById(id).ifPresentOrElse(
                    note -> model.addAttribute("note", note),
                            () -> model.addAttribute("error", "Notiz nicht gefunden.")
        );
                },
                () -> model.addAttribute("error", "Benutzer nicht gefunden.")
        );


        /*
        noteRepository.findById(id).ifPresentOrElse(
                List<Category> c = categoryService.getCategoryByUserId(user.getId());
                note -> model.addAttribute("note", note),
                () -> model.addAttribute("error", "Notiz nicht gefunden.")
        );*/


        return "note-form";
    }
    /**
     * Saves a new or updated Note
     *
     * @param note The Note object
     * @param userDetails The authenticated User details
     * @param model The model to store attributes
     * @return Redirects to the User dashboard after saving
     */
    @PostMapping("/user/notes")
    public String saveNote(@ModelAttribute("note") Note note,
                           @AuthenticationPrincipal UserDetails userDetails,
                           Model model) {
        log.debug("Saving note with id: {}", note.getId());
        if (userDetails == null) return "redirect:/user/login";

        String email = userDetails.getUsername();
        authService.findUserByEmail(email).ifPresent(user -> {
            note.setUser(user);
            if (note.getCategory() != null && note.getCategory().getName() != null) {
                Category category = categoryService.findOrCreateCategory(note.getCategory().getName(), user);
                note.setCategory(category);
            }
            authService.saveNote(note);
        });
        return "redirect:/user";
    }
    /**
     * Deletes a Note by its ID
     *
     * @param id The ID of the Note to delete
     * @return Redirects to the User dashboard after deletion
     */
    @PostMapping("/user/notes/{id}/delete")
    public String deleteNote(@PathVariable Long id) {
        System.out.println("DeleteNote aufgerufen mit ID: " + id);
        Optional<Note> optionalNote = noteRepository.findById(id);
        if (optionalNote.isEmpty()) {
            return "redirect:/user?error=NotizNichtGefunden";
        }
        Note note = optionalNote.get();
        noteRepository.delete(note);
        return "redirect:/user";
    }
}
