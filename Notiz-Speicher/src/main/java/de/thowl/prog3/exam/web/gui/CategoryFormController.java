package de.thowl.prog3.exam.web.gui;

import de.thowl.prog3.exam.service.AuthService;
import de.thowl.prog3.exam.storage.entities.Category;
import de.thowl.prog3.exam.storage.repositories.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
/**
 * Controller for handling Category-related forms
 * Allows users to add and delete Categories
 */
@Controller
public class CategoryFormController {
    @Autowired
    private AuthService authService;
    private static final Logger log = LoggerFactory.getLogger(NoteFormController.class);
    @Autowired private CategoryRepository categoryRepository;

    /**
     * Adds a new Category for the authenticated User
     *
     * @param userDetails The authentication details of the logged in User
     * @param name The name of the new Category
     * @return Redirects to the Users profile page
     */
    @PostMapping("/user/kategorien")
    public String addCategory(@AuthenticationPrincipal UserDetails userDetails, @RequestParam String name) {
        String email = userDetails.getUsername();
        authService.findUserByEmail(email).ifPresentOrElse(
                user -> {
                    Category category = Category.builder()
                            .name(name)
                            .user(user)
                            .build();
                    categoryRepository.save(category);
                },
                () -> {
                    // Handle case where user is not found (redirect or error message)
                }
        );
        return "redirect:/user/profile";
    }
    /**
     * Deletes a category based on its ID
     *
     * @param categoryId The ID of the Category to delete
     * @return Redirects to the Users profile page
     */
    @DeleteMapping("/user/kategorien/{categoryId}")
    public String deleteCategory(@PathVariable Long categoryId) {
        categoryRepository.deleteById(categoryId);
        return "redirect:/user/profile";
    }
}
