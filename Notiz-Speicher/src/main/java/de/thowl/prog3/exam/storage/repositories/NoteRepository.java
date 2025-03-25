package de.thowl.prog3.exam.storage.repositories;

import java.util.Optional;

import de.thowl.prog3.exam.storage.entities.Note;

import de.thowl.prog3.exam.storage.entities.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository interface for accessing and managing Note entities in the database
 * Extends JpaRepository to provide basic JPA operations and custom query methods
 */
public interface NoteRepository extends JpaRepository<Note, Long> {

    public Optional<Note> findNoteById(long id);

    public Optional<Note> findNoteByTitle(String title);

    public Optional<Note> findNoteByContent(String content);

    public List<Note> findNoteByFavorite(boolean favorite);

    public List<Note> findNoteByFavoriteAndUserId(boolean favorite, long userId);

    public List<Note> findByUserId(Long userId);

    public Optional<Note> findByShareLink(String shareLink);

    public List<Note> findByUser(User user);

    public boolean existsByCategoryId(Long categoryId);

    /**
     * Finds notes for a user with optional filters: keyword (in title or content) and category
     *
     * @param userId the ID of the user
     * @param keyword the keyword to search in content or title (optional)
     * @param categoryId the ID of the category to filter by (optional)
     * @return a list of matching notes
     */
    @Query("SELECT n FROM Note n WHERE n.user.id = :userId " +
                  "AND (:keyword IS NULL OR LOWER(n.content) LIKE LOWER(CONCAT('%', :keyword, '%'))OR :keyword IS NULL OR LOWER(n.title) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
                     "AND (:categoryId IS NULL OR n.category.id = :categoryId)")
    List<Note> findByUserIdAndFilters(@Param("userId") Long userId,
                                      @Param("keyword") String keyword,
                                      @Param("categoryId") Long categoryId);

    int countByUser(User user);
    /**
     * Löscht alle Notizen eines bestimmten Benutzers.
     * @param userId Die ID des Benutzers, dessen Notizen gelöscht werden sollen.
     */
    @Transactional
    void deleteByUserId(Long userId);

}
