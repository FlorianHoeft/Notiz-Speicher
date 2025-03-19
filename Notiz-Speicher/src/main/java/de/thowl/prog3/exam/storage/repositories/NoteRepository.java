package de.thowl.prog3.exam.storage.repositories;

import java.util.Optional;

import de.thowl.prog3.exam.storage.entities.Note;

import de.thowl.prog3.exam.storage.entities.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface NoteRepository extends JpaRepository<Note, Long> {

    public Optional<Note> findNoteById(long id);

    public Optional<Note> findNoteByTitle(String title);

    public Optional<Note> findNoteByContent(String content);

    public List<Note> findNoteByFavorite(boolean favorite);

    public List<Note> findNoteByFavoriteAndUserId(boolean favorite, long userId);

    public List<Note> findByUserId(Long userId);

    @Query("SELECT n FROM Note n WHERE n.user.id = :userId " +
                  "AND (:keyword IS NULL OR LOWER(n.content) LIKE LOWER(CONCAT('%', :keyword, '%'))OR :keyword IS NULL OR LOWER(n.title) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
                     "AND (:categoryId IS NULL OR n.category.id = :categoryId)")
    List<Note> findByUserIdAndFilters(@Param("userId") Long userId,
                                      @Param("keyword") String keyword,
                                      @Param("categoryId") Long categoryId);



    @Query("SELECT n FROM Note n WHERE n.user.id = :userId")
    List<Note> findNotesByUserId(@Param("userId") Long userId);

    int countByUser(User user);
}
