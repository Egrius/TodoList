package by.egrius.mapper;

import by.egrius.database.entity.User;
import by.egrius.dto.UserCreateEditDto;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class UserCreateEditMapper implements Mapper<UserCreateEditDto, User>{

    @Override
    public User map(UserCreateEditDto fromObject, User toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public User map(UserCreateEditDto object) {
        User user = new User();
        copy(object, user);
        return user;
    }

    private void copy(UserCreateEditDto object, User user) {
        user.setUsername(object.getUsername());
        user.setEmail(object.getEmail());
        user.setPassword(object.getPassword());
        user.setUserTodos(Collections.emptyList());
    }
}
