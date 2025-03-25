package de.thowl.prog3.exam.service;

import de.thowl.prog3.exam.storage.entities.User;
import de.thowl.prog3.exam.storage.entities.Note;
import de.thowl.prog3.exam.storage.repositories.CategoryRepository;
import de.thowl.prog3.exam.storage.repositories.UserRepository;
import de.thowl.prog3.exam.storage.repositories.NoteRepository;
import de.thowl.prog3.exam.web.api.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired private NoteRepository noteRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean register(String username, String password, String email) {
        if (userRepository.findUserByEmail(email).isPresent()) {
            return false;
        }
        try {
            User user = new User();
            user.setName(username);
            user.setEmail(email);
            // Verschlüsseln des Passworts mit BCrypt
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Fehler bei der Registrierung", e);
        }
    }

    /**
     * Holt den Benutzer anhand der E-Mail-Adresse.
     * @param email Die E-Mail-Adresse des Benutzers.
     * @return Optional<User> Der Benutzer, falls er existiert.
     */
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public void saveNote(Note note) {
        if (note == null) {
            throw new IllegalArgumentException("Note cannot be null");
        }
        noteRepository.save(note); // Speichert die Notiz (INSERT oder UPDATE)
    }

    /**
     * Ändert das Passwort eines Benutzers, falls das aktuelle Passwort korrekt ist.
     * @param userId Die ID des Benutzers.
     * @param currentPassword Das aktuelle Passwort zur Verifizierung.
     * @param newPassword Das neue Passwort, das gesetzt werden soll.
     * @return true, wenn das Passwort erfolgreich geändert wurde; false, wenn das aktuelle Passwort falsch war.
     */
    public boolean changePassword(Long userId, String currentPassword, String newPassword) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            log.warn("Benutzer mit ID {} nicht gefunden", userId);
            return false;
        }

        User user = optionalUser.get();

        // Überprüfen, ob das aktuelle Passwort korrekt ist
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            log.warn("Falsches aktuelles Passwort für Benutzer {}", user.getEmail());
            return false;
        }

        // Neues Passwort setzen und speichern
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        log.info("Passwort erfolgreich geändert für Benutzer: {}", user.getEmail());
        return true;
    }

    /**
     * Löscht einen Benutzer aus der Datenbank.
     * @param userId Die ID des zu löschenden Benutzers.
     * @return true, wenn der Benutzer erfolgreich gelöscht wurde, ansonsten false.
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
     * @param user new User Credentials
     */
    public void updateUser(User user) {
        userRepository.save(user);
    }
}
