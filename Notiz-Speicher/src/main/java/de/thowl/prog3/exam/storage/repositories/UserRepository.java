package de.thowl.prog3.exam.storage.repositories;

import de.thowl.prog3.exam.storage.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Repository interface for accessing and managing User entities in the database
 * Extends JpaRepository to provide basic JPA operations and custom query methods
 */
public interface UserRepository extends CrudRepository<User, Long> {

    public Optional<User> findUserById(long id);

    public Optional<User> findUserByName(String username);

    public Optional<User> findUserByEmail(String email);
}
