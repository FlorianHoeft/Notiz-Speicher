package de.thowl.prog3.exam.storage;

import de.thowl.prog3.exam.storage.entities.Category;
import de.thowl.prog3.exam.storage.repositories.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Initializer to load default Categories on first start
 */
@Component
public class DataInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final CategoryRepository categoryRepository;

    public DataInitializer(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Creates three different Categories, with user_id=null
     */
    @Override
    public void run(ApplicationArguments args){
        if (categoryRepository.count() == 0) {
            Category cat1 = new Category();
            cat1.setName("Arbeit");
            cat1.setUser(null);

            Category cat2 = new Category();
            cat2.setName("Privat");
            cat2.setUser(null);

            Category cat3 = new Category();
            cat3.setName("Rezepte");
            cat3.setUser(null);

            categoryRepository.save(cat1);
            categoryRepository.save(cat2);
            categoryRepository.save(cat3);

            log.info("Default Categories added successfully");
        } else {
            log.info("Categories already exist");
        }

    }
}
