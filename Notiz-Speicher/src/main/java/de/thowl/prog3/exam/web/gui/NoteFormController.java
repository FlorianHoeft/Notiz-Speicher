package de.thowl.prog3.exam.web.gui;

import de.thowl.prog3.exam.service.CategoryService;
import de.thowl.prog3.exam.service.UserService;
import de.thowl.prog3.exam.storage.entities.*;
import de.thowl.prog3.exam.storage.repositories.CategoryRepository;
import de.thowl.prog3.exam.storage.repositories.NoteRepository;
import de.thowl.prog3.exam.web.gui.form.UserForm;
import de.thowl.prog3.exam.web.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import de.thowl.prog3.exam.service.NoteService;
import de.thowl.prog3.exam.web.gui.form.NoteForm;
import de.thowl.prog3.exam.web.mapper.NoteMapper;
import lombok.extern.slf4j.Slf4j;
import de.thowl.prog3.exam.service.AuthService;
import de.thowl.prog3.exam.storage.entities.Note;
import de.thowl.prog3.exam.storage.entities.Note;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


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
     * Stellt den Benutzernamen global für alle Templates bereit.
     *
     * @param userDetails Der eingeloggte Benutzer.
     * @return Der Benutzername oder "Gast", falls nicht eingeloggt.
     */
    @ModelAttribute("username")
    public String addUserToModel(@AuthenticationPrincipal UserDetails userDetails) {
        return authService.findUserByEmail(userDetails.getUsername())
                .map(User::getName)
                .orElse("Gast");
    }

    @GetMapping("/user/notes/new")
    public String showNewNoteForm(Model model) {
        log.debug("entering showNewNoteForm");
        model.addAttribute("note", new Note());
        return "note-form";
    }

    @GetMapping("/user/notes/{id}")
    public String showEditNoteForm(@PathVariable("id") Long id, Model model) {
        log.debug("entering showEditNoteForm for note id: {}", id);
        noteRepository.findById(id).ifPresentOrElse(
                note -> model.addAttribute("note", note),
                () -> model.addAttribute("error", "Notiz nicht gefunden.")
        );
        return "note-form"; // Wiederverwendung desselben Formulars
    }

    @PostMapping("/user/notes")
    public String saveNote(@ModelAttribute("note") Note note,
                           @AuthenticationPrincipal UserDetails userDetails,
                           Model model) {
        log.debug("Saving note with id: {}", note.getId());
        if (userDetails == null) return "redirect:/user/login";

        String email = userDetails.getUsername();
        authService.findUserByEmail(email).ifPresent(user -> {
            note.setUser(user); // Verknüpfung mit dem Benutzer
            if (note.getCategory() != null && note.getCategory().getName() != null) {
                Category category = categoryService.findOrCreateCategory(note.getCategory().getName(), user);
                note.setCategory(category);
            }
            authService.saveNote(note); // Speichert sowohl neue als auch bearbeitete Notizen
        });
        return "redirect:/user";
    }
}
