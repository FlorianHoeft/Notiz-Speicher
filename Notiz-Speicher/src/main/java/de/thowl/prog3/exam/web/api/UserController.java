package de.thowl.prog3.exam.web.api;

import de.thowl.prog3.exam.service.UserService;
import de.thowl.prog3.exam.storage.entities.User;
import de.thowl.prog3.exam.web.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Rest controller that handles HTTP requests related to Users
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/users")// Base URL path for all user-related endpoints
public class UserController {

    @Autowired
    @Qualifier("usermapper")
    private UserMapper mapper = new UserMapper();// Converts User entities to DTOs

    @Autowired
    private UserService service;

    public UserController() {
        log.debug("entering ctor");
    }

    /**
     * Handles GET requests to /api/v1/users/
     * Retrieves a list of all Users and returns them as DTOs
     *
     * @return A list of User DTOs
     */
    @GetMapping("/")
    public List<de.thowl.prog3.exam.web.dto.User> getUsers() {
        log.debug("entering getUsers");
        List<de.thowl.prog3.exam.web.dto.User> result = new ArrayList<>();
        List<User> users = this.service.getAllUsers();
        for (User u : users) {
            result.add(this.mapper.map(u));
        }
        return result;
    }

    /**
     * Handles GET requests to /api/v1/users/{id}
     * Retrieves a single User by their ID
     *
     * @param id The ID of the user to retrieve
     * @return The User DTOs corresponding to the User
     */
    @GetMapping("/{id}")
    public de.thowl.prog3.exam.web.dto.User getUserbyId(@PathVariable Long id) {
        log.debug("entering getUserById, id={}", id);
        User u = this.service.getUser(id);
        return this.mapper.map(u);
    }
}
