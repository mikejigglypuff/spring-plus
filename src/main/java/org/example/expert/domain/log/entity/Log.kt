package org.example.expert.domain.log.entity

import jakarta.persistence.*
import lombok.NoArgsConstructor
import org.example.expert.domain.common.entity.Timestamped
import org.example.expert.domain.manager.dto.request.ManagerSaveRequest

@Entity
@NoArgsConstructor
@Table(name = "log")
class Log(
    @Column
    private var requestBody: String,

    @Column
    private var errorMessage: String
) : Timestamped() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null

    companion object {
        @JvmStatic
        fun of(requestBody: ManagerSaveRequest, errorMessage: String): Log {
            return Log(requestBody.toString(), errorMessage)
        }
    }

    fun getId(): Long? {
        return id
    }

    fun getRequestBody(): String {
        return requestBody
    }

    fun getErrorMessage(): String {
        return errorMessage
    }
}