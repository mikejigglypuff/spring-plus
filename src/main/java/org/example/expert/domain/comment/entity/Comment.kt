package org.example.expert.domain.comment.entity

import jakarta.persistence.*
import lombok.Getter
import lombok.NoArgsConstructor
import org.example.expert.domain.common.entity.Timestamped
import org.example.expert.domain.todo.entity.Todo
import org.example.expert.domain.user.entity.User

@Entity
@NoArgsConstructor
@Table(name = "comments")
class Comment(
    private var contents: String,

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private var user: User, @JoinColumn(name = "todo_id", nullable = false)

    @ManyToOne(fetch = FetchType.LAZY)
    private var todo: Todo
) : Timestamped() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null

    fun getContents() : String { return contents }
    fun getId() : Long? { return id }
    fun getUser() : User { return user }
    fun getTodo() : Todo { return todo }
}