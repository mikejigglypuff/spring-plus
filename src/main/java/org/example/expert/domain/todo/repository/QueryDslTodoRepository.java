package org.example.expert.domain.todo.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.example.expert.domain.todo.entity.QTodo;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class QueryDslTodoRepository {
    private final JPAQueryFactory queryFactory;

    public QueryDslTodoRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Optional<Todo> findByIdWithUser(Long todoId) {
        QTodo todo = QTodo.todo;
        return Optional.ofNullable(
            queryFactory.selectFrom(todo)
            .where(todo.id.eq(todoId))
            .innerJoin(todo.user).fetchJoin()
            .fetchOne());
    }
}
