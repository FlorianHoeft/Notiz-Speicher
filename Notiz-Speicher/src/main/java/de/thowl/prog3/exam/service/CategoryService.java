package de.thowl.prog3.exam.service;

import java.util.List;

import de.thowl.prog3.exam.storage.entities.Category;
import de.thowl.prog3.exam.storage.entities.User;

/**
 * Interface for managing and retrieving Notes
 */
public interface CategoryService {

    public Category getCategory(long id);

    public Category getCategory(String name);

    public List<Category> getAllCategories();

    public Category findOrCreateCategory(String categoryName, User user);

    public Category getStandardCategory();

    /**
     * Retrieves a List of Categories based on a User ID
     *
     * @param userId Id of a User
     */
    List<Category> getCategoryByUserIdOrGlobal(Long userId);

    public List<Category> getCategoryByUserId(Long userId);



}
