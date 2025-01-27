package org.example.expert.domain.todo.repository

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import org.example.expert.domain.todo.dto.response.TodoSearchResponse
import org.example.expert.domain.todo.entity.QTodo
import org.example.expert.domain.todo.entity.Todo
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
class QueryDslTodoRepository(em: EntityManager) {
    private val queryFactory = JPAQueryFactory(em)

    fun findByIdWithUser(todoId: Long): Optional<Todo> {
        val todo = QTodo.todo
        return Optional.ofNullable(
            queryFactory.selectFrom(todo)
                .where(todo.id.eq(todoId))
                .innerJoin(todo.user).fetchJoin()
                .fetchOne()
        )
    }

    fun findByTitleAndCreatedAtAndNickname(
        title: String?, startCreationTime: LocalDateTime?, endCreationTime: LocalDateTime?, nickname: String?
    ): List<TodoSearchResponse> {
        val todo = QTodo.todo
        return queryFactory
            .select(
                Projections.constructor(
                    TodoSearchResponse::class.java,
                    todo.title,
                    todo.comments.size(),
                    todo.managers.size()
                )
            ).distinct()
            .from(todo)
            .leftJoin(todo.comments)
            .leftJoin(todo.managers)
            .where(createDynamicBuilder(todo, title, startCreationTime, endCreationTime, nickname))
            .fetch()
    }

    // 빌드 패턴 적용 고민해볼 것
    // BooleanBuilder는 mutable하므로 정적 팩토리 메서드를 적용해도 메모리상의 이점을 가지기 힘듬
    private fun createDynamicBuilder(
        todo: QTodo,
        title: String?,
        startCreationTime: LocalDateTime?,
        endCreationTime: LocalDateTime?,
        nickname: String?
    ): BooleanBuilder {
        val booleanBuilder = BooleanBuilder()
        if (!title.isNullOrEmpty()) {
            booleanBuilder.and(todo.title.contains(title))
        }
        if (startCreationTime != null) {
            booleanBuilder.and(todo.createdAt.after(startCreationTime))
        }
        if (endCreationTime != null) {
            booleanBuilder.and(todo.createdAt.before(endCreationTime))
        }
        if (!nickname.isNullOrEmpty()) {
            booleanBuilder.and(todo.user.nickname.contains(nickname))
        }

        return booleanBuilder
    }
}