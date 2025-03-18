package de.thowl.prog3.exam.web.gui;

import de.thowl.prog3.exam.service.AuthService;
import de.thowl.prog3.exam.service.NoteService;
import de.thowl.prog3.exam.storage.entities.Category;
import de.thowl.prog3.exam.storage.repositories.CategoryRepository;
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
import org.springframework.web.bind.annotation.ModelAttribute;
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
                    System.out.println("Hallo hier hier hier"+user.getId());//Tests um user zu überprüfen
                    //List<Note> favorites = noteRepository.findNoteByFavorite(true); // Veraltet
                    List<Note> favorites = noteRepository.findNoteByFavoriteAndUserId(true, user.getId());
                    System.out.println("Hallo hallo hallo hierhalloooo   "+favorites.size()); //Test für notizliste
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
    public String showDocumentsForm(@AuthenticationPrincipal UserDetails userDetails,Model model) {

        String email = userDetails.getUsername();
        authService.findUserByEmail(email).ifPresentOrElse(
        user -> {
            System.out.println("Hallo hier hier hier"+user.getId()); //Tests um user zu überprüfen
            log.debug("entering showDocumentsForm");
            List<Note> n = service.getNoteByUserId(user.getId());
            model.addAttribute("n", n);
        },
                () -> model.addAttribute("error", "Benutzer nicht gefunden.")
        );
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
