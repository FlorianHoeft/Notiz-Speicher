package de.thowl.prog3.exam.service;

import java.util.List;

import de.thowl.prog3.exam.storage.entities.Category;
import de.thowl.prog3.exam.storage.entities.Note;

public interface CategoryService {

    public Category getCategory(long id);

    public Category getCategory(String name);

    public List<Category> getAllCategories();

    List<Category> getCategoryByUserId(Long userId);
}
