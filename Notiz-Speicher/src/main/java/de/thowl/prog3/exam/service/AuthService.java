package de.thowl.prog3.exam.service;

import de.thowl.prog3.exam.storage.entities.Note;
import de.thowl.prog3.exam.storage.entities.User;
import de.thowl.prog3.exam.storage.repositories.CategoryRepository;
import de.thowl.prog3.exam.storage.repositories.NoteRepository;
import de.thowl.prog3.exam.storage.repositories.UserRepository;
import de.thowl.prog3.exam.web.api.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * AuthService is Providing the Authentication for User
 */
@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Register new User
     *
     * @param username username
     * @param password password
     * @param email    email
     * @return
     */
    public boolean register(String username, String password, String email) {
        if (userRepository.findUserByEmail(email).isPresent()) {
            return false;
        }
        try {
            User user = new User();
            user.setName(username);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Fehler bei der Registrierung", e);
        }
    }

    /**
     * Finding user by Email
     *
     * @param email email from user
     * @return Optional<User> User if he exists
     */
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    /**
     * Save Note
     *
     * @param note new note to save
     */
    public void saveNote(Note note) {
        if (note == null) {
            throw new IllegalArgumentException("Note cannot be null");
        }
        noteRepository.save(note); // Saves the Note (INSERT or UPDATE)
    }

    /**
     * Changing the password if the old one is correct
     *
     * @param userId          ID from User
     * @param currentPassword Old Password to verify
     * @param newPassword     New Password
     * @return true, if old password is right
     */
    public boolean changePassword(Long userId, String currentPassword, String newPassword) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            log.warn("Benutzer mit ID {} nicht gefunden", userId);
            return false;
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            log.warn("Falsches aktuelles Passwort für Benutzer {}", user.getEmail());
            return false;
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        log.info("Passwort erfolgreich geändert für Benutzer: {}", user.getEmail());
        return true;
    }

    /**
     * Deletes User from Database in the correct way, so first the notes then categories and finally the user
     *
     * @param userId ID from user
     * @return true, if user is deleted correctly
     */
    public boolean deleteUser(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            log.warn("Benutzer mit ID {} nicht gefunden", userId);
            return false;
        }

        noteRepository.deleteByUserId(userId);
        categoryRepository.deleteByUserId(userId);
        userRepository.deleteById(userId);

        log.info("Benutzer mit ID {} wurde erfolgreich gelöscht", userId);
        return true;
    }

    /**
     * Update the User Credentials
     *
     * @param user new User Credentials
     */
    public void updateUser(User user) {
        userRepository.save(user);
    }
}
