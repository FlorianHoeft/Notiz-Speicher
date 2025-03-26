package de.thowl.prog3.exam.web.gui;

import de.thowl.prog3.exam.service.AuthService;
import de.thowl.prog3.exam.service.CategoryService;
import de.thowl.prog3.exam.service.NoteService;
import de.thowl.prog3.exam.storage.entities.Category;
import de.thowl.prog3.exam.storage.repositories.CategoryRepository;
import de.thowl.prog3.exam.storage.entities.Note;
import de.thowl.prog3.exam.storage.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import de.thowl.prog3.exam.service.UserService;
import de.thowl.prog3.exam.storage.entities.User;
import de.thowl.prog3.exam.web.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
/**
 * Controller for handling User-related forms
 * Provides functionalities for displaying user notes, profile, favorite notes, searching notes, and user processing
 */
@Slf4j
@Controller
public class UserFormController {
    private final AuthService authService;
    /**
     * Constructor-based dependency injection for AuthService
     *
     * @param authService The authentication service
     */
    public UserFormController(AuthService authService) {
        this.authService = authService;
    }

    @Autowired private NoteRepository noteRepository;

    @Autowired private CategoryRepository categoryRepository;

    @Autowired
    private NoteService service;

    @Autowired
    private CategoryService cservice;

    @Autowired
    @Qualifier("usermapper")
    private UserMapper mapper = new UserMapper();

    @Autowired
    UserService svc;
    /**
     * Adds the currently authenticated Users name to the model
     *
     * @param userDetails The authenticated User details
     * @return The username or "Gast" if the User is not found
     */
    @ModelAttribute("username")
    public String addUserToModel(@AuthenticationPrincipal UserDetails userDetails) {
        return authService.findUserByEmail(userDetails.getUsername())
                .map(User::getName)
                .orElse("Gast");
    }
    /**
     * Displays the Users notes page
     *
     * @param userDetails The authenticated User details
     * @param model The model to store attributes
     * @return The User notes page or redirects to login if unauthenticated
     */
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
    /**
     * Displays the forgot password form
     *
     * @return The forgot password view
     */
    @GetMapping("/user/forgot-password")
    public String showForgot_passwordForm() {
        log.debug("entering showForgot-passwordForm");
        return "forgot-password";
    }
    /**
     * Displays the User profile page
     *
     * @param userDetails The authenticated User details
     * @param model The model to store attributes
     * @return The profile view or redirects to login if unauthenticated
     */
    @GetMapping("/user/profile")
    public String showProfile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        log.debug("entering showProfile");
        if (userDetails == null) return "redirect:/user/login";
        String email = userDetails.getUsername();
        authService.findUserByEmail(email).ifPresentOrElse(
                user -> {
                    model.addAttribute("user", user);

                    int notesCount = noteRepository.countByUser(user);
                    model.addAttribute("notesCount", notesCount);
                    model.addAttribute("kategorien", categoryRepository.findByUser(user));
                },
                () -> model.addAttribute("error", "Benutzer nicht gefunden.")
        );
        return "profile";
    }
    /**
     * Displays the search form and handles Note search query
     *
     * @param userDetails The authenticated User details
     * @param keyword The search keyword
     * @param categoryId The Category ID filter
     * @param model The model to store search results
     * @return The search results view
     */
    @GetMapping("/user/search")
    public String showSearchForm(@AuthenticationPrincipal UserDetails userDetails,
                                 @RequestParam(name = "query", required = false) String keyword,
                                 @RequestParam(name = "category", required = false) Long categoryId, Model model,
                                 @RequestParam(name = "sort", required = false) String sort) {
        log.debug("entering showSearchForm");

        if (userDetails == null) return "redirect:/user/login";

        String email = userDetails.getUsername();
        authService.findUserByEmail(email).ifPresentOrElse(
                user -> {
                    List<Category> c = cservice.getCategoryByUserIdOrGlobal(user.getId());
                    List<Note> n = service.getNoteByUserId(user.getId());
                    model.addAttribute("search", n);
                    model.addAttribute("categories", c);

                    Sort sortOption = Sort.by(Sort.Direction.ASC, "id");
                    if ("date_desc".equals(sort)) {
                        log.debug("Sorting by lastModified DESC");
                        sortOption = Sort.by(Sort.Direction.DESC, "lastModified");
                    } else if ("date_asc".equals(sort)) {
                        log.debug("Sorting by lastModified ASC");
                        sortOption = Sort.by(Sort.Direction.ASC, "lastModified");
                    }

                    List<Note> searchedNotes = service.searchNotesSorted(user.getId(), keyword, categoryId, sortOption);

                    model.addAttribute("searchedNotes", searchedNotes);
                    model.addAttribute("currentSort", sort);
                    if (!n.isEmpty()) {
                        log.debug("First note content: {}", n.get(0).getContent());
                    } else {
                        log.debug("No notes found for user: {}", email);
                    }

                },
                () -> model.addAttribute("error", "Benutzer nicht gefunden.")
        );
        return "search";
    }
    /**
     * Displays the Users favorite Notes
     *
     * @param userDetails The authenticated User details
     * @param model The model to store attributes
     * @return The favorites view
     */
    @GetMapping("/user/favorites")
    public String showFavoritesForm(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        log.debug("entering showFavoritesForm");

        if (userDetails == null) return "redirect:/user/login";

        String email = userDetails.getUsername();
        authService.findUserByEmail(email).ifPresentOrElse(
                user -> {
                    List<Note> favorites = noteRepository.findNoteByFavoriteAndUserId(true, user.getId());
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
    /**
     * Displays the Users documents
     *
     * @param userDetails The authenticated User details
     * @param model The model to store attributes
     * @return The documents view
     */
    @GetMapping("/user/documents")
    public String showDocumentsForm(@AuthenticationPrincipal UserDetails userDetails,Model model) {

        String email = userDetails.getUsername();
        authService.findUserByEmail(email).ifPresentOrElse(
        user -> {
            log.debug("entering showDocumentsForm");
            List<Note> n = service.getNoteByUserId(user.getId());
            model.addAttribute("n", n);
        },
                () -> model.addAttribute("error", "Benutzer nicht gefunden.")
        );
        return "documents";
    }
}


