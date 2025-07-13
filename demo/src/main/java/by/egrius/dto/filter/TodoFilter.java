package by.egrius.dto.filter;

import by.egrius.database.entity.TodoPriority;
import by.egrius.database.entity.TodoStatus;
import by.egrius.database.entity.User;

import java.time.LocalDateTime;

public record TodoFilter (LocalDateTime createdAt,
                          LocalDateTime finishedAt,
                          TodoStatus status,
                          TodoPriority priority,
                          LocalDateTime deadline,
                          String title) {

}
