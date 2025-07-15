package by.egrius.integration.repository;

import by.egrius.TestApplicationRunner;
import by.egrius.database.repository.UserRepository;
import com.sun.tools.javac.Main;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@RequiredArgsConstructor
@SpringBootTest(classes = {
        TestApplicationRunner.class,
        Main.class
})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Transactional
class UserRepositoryTest {

    private final UserRepository userRepository;

    @Test
    void test() {

    }
}