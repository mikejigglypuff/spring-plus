package org.example.expert.domain.user.service

import org.example.expert.config.PasswordEncoder
import org.example.expert.domain.common.util.StringUtil
import org.example.expert.domain.user.entity.User
import org.example.expert.domain.user.enums.UserRole
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import java.sql.PreparedStatement
import java.sql.Timestamp
import java.time.LocalDateTime
import kotlin.math.min

// 더 빠른 Bulk Insert를 수행하기 위해 JDBC 도입
@Service
class UserBulkInsertService(
    private val jdbcTemplate: JdbcTemplate,
    private val encoder: PasswordEncoder
) {

    fun batchInsert(repeats: Int, batchSize: Int) {
        val users = createUserList(repeats)

        var i = 0
        while (i < repeats) {
            val batchList = users.subList(i, min(repeats.toDouble(), (i + batchSize).toDouble()).toInt())
            jdbcTemplate.batchUpdate(
                "INSERT INTO users (email, password, user_role, nickname, created_at, modified_at) " +
                        "VALUES (?, ?, ?, ?, ?, ?)",
                batchList,
                batchSize
            ) { ps: PreparedStatement, entity: User ->
                ps.setString(1, entity.getEmail())
                ps.setString(2, entity.getPassword())
                ps.setString(3, entity.getUserRole().role)
                ps.setString(4, entity.getNickname())
                ps.setTimestamp(5, Timestamp.valueOf(entity.getCreatedAt()))
                ps.setTimestamp(6, Timestamp.valueOf(entity.getModifiedAt()))
            }

            i += batchSize
        }
    }

    private fun createUserList(repeats: Int): List<User> {
        val users: MutableList<User> = ArrayList()
        for (i in 0..<repeats) {
            val dateTime = LocalDateTime.now()
            val user = User(
                StringUtil.generateRandomString(10),
                StringUtil.generateRandomString(6) + "@" + StringUtil.generateRandomString(6) + ".com",
                encoder.encode(StringUtil.generateRandomString(10)),
                UserRole.ROLE_USER
            )
            user.setCreatedAt(dateTime)
            user.setModifiedAt(dateTime)

            users.add(user)
        }
        return users
    }
}