package de.thowl.prog3.exam.service.impl;

import de.thowl.prog3.exam.service.UserService;
import de.thowl.prog3.exam.storage.entities.User;
import de.thowl.prog3.exam.storage.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the UserService interface
 * Provides methods to retrieve Users from the repository
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired// Automatically injects the UserRepository bean
    private UserRepository repository;

    /**
     * Retrieves a User by their ID
     *
     * @param id The ID of a User
     * @return The User object if found
     */
    @Override
    public User getUser(long id) {
        log.debug("entering getUser(id={})", id);
        Optional<User> result = this.repository.findById(id);
        return result.orElseThrow(IllegalArgumentException::new);
    }

    /**
     * Retrieves a User by their name
     *
     * @param name The name of the User
     * @return The User object if found
     */
    @Override
    public User getUser(String name) {
        log.debug("entering getUser(name={})", name);
        Optional<User> result = this.repository.findUserByName(name);
        return result.orElseThrow(IllegalArgumentException::new);
    }

    /**
     * Retrieves a list of all Users
     *
     * @return A list of all User objects
     */
    @Override
    public List<User> getAllUsers() {
        log.debug("entering getAllUsers()");
        ArrayList<User> result = new ArrayList<>();
        for (User u : this.repository.findAll()) {
            result.add(u);
        }
        return result;
    }
}