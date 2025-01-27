package org.example.expert.domain.todo.service

import org.example.expert.client.WeatherClient
import org.example.expert.domain.common.dto.AuthUser
import org.example.expert.domain.common.exception.InvalidRequestException
import org.example.expert.domain.todo.dto.request.TodoSaveRequest
import org.example.expert.domain.todo.dto.response.TodoResponse
import org.example.expert.domain.todo.dto.response.TodoSaveResponse
import org.example.expert.domain.todo.dto.response.TodoSearchResponse
import org.example.expert.domain.todo.entity.Todo
import org.example.expert.domain.todo.repository.QueryDslTodoRepository
import org.example.expert.domain.todo.repository.TodoRepository
import org.example.expert.domain.user.dto.response.UserResponse
import org.example.expert.domain.user.entity.User.Companion.fromAuthUser
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class TodoService(
    private val todoRepository: TodoRepository,
    private val weatherClient: WeatherClient,
    private val queryDslTodoRepository: QueryDslTodoRepository
) {

    @Transactional
    fun saveTodo(authUser: AuthUser, todoSaveRequest: TodoSaveRequest): TodoSaveResponse {
        val user = fromAuthUser(authUser)

        val weather = weatherClient.todayWeather

        val newTodo = Todo(
            todoSaveRequest.title,
            todoSaveRequest.contents,
            weather,
            user
        )
        val savedTodo = todoRepository.save(newTodo)

        return TodoSaveResponse(
            savedTodo.getId(),
            savedTodo.getTitle(),
            savedTodo.getContents(),
            weather,
            UserResponse(user.getId(), user.getEmail())
        )
    }

    @Transactional(readOnly = true)
    fun getTodos(
        page: Int, size: Int, weather: String, startModifiedTime: LocalDateTime?, endModifiedTime: LocalDateTime?
    ): Page<TodoResponse> {
        val pageable: Pageable = PageRequest.of(page - 1, size)

        val todos = todoRepository.findAllByWeatherAndModifiedAt(
            pageable, weather, startModifiedTime, endModifiedTime
        )

        return todos.map { todo: Todo ->
            TodoResponse(
                todo.getId(),
                todo.getTitle(),
                todo.getContents(),
                todo.getWeather(),
                UserResponse(todo.getUser().getId(), todo.getUser().getEmail()),
                todo.getCreatedAt(),
                todo.getModifiedAt()
            )
        }
    }

    @Transactional(readOnly = true)
    fun getTodo(todoId: Long): TodoResponse {
        val todo = queryDslTodoRepository.findByIdWithUser(todoId)
            .orElseThrow { InvalidRequestException("Todo not found") }

        val user = todo.getUser()

        return TodoResponse(
            todo.getId(),
            todo.getTitle(),
            todo.getContents(),
            todo.getWeather(),
            UserResponse(user.getId(), user.getEmail()),
            todo.getCreatedAt(),
            todo.getModifiedAt()
        )
    }

    @Transactional(readOnly = true)
    fun searchTodo(
        title: String?, startCreationTime: LocalDateTime?, endCreationTime: LocalDateTime?, nickname: String?
    ): List<TodoSearchResponse> {
        return queryDslTodoRepository.findByTitleAndCreatedAtAndNickname(
            title, startCreationTime, endCreationTime, nickname
        )
    }
}