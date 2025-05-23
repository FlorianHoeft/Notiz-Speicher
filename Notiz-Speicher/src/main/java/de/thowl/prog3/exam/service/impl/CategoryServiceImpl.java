package de.thowl.prog3.exam.service.impl;

import de.thowl.prog3.exam.service.CategoryService;
import de.thowl.prog3.exam.storage.entities.Category;
import de.thowl.prog3.exam.storage.entities.User;
import de.thowl.prog3.exam.storage.repositories.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the CategoryService interface
 * Provides methods to retrieve Categories from the repository
 */
@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired // Automatically injects the CategoryRepository bean
    private CategoryRepository repository;

    /**
     * Retrieves a Category by its ID
     *
     * @param id The ID of the Category
     * @return The Category object if found
     */
    @Override
    public Category getCategory(long id) {
        log.debug("entering getCategory(id={})", id);
        Optional<Category> result = this.repository.findCategoryById(id);
        return result.orElseThrow(IllegalArgumentException::new);
    }

    /**
     * Retrieves a Category by their name
     *
     * @param name The name of the Category
     * @return The Category object if found
     */
    @Override
    public Category getCategory(String name) {
        log.debug("entering getCategory(title={})", name);
        Optional<Category> result = this.repository.findCategoryByName(name);
        return result.orElseThrow(IllegalArgumentException::new);
    }

    /**
     * Retrieves a list of all Categories
     *
     * @return A list of all Category objects
     */
    @Override
    public List<Category> getAllCategories() {
        log.debug("entering getAllCategory()");
        ArrayList<Category> result = new ArrayList<>();
        for (Category c : this.repository.findAll()) {
            result.add(c);
        }
        return result;
    }

    /**
     * Retrieves all Categories that belong to a specific User
     *
     * @param userId The ID of the User
     * @return A list of Category objects associated with the User
     */
    public List<Category> getCategoryByUserId(Long userId) {
        return repository.findCategoryByUserId(userId);
    }

    /**
     * Finds an existing Category by name and User, or creates a new one if it doesnt exist
     * Global categories (with userId = null) are reused instead of being duplicated.
     *
     * @param categoryName The name of the Category
     * @param user         The User who owns the Category
     * @return The found or newly created Category object
     */
    @Override
    public Category findOrCreateCategory(String categoryName, User user) {
        log.debug("entering findOrCreateCategory(categoryName={}, userId={})", categoryName, user.getId());
        Optional<Category> globalCategory = repository.findByNameAndUser(categoryName.trim(), null);

        if (globalCategory.isPresent()) {
            log.info("Using existing global category: {}", categoryName);
            return globalCategory.get();
        }
        return repository.findByNameAndUser(categoryName.trim(), user)

                .orElseGet(() -> {
                    log.info("Creating new category: {}", categoryName);
                    Category newCategory = new Category();
                    newCategory.setName(categoryName.trim());
                    newCategory.setUser(user);
                    return repository.save(newCategory);
                });
    }

    /**
     * Getting Category by User or the global if there are none
     *
     * @param userId Id of a User
     * @return All categories
     */
    @Override
    public List<Category> getCategoryByUserIdOrGlobal(Long userId) {
        return repository.findByUserIdOrGlobal(userId);
    }

    @Override
    public Category getStandardCategory() {
        return repository.findStandardCategory();
    }
}
