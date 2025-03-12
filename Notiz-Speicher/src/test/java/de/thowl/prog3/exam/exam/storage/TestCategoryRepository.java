package de.thowl.prog3.exam.exam.storage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Optional;

import de.thowl.prog3.exam.service.CategoryService;
import de.thowl.prog3.exam.storage.entities.Category;
import de.thowl.prog3.exam.storage.entities.Note;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;
import de.thowl.prog3.exam.storage.entities.Category;
import de.thowl.prog3.exam.storage.repositories.CategoryRepository;
import lombok.extern.slf4j.Slf4j;



@Slf4j
@SpringBootTest
public class TestCategoryRepository {

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private CategoryService service;
    @Test
    public void testGetCategoryByID() {
        log.info("Starting testGetCategoryByID");

        Optional<Category> c = this.repository.findCategoryById(1);
        assertTrue(c.isPresent(), "Unexpected empty result");
        log.debug("Got Category {}", c.get());
        assertFalse(c.isEmpty(), "Unexpected empty result");
    }


}
