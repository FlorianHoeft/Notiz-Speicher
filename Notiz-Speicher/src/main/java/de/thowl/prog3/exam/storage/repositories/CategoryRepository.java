package de.thowl.prog3.exam.storage.repositories;

import java.util.Optional;

import de.thowl.prog3.exam.storage.entities.Category;

import de.thowl.prog3.exam.storage.entities.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
/**
 * Repository interface for accessing and managing Category entities in the database
 * Extends JpaRepository to provide basic JPA operations and custom query methods
 */
public interface CategoryRepository extends JpaRepository<Category, Long>{

    public Optional<Category> findCategoryById(long id);

    public Optional<Category> findCategoryByName(String name);

    public List<Category> findCategoryByUserId(Long userId);

    public Optional<Category> findByNameAndUser(String name, User user);

    public List<Category> findByUser(User user);
}
