package org.example.expert.domain.user.repository;

import org.example.expert.domain.user.dto.response.UserResponse;
import org.example.expert.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    @Query("SELECT new org.example.expert.domain.user.dto.response.UserResponse(u.id, u.email) "
        + "FROM User u WHERE u.nickname = :nickname"
    )
    List<UserResponse> findAllByNickname(String nickname);
}
