package de.thowl.prog3.exam.service;

import de.thowl.prog3.exam.storage.entities.User;
import de.thowl.prog3.exam.storage.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private static de.thowl.prog3.exam.service.Encryptor Encryptor;

    private final UserRepository userRepository;
    @Autowired
    public AuthService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    public boolean login(String email, String password) {
        User user = userRepository.findUserByEmail(email).orElse(null);
        if (user == null) {
            return false;
        }
        try {
            return Encryptor.verifyPassword(password, user.getPassword());
        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Login-Vorgang", e);
        }
    }

    public boolean register(String username, String password, String email) {
        if (userRepository.findUserByEmail(email).isPresent()) {
            return false;
        }
        try {
            User user = new User();
            user.setName(username);
            user.setEmail(email);
            user.setPassword(Encryptor.hashPassword(password));
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Fehler bei der Registrierung", e);
        }
    }
}
