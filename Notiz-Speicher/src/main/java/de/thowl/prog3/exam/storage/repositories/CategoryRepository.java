package de.thowl.prog3.exam.storage.repositories;

import de.thowl.prog3.exam.storage.entities.Category;
import de.thowl.prog3.exam.storage.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for accessing and managing Category entities in the database
 * Extends JpaRepository to provide basic JPA operations and custom query methods
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

    public Optional<Category> findCategoryById(long id);

    public Optional<Category> findCategoryByName(String name);

    public List<Category> findCategoryByUserId(Long userId);

    public Optional<Category> findByNameAndUser(String name, User user);

    public List<Category> findByUser(User user);

    @Query("SELECT c FROM Category c WHERE c.user.id = :userId OR c.user IS NULL")
    List<Category> findByUserIdOrGlobal(@Param("userId") Long userId);

    /**
     * Löscht alle Kategorien eines bestimmten Benutzers.
     *
     * @param userId Die ID des Benutzers, dessen Kategorien gelöscht werden sollen.
     */
    @Transactional
    void deleteByUserId(Long userId);
}
