package by.egrius;

import by.egrius.database.repository.UserRepository;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;


@TestConfiguration
@AllArgsConstructor
public class TestApplicationRunner {
    @Autowired
    private UserRepository userRepository;
}