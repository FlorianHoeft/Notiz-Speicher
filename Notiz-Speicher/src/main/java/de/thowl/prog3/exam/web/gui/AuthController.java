package de.thowl.prog3.exam.web.gui;

import de.thowl.prog3.exam.service.AuthService;
import de.thowl.prog3.exam.web.api.UserController;
import de.thowl.prog3.exam.web.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import lombok.extern.slf4j.Slf4j;
import de.thowl.prog3.exam.service.UserService;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private AuthService authService;

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

    @PostMapping("/user/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        RedirectAttributes redirectAttributes) {
        log.debug("Processing login for user: {}", email);
        boolean loginSuccess = authService.login(email, password);
        if (loginSuccess) {
            log.debug("Login was successful for user: {}", email);
            return "redirect:/user"; // Erfolgreicher Login → Weiterleitung
        } else {
            redirectAttributes.addFlashAttribute("error", "Falscher Benutzername oder Passwort!");
            log.debug("Login was not done for user: {}", email);
            return "redirect:/user/login";
        }
    }

    @PostMapping("/user/register")
    public String register(@RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestParam("e-mail") String email,
                           RedirectAttributes redirectAttributes) {
        log.debug("Processing registration for user: {}", username);
        boolean registerSuccess = authService.register(username, password, email);
        if (registerSuccess) {
            log.debug("Registration was successful for user: {}", username);
            return "redirect:/user/login";
        } else {
            log.debug("Registration was not done for user: {}", username);
            return "redirect:/user/register";
        }
    }
}
