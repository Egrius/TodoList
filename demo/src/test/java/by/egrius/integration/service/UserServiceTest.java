package by.egrius.integration.service;

import by.egrius.TestApplicationRunner;
import by.egrius.TodoListApplication;
import by.egrius.database.repository.UserRepository;
import by.egrius.dto.UserCreateEditDto;
import by.egrius.dto.UserReadDto;
import by.egrius.service.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;

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
class UserServiceTest {

    private final UserService userService;

    @Test
    void showAllTest() {
        List<UserReadDto> found = userService.findAll();

        assertNotNull(found);

        found.forEach(e -> System.out.println(
                "id: " + e.id() +
                        ", username: " + e.username() +
                        ", email: " + e.email())
        );
    }

    @Test
    void addUserTest() {
        UserCreateEditDto userToInsert = new UserCreateEditDto(
                "Mihail_",
                "JojoKoko@gmail.com",
                "{noop}12345");

        assertNotNull(userService.create(userToInsert));

        showAllTest();
    }

    @Test
    void findByUsernameTest() {
        String usernameToFind = "Mihail_";

        Optional<UserReadDto> foundUser = userService.findByUsername(usernameToFind);

        assertTrue(foundUser.isPresent());

        System.out.println("Found: " + foundUser.get());
    }

    @Test
    void findByIdTest() {
        Long id = 3L;

        Optional<UserReadDto> foundUser = userService.findById(id);

        assertTrue(foundUser.isPresent());

        System.out.println("Found: " + foundUser.get());
    }

    @Test
    void updateTest() {
        // Сначала создать пользователя
        UserCreateEditDto dto = new UserCreateEditDto("Test", "test@example.com", "{noop}123");
        UserReadDto createdUser = userService.create(dto);

        assertNotNull(createdUser);

        Long id = createdUser.id();

        UserCreateEditDto toUpdate = new UserCreateEditDto("Kirill", "test@example.com", "{noop}456");
        UserReadDto updated = userService.update(id, toUpdate);

        assertNotNull(updated);

        assertEquals("Kirill", updated.username());
    }

    @Test
    void deleteTest() {
        Long id = 1L;

        assertTrue(userService.delete(id));

        showAllTest();
    }
}