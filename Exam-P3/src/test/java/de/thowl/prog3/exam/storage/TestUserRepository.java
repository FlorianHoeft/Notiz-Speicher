package de.thowl.prog3.exam.storage;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import de.thowl.prog3.exam.storage.entities.User;
import de.thowl.prog3.exam.storage.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DataJpaTest
public class TestUserRepository {

    // have a look to data.sql
    // Admin User: u=admin, id=3
    private static final int ADMIN_USER = 3;
    // Normal User: u=thowl, id=1
    private static final int UNPRIV_USER = 2;

    @Autowired
    private UserRepository repository;

    @Test
    public void testGetUserByID() {
        log.info("Starting testGetUserByID");

        Optional<User> u = this.repository.findUserById(ADMIN_USER);
        assertTrue(u.isPresent(), "Unexpected empty result");
        log.debug("Got user {}", u.get());
        assertTrue(u.get().getName().equals("admin"), "Username is wrong");
    }

    @Test
    public void testGetUserByName() {
        log.info("Starting testGetUserByName");

        Optional<User> u = this.repository.findUserByName("admin");
        assertTrue(u.isPresent(), "Unexpected empty result");
        log.debug("Got user {}", u.get());
        assertTrue(u.get().getName().equals("admin"), "Username is wrong");
        assertTrue(u.get().getId() == ADMIN_USER, "Admin user has wrong ID");
    }

    @Test
    public void testStoreUser() {
        String username = "Testcase-User";
        User u = User.builder().name(username).email("test@prog3.unit-test.info").build();
        User savedusr = this.repository.save(u);

        assertTrue(savedusr.getId() != 0, "UID is wrong after saving the user");
        assertTrue(u == savedusr, "the saved object differs from our original user");
    }
}
