package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    @EntityGraph(attributePaths = "user")
    @Query(""" 
    SELECT t FROM Todo t
    WHERE (:weather IS NULL OR t.weather = :weather)
      AND (:startDate IS NULL OR t.modifiedAt >= :startDate)
      AND (:endDate IS NULL OR t.modifiedAt <= :endDate)
    ORDER BY t.modifiedAt DESC
    """)
    Page<Todo> findAllByOrderByModifiedAtDesc(
        @Param("weather") String weather,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate,
        Pageable pageable);

    @Query("SELECT t FROM Todo t " +
            "LEFT JOIN t.user " +
            "WHERE t.id = :todoId")
    Optional<Todo> findByIdWithUser(@Param("todoId") Long todoId);
}
