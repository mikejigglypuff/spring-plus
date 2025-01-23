package org.example.expert.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.config.PasswordEncoder;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.enums.UserRole;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.example.expert.domain.common.util.StringUtil.generateRandomString;

// 더 빠른 Bulk Insert를 수행하기 위해 JDBC 도입
@Service
@RequiredArgsConstructor
public class UserBulkInsertService {
    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder encoder;

    public void batchInsert(long repeats) {
        jdbcTemplate.batchUpdate(
            "INSERT INTO users (email, password, user_role, nickname, created_at, modified_at) " +
                "VALUES (?, ?, ?, ?, ?, ?)",
            createUserList(repeats),
            1000,
            (ps, entity) -> {
                ps.setString(1, entity.getEmail());
                ps.setString(2, entity.getPassword());
                ps.setString(3, entity.getUserRole().getRole());
                ps.setString(4, entity.getNickname());
                ps.setTimestamp(5, Timestamp.valueOf(entity.getCreatedAt()));
                ps.setTimestamp(6, Timestamp.valueOf(entity.getModifiedAt()));
            }
        );
    }

    private List<User> createUserList(long repeats) {
        List<User> users = new ArrayList<>();
        for(int i = 0; i < repeats; i++) {
            LocalDateTime dateTime = LocalDateTime.now();
            User user = new User(
                generateRandomString(8),
                generateRandomString(6) + "@" + generateRandomString(6) + ".com",
                encoder.encode(generateRandomString(10)),
                UserRole.ROLE_USER
            );
            user.setCreatedAt(dateTime);
            user.setModifiedAt(dateTime);

            users.add(user);
        }
        return users;
    }
}