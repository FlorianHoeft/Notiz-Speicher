package de.thowl.prog3.exam.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.thowl.prog3.exam.service.CategoryService;
import de.thowl.prog3.exam.storage.entities.Category;
import de.thowl.prog3.exam.storage.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.thowl.prog3.exam.service.CategoryService;
import de.thowl.prog3.exam.storage.entities.Note;
import de.thowl.prog3.exam.storage.repositories.CategoryRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Override
    public Category getCategory(long id) {
        log.debug("entering getCategory(id={})", id);
        Optional<Category> result = this.repository.findCategoryById(id);
        return result.orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public Category getCategory(String name) {
        log.debug("entering getCategory(title={})", name);
        Optional<Category> result = this.repository.findCategoryByName(name);
        return result.orElseThrow(IllegalArgumentException::new);
    }


    @Override
    public List<Category> getAllCategories() {
        log.debug("entering getAllCategory()");
        ArrayList<Category> result = new ArrayList<>();
        for (Category c : this.repository.findAll()) {
            result.add(c);
        }
        return result;
    }



    public List<Category> getCategoryByUserId(Long userId) {
        return repository.findCategoryByUserId(userId);
    }

    @Override
    public Category findOrCreateCategory(String categoryName, User user) {
        log.debug("entering findOrCreateCategory(categoryName={}, userId={})", categoryName, user.getId());

        return repository.findByNameAndUser(categoryName.trim(), user)
                .orElseGet(() -> {
                    log.info("Creating new category: {}", categoryName);
                    Category newCategory = new Category();
                    newCategory.setName(categoryName.trim());
                    newCategory.setUser(user);
                    return repository.save(newCategory);
                });
    }
}
