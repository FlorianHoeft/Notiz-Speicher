package de.thowl.prog3.exam.service;

import java.util.List;

import de.thowl.prog3.exam.storage.entities.User;
/**
 * Interface for managing and retrieving Users
 */
public interface UserService {
    /**
     * Retrieves a single User by its Id
     *
     * @param id Id of a User
     */
    public User getUser(long id);

    public User getUser(String name);

    public List<User> getAllUsers();

}