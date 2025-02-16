package de.thowl.prog3.exam.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import de.thowl.prog3.exam.storage.entities.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class TestUserService {

    private final String ADMIN_USER_NAME = "admin";

    @Autowired
    private UserService svc;

    @Test
    @DisplayName("Should not throw an exception")
    public void testGetUser() {
        log.debug("entering testGetUser");
        assertDoesNotThrow(() -> {
            this.svc.getUser(ADMIN_USER_NAME);
        });
    }

    @Test
    @DisplayName("Should not return an empty user list")
    public void testGetAllUsers() {
        log.debug("entering testGetUser");
        List<User> allUsers = this.svc.getAllUsers();
        assertNotNull(allUsers, "List of all users should not be a null reference");
        assertFalse(allUsers.size() == 0, "List of all users should not be empty");
    }
}
