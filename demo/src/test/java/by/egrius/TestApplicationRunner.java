package by.egrius;

import by.egrius.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@TestConfiguration
@EnableJpaRepositories
public class TestApplicationRunner {

}