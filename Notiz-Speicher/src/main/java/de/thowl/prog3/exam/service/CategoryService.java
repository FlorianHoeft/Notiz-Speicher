package de.thowl.prog3.exam.service;

import java.util.List;

import de.thowl.prog3.exam.storage.entities.Category;
import de.thowl.prog3.exam.storage.entities.Note;
import de.thowl.prog3.exam.storage.entities.User;
import de.thowl.prog3.exam.storage.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import de.thowl.prog3.exam.storage.entities.Category;

/**
 * Interface for managing and retrieving Notes
 */
public interface CategoryService {

    public Category getCategory(long id);

    public Category getCategory(String name);

    public List<Category> getAllCategories();

    public Category findOrCreateCategory(String categoryName, User user);

    /**
     * Retrieves a List of Categories based on a User ID
     *
     * @param userId Id of a User
     */
    List<Category> getCategoryByUserIdOrGlobal(Long userId);

    public List<Category> getCategoryByUserId(Long userId);



}
