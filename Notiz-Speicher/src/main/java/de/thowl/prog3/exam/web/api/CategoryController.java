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

@Slf4j
@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    @Qualifier("categorymapper")
    private CategoryMapper mapper = new CategoryMapper();

    @Autowired
    private CategoryService service;

    public CategoryController() {
        log.debug("entering ctor");
    }

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

    @GetMapping("/{id}")
    public de.thowl.prog3.exam.web.dto.Category getCategorybyId(@PathVariable Long id) {
        log.debug("entering getCategoryById, id={}", id);
        Category c = this.service.getCategory(id);
        return this.mapper.map(c);
    }

    @GetMapping("/user/{userId}")
    public List<Category> getCategoryByUserId(@PathVariable Long userId) {
        return service.getCategoryByUserId(userId);
    }
}
