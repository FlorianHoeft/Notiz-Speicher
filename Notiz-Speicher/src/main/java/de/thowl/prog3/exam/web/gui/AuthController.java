package de.thowl.prog3.exam.web.gui;

import de.thowl.prog3.exam.service.AuthService;
import de.thowl.prog3.exam.web.api.UserController;
import de.thowl.prog3.exam.web.mapper.UserMapper;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import lombok.extern.slf4j.Slf4j;
import de.thowl.prog3.exam.service.UserService;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.Optional;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.ui.Model;
import de.thowl.prog3.exam.storage.entities.User;

@Controller
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final AuthService authService;

    //SELECT * FROM USERS
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Autowired
    @Qualifier("usermapper")
    private UserMapper mapper = new UserMapper();


    @GetMapping("/user/login")
    public String showLoginForm() {
        log.debug("entering showLoginForm");
        return "login";
    }

    @GetMapping("/user/register")
    public String showRegisterForm() {
        log.debug("entering showRegisterForm");
        return "register";
    }

    @GetMapping("/user/logout")
    public String showLogoutForm() {
        log.debug("entering showLogoutForm");
        return "logout";
    }

    @PostMapping("/user/register")
    public String register(@RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestParam("e-mail") String email,
                           RedirectAttributes redirectAttributes) {
        log.debug("Processing registration for user: {}", username);
        boolean emailExists = authService.findUserByEmail(email).isPresent();
        if (emailExists) {
            // Wenn die E-Mail bereits vergeben ist, gib eine Fehlermeldung zurück
            redirectAttributes.addFlashAttribute("error", "Die E-Mail-Adresse ist bereits vergeben.");
            return "redirect:/user/register";
        }
        boolean registerSuccess = authService.register(username, password, email);
        if (registerSuccess) {
            log.debug("Registration was successful for user: {}", username);
            return "redirect:/user/login";
        } else {
            log.debug("Registration was not done for user: {}", username);
            return "redirect:/user/register";
        }
    }

    @PostMapping("/user/newPassword")
    public String changePassword(@AuthenticationPrincipal UserDetails userDetails,
                                 Model model,
                                 @RequestParam String currentPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmPassword) {
        log.debug("Entering changePassword");

        Optional<User> optionalUser = authService.findUserByEmail(userDetails.getUsername());
        if (optionalUser.isEmpty()) {
            model.addAttribute("error", "Benutzer nicht gefunden.");
            return "redirect:/user/profile";
        }

        User user = optionalUser.get();

        // Passwort ändern über den AuthService
        if (!authService.changePassword(user.getId(), currentPassword, newPassword)) {
            model.addAttribute("error", "Das aktuelle Passwort ist falsch.");
            return "redirect:/user/profile";
        }

        model.addAttribute("success", "Passwort erfolgreich geändert.");
        return "redirect:/user/profile";
    }

    /**
     * Löscht den angemeldeten Benutzer und leitet zur Startseite um.
     * @param userDetails Die Details des angemeldeten Benutzers.
     * @param redirectAttributes Zum Übermitteln von Erfolgsmeldungen.
     * @return Weiterleitung zur Startseite.
     */
    @PostMapping("/user/delete")
    public String deleteUser(@AuthenticationPrincipal UserDetails userDetails,
                             RedirectAttributes redirectAttributes) {
        authService.findUserByEmail(userDetails.getUsername())
                .ifPresent(user -> {
                    if(authService.deleteUser(user.getId())) {
                        redirectAttributes.addFlashAttribute("success", "Benutzer wurde erfolgreich gelöscht.");
                    }
                });
        return "redirect:/user/login?profileDeleted=true";
    }
}
