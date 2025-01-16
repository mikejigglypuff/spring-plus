package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    @Query("SELECT t FROM Todo t LEFT JOIN FETCH t.user u ORDER BY t.modifiedAt DESC")
    Page<Todo> findAllByOrderByModifiedAtDesc(Pageable pageable);

    @Query("SELECT t FROM Todo t " +
        "LEFT JOIN t.user " +
        "WHERE t.id = :todoId")
    Optional<Todo> findByIdWithUser(@Param("todoId") Long todoId);

    // t.weather = null인 경우는 WeatherClient에서 배제하므로 고려하지 않음
    @Query("SELECT t FROM Todo t " +
        "LEFT JOIN FETCH t.user " +
        "WHERE (t.weather LIKE %:weather%) AND " +
        "t.modifiedAt >= coalesce(:startModifiedTime, t.modifiedAt) AND " +
        "t.modifiedAt <= coalesce(:endModifiedTime, t.modifiedAt) " +
        "ORDER BY t.modifiedAt DESC"
    )
    Page<Todo> findAllByWeatherAndModifiedAt(
        Pageable pageable, String weather, LocalDateTime startModifiedTime, LocalDateTime endModifiedTime);
}