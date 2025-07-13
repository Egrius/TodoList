package by.egrius.database.repository;

import static by.egrius.database.entity.QTodo.todo;
import by.egrius.database.entity.Todo;
import by.egrius.dto.filter.TodoFilter;
import by.egrius.dto.predicate.QPredicates;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FilterTodoRepositoryImpl implements FilterTodoRepository {
    private final EntityManager entityManager;

    @Override
    public List<Todo> findAllByFilter(TodoFilter filter) {
        Predicate predicate = QPredicates.builder()
                .add(filter.createdAt(), todo.createdAt::after)
                .add(filter.finishedAt(), todo.finishedAt::before)
                .add(filter.status(), todo.status::eq)
                .add(filter.priority(), todo.priority::eq)
                .add(filter.deadline(), todo.deadline::eq)
                .add(filter.title(), todo.title::containsIgnoreCase)
                .build();
        return new JPAQuery<Todo>(entityManager)
                .select(todo)
                .from(todo)
                .where(predicate)
                .fetch();
    }
}
