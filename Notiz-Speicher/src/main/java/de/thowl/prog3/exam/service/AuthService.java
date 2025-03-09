package de.thowl.prog3.exam.service;

import de.thowl.prog3.exam.storage.entities.User;
import de.thowl.prog3.exam.storage.repositories.UserRepository;
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

}
