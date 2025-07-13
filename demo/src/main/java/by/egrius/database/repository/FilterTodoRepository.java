package by.egrius.database.repository;

import by.egrius.database.entity.Todo;
import by.egrius.dto.filter.TodoFilter;

import java.util.List;

public interface FilterTodoRepository {
    List<Todo> findAllByFilter(TodoFilter filter);
}
