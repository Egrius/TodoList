package by.egrius.integration.repository;

import by.egrius.TestApplicationRunner;
import by.egrius.TodoListApplication;
import by.egrius.database.entity.User;
import by.egrius.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@RequiredArgsConstructor
@SpringBootTest(classes = {
        TestApplicationRunner.class,
        TodoListApplication.class
})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class UserRepositoryTest {

    private final UserRepository userRepository;

    @Test
    void showAllTest() {
        List<User> found = userRepository.findAll();

        assertNotNull(found);

        found.forEach(e -> System.out.println(
                "id: " + e.getId() +
                ", username: " + e.getUsername() +
                ", email: " + e.getEmail() +
                ", password: " + e.getPassword())
        );
    }

    @Test
    void addUserTest() {
        User userToAdd = new User(
                null,
                "Antonio",
                "antonMoscow@gmail.com",
                "{noop}12345",
                Collections.emptyList()
        );

     userRepository.save(userToAdd);
    }

    @Test
    void findByUsername() {
        final String username = "Antonio";

        Optional<User> found = userRepository.findByUsername(username);

        assertNotNull(found);
        assertEquals(username, found.get().getUsername());
    }
}