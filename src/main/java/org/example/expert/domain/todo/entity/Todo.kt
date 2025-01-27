package org.example.expert.domain.todo.entity

import jakarta.persistence.*
import lombok.NoArgsConstructor
import org.example.expert.domain.comment.entity.Comment
import org.example.expert.domain.common.entity.Timestamped
import org.example.expert.domain.manager.entity.Manager
import org.example.expert.domain.user.entity.User

@Entity
@NoArgsConstructor
@Table(name = "todos")
class Todo(
    private val title: String,
    private val contents: String,
    private val weather: String,

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private val user: User
) : Timestamped() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null

    @OneToMany(mappedBy = "todo", cascade = [CascadeType.REMOVE])
    private val comments: List<Comment> = ArrayList<Comment>()

    @OneToMany(mappedBy = "todo", cascade = [CascadeType.PERSIST])
    private val managers: MutableList<Manager> = ArrayList()

    init {
        managers.add(Manager(user, this))
    }

    fun getTitle() : String { return title }
    fun getContents() : String { return contents }
    fun getWeather() : String { return weather }
    fun getUser() : User { return user }
    fun getId() : Long? { return id }
    fun getComments() : List<Comment> { return comments }
    fun getManagers() : MutableList<Manager> { return managers}
}