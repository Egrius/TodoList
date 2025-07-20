package by.egrius.integration.repository;

import by.egrius.TestApplicationRunner;
import by.egrius.TodoListApplication;
import by.egrius.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@RequiredArgsConstructor
@SpringBootTest(classes = {
        TestApplicationRunner.class,
        TodoListApplication.class
})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Transactional
class UserRepositoryTest {

    private final UserRepository userRepository;


}