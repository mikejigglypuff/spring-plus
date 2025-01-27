package org.example.expert.domain.user.entity

import jakarta.persistence.*
import lombok.Getter
import lombok.NoArgsConstructor
import org.example.expert.domain.common.dto.AuthUser
import org.example.expert.domain.common.entity.Timestamped
import org.example.expert.domain.user.enums.UserRole

@Getter
@Entity
@NoArgsConstructor
@Table(name = "users")
class User : Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Long? = null
    private final var nickname: String

    @Column(unique = true)
    private final var email: String
    private var password: String? = null

    @Enumerated(EnumType.STRING)
    private final var userRole: UserRole
    private var profileUrl: String? = null

    constructor(nickname: String, email: String, password: String?, userRole: UserRole) {
        this.nickname = nickname
        this.email = email
        this.password = password
        this.userRole = userRole
    }

    private constructor(id: Long, nickname: String, email: String, userRole: UserRole) {
        this.id = id
        this.nickname = nickname
        this.email = email
        this.userRole = userRole
    }

    fun getId() : Long? { return id }
    fun getNickname() : String { return nickname }
    fun getEmail() : String { return email }
    fun getPassword() : String? { return password }
    fun getUserRole() : UserRole { return userRole }
    fun getProfileUrl() : String? { return profileUrl }

    fun addProfile(profileUrl: String?) {
        this.profileUrl = profileUrl
    }

    fun changePassword(password: String?) {
        this.password = password
    }

    fun updateRole(userRole: UserRole) {
        this.userRole = userRole
    }

    companion object {
        @JvmStatic
        fun fromAuthUser(authUser: AuthUser): User {
            return User(authUser.id, authUser.nickname, authUser.email, authUser.userRole)
        }
    }
}