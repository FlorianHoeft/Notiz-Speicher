package de.thowl.prog3.exam.web.gui;

import de.thowl.prog3.exam.service.AuthService;
import de.thowl.prog3.exam.service.NoteService;
import de.thowl.prog3.exam.storage.entities.Note;
import de.thowl.prog3.exam.storage.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.Optional;


import de.thowl.prog3.exam.service.UserService;
import de.thowl.prog3.exam.storage.entities.User;
import de.thowl.prog3.exam.web.gui.form.UserForm;
import de.thowl.prog3.exam.web.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Controller
public class UserFormController {
    private final AuthService authService;

    public UserFormController(AuthService authService) {
        this.authService = authService;
    }

    @Autowired private NoteRepository noteRepository;

    @Autowired
    private NoteService service;

    @Autowired
    @Qualifier("usermapper")
    private UserMapper mapper = new UserMapper();

    @Autowired
    UserService svc;

    @GetMapping("/user")
    public String showNotes(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        log.debug("entering showNotes");
        if (userDetails == null) return "redirect:/user/login";

        String email = userDetails.getUsername();
        authService.findUserByEmail(email).ifPresentOrElse(
                user -> {
                    model.addAttribute("user", user);
                    model.addAttribute("notes", noteRepository.findByUserId(user.getId()));
                },
                () -> model.addAttribute("error", "Benutzer nicht gefunden.")
        );
        return "user";
    }

    @GetMapping("/user/forgot-password")
    public String showForgot_passwordForm() {
        log.debug("entering showForgot-passwordForm");
        return "forgot-password";
    }

    @GetMapping("/user/profile")
    public String showProfile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        log.debug("entering showProfile");
        if (userDetails == null) return "redirect:/user/login";
        String email = userDetails.getUsername();
        authService.findUserByEmail(email).ifPresentOrElse(
                user -> model.addAttribute("user", user),
                () -> model.addAttribute("error", "Benutzer nicht gefunden.")
        );
        return "profile";
    }
    @GetMapping("/user/settings")
    public String showSettingsForm() {
        log.debug("entering showSettingsForm");
        return "settings";
    }
    @GetMapping("/user/search")
    public String showSearchForm() {
        log.debug("entering showSearchForm");
        return "search";
    }
    @GetMapping("/user/favorites")
    public String showFavoritesForm(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        log.debug("entering showFavoritesForm");
        if (userDetails == null) return "redirect:/user/login";

        String email = userDetails.getUsername();
        authService.findUserByEmail(email).ifPresentOrElse(
                user -> {
                    List<Note> favorites = noteRepository.findNoteByFavorite(true); // musst noch angepasst werde. Es muss nach user gesucht werden
                    model.addAttribute("favorites", favorites);
                    if (!favorites.isEmpty()) {
                        log.debug("First favorite note content: {}", favorites.get(0).getContent());
                    } else {
                        log.debug("No favorite notes found for user: {}", email);
                    }
                },
                () -> model.addAttribute("error", "Benutzer nicht gefunden.")
        );
        return "favorites";
    }
    @GetMapping("/user/documents")
    public String showDocumentsForm(Model model) {
        long usertestid=1L;
        log.debug("entering showDocumentsForm");
        List<Note> n = service.getNoteByUserId(usertestid);
        model.addAttribute("n", n);
        return "documents";
    }

    @PostMapping("/user")
    public String processUserForm(Model model, UserForm formdata) {
        log.debug("entering processUserForm");
        String username = formdata.getUsername();
        log.debug("searching for User={}", username);

        // retrieve user record
        String target = "userform"; // FAILURE LANE -> back to form page
        try {
            User u = this.svc.getUser(username);
            if (u != null) {
                model.addAttribute("user", this.mapper.map(u));
                target = "showuser"; // SUCCESS LANE
            }
        } catch (Exception e) {
            log.error("unable to retrieve user data");
        }
        return target;
    }

}
