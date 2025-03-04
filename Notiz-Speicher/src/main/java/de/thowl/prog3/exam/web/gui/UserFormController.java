package de.thowl.prog3.exam.web.gui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import de.thowl.prog3.exam.service.UserService;
import de.thowl.prog3.exam.storage.entities.User;
import de.thowl.prog3.exam.web.gui.form.UserForm;
import de.thowl.prog3.exam.web.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class UserFormController {

    @Autowired
    @Qualifier("usermapper")
    private UserMapper mapper = new UserMapper();

    @Autowired
    UserService svc;

    @GetMapping("/user")
    public String showUserForm() {
        log.debug("entering showUserForm");
        return "user";
    }

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

    @GetMapping("/user/forgot-password")
    public String showForgot_passwordForm() {
        log.debug("entering showForgot-passwordForm");
        return "forgot-password";
    }

    @GetMapping("/user/profile")
    public String showProfileForm() {
        log.debug("entering showProfileForm");
        return "profile";
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
