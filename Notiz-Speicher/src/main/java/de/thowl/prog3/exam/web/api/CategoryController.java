package de.thowl.prog3.exam.web.api;

import java.util.ArrayList;
import java.util.List;

import de.thowl.prog3.exam.storage.entities.Category;
import de.thowl.prog3.exam.web.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.thowl.prog3.exam.service.CategoryService;
import de.thowl.prog3.exam.storage.entities.Note;
import de.thowl.prog3.exam.web.mapper.NoteMapper;
import lombok.extern.slf4j.Slf4j;
/**
 * Rest controller that handles HTTP requests related to Category
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/category")// Base URL path for all user-related endpoints
public class CategoryController {

    @Autowired
    @Qualifier("categorymapper")
    private CategoryMapper mapper = new CategoryMapper(); // Converts Category entities to DTOs

    @Autowired
    private CategoryService service;

    public CategoryController() {
        log.debug("entering ctor");
    }

    /**
     * Handles GET requests to /api/v1/category/
     * Retrieves a list of all Categories and returns them as DTOs
     *
     * @return A list of Categroy DTOs
     */
    @GetMapping("/")
    public List<de.thowl.prog3.exam.web.dto.Category> getCategory() {
        log.debug("entering getCategory");
        List<de.thowl.prog3.exam.web.dto.Category> result = new ArrayList<>();
        List<Category> cat = this.service.getAllCategories();
        for (Category c : cat) {
            result.add(this.mapper.map(c));
        }
        return result;
    }
    /**
     * Handles GET requests to /api/v1/category/{id}
     * Retrieves a single Category by their ID
     *
     * @param id The ID of the Category to retrieve
     * @return The Category DTOs corresponding to the Category
     */
    @GetMapping("/{id}")
    public de.thowl.prog3.exam.web.dto.Category getCategorybyId(@PathVariable Long id) {
        log.debug("entering getCategoryById, id={}", id);
        Category c = this.service.getCategory(id);
        return this.mapper.map(c);
    }
    /**
     * Handles GET requests to /api/v1/category/{id}
     * Retrieves a List of Categories corresponding to a User
     *
     * @param userId The ID of the User to retrieve
     * @return The Category DTOs corresponding to the User
     */
    @GetMapping("/user/{userId}")
    public List<Category> getCategoryByUserId(@PathVariable Long userId) {
        return service.getCategoryByUserId(userId);
    }
}
