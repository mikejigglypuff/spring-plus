package org.example.expert.domain.todo.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.example.expert.domain.todo.dto.response.TodoSearchResponse;
import org.example.expert.domain.todo.entity.QTodo;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class QueryDslTodoRepository{
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

    public List<TodoSearchResponse> findByTitleAndCreatedAtAndNickname(
        String title, LocalDateTime startCreationTime, LocalDateTime endCreationTime, String nickname
    ) {
        QTodo todo = QTodo.todo;
        return queryFactory
            .select(Projections.constructor(TodoSearchResponse.class,
                todo.title,
                todo.comments.size(),
                todo.managers.size()
            )).from(todo)
            .leftJoin(todo.comments)
            .leftJoin(todo.managers)
            .where(createDynamicBuilder(todo, title, startCreationTime, endCreationTime, nickname))
            .fetch();
    }

    // 빌드 패턴 적용 고민해볼 것
    // BooleanBuilder는 mutable하므로 정적 팩토리 메서드를 적용해도 메모리상의 이점을 가지기 힘듬
    private BooleanBuilder createDynamicBuilder(
        QTodo todo, String title, LocalDateTime startCreationTime, LocalDateTime endCreationTime, String nickname
    ) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if(title != null && !title.isEmpty()) { booleanBuilder.and(todo.title.contains(title)); }
        if(startCreationTime != null) { booleanBuilder.and(todo.createdAt.after(startCreationTime)); }
        if(endCreationTime != null) { booleanBuilder.and(todo.createdAt.before(endCreationTime)); }
        if(nickname != null && !nickname.isEmpty()) { booleanBuilder.and(todo.user.nickname.contains(nickname)); }

        return booleanBuilder;
    }
}
