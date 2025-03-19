package de.thowl.prog3.exam.web.gui;

import de.thowl.prog3.exam.service.AuthService;
import de.thowl.prog3.exam.service.CategoryService;
import de.thowl.prog3.exam.service.UserService;
import de.thowl.prog3.exam.storage.entities.Category;
import de.thowl.prog3.exam.storage.repositories.CategoryRepository;
import de.thowl.prog3.exam.storage.repositories.NoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

@Controller
public class CategoryFormController {
    @Autowired
    private AuthService authService;
    private static final Logger log = LoggerFactory.getLogger(NoteFormController.class);
    @Autowired private CategoryRepository categoryRepository;

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
    @DeleteMapping("/user/kategorien/{categoryId}")
    public String deleteCategory(@PathVariable Long categoryId) {
        categoryRepository.deleteById(categoryId); // Lösche die Kategorie
        return "redirect:/user/profile"; // Nach dem Löschen auf das Profil weiterleiten
    }
}
