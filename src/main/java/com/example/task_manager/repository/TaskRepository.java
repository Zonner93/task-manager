package com.example.task_manager.repository;

import com.example.task_manager.constans.TaskStatus;
import com.example.task_manager.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t LEFT JOIN FETCH t.assignedUsers WHERE t.id = :id")
    Optional<Task> findTaskByIdWithUsers(Long id);

    @Query("""
                SELECT t FROM Task t
                WHERE (:title IS NULL OR LOWER(t.title) LIKE %:title%)
                  AND (:description IS NULL OR LOWER(t.description) LIKE %:description%)
                  AND (:status IS NULL OR t.status = :status)
                  AND (cast(:dueDateFrom as timestamp) IS NULL OR t.dueDate >= :dueDateFrom)
                  AND (cast(:dueDateTo as timestamp) IS NULL OR t.dueDate <= :dueDateTo)
            """)
    Page<Task> findByFilter(
            @Param("title") String title,
            @Param("description") String description,
            @Param("status") TaskStatus status,
            @Param("dueDateFrom") LocalDateTime dueDateFrom,
            @Param("dueDateTo") LocalDateTime dueDateTo,
            Pageable pageable
    );


}
