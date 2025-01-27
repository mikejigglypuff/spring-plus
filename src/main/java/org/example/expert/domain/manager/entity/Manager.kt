package org.example.expert.domain.manager.entity

import jakarta.persistence.*
import lombok.NoArgsConstructor
import org.example.expert.domain.todo.entity.Todo
import org.example.expert.domain.user.entity.User

@Entity
@NoArgsConstructor
@Table(name = "managers")
class Manager(// 일정 만든 사람 id
    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private var user: User, // 일정 id

    @JoinColumn(name = "todo_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private var todo: Todo
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null

    fun getId(): Long? {
        return id
    }

    fun getUser(): User {
        return user
    }

    fun getTodo(): Todo {
        return todo
    }
}