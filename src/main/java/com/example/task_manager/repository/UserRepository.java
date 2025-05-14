package com.example.task_manager.repository;

import com.example.task_manager.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    @Query("""
            SELECT u FROM User u
            WHERE (:firstName IS NULL OR LOWER(u.firstName) LIKE %:firstName%)
              AND (:lastName IS NULL OR LOWER(u.lastName) LIKE %:lastName%)
              AND (:email IS NULL OR LOWER(u.email) LIKE %:email%)
            """)
    Page<User> findByFilter(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("email") String email,
            Pageable pageable);
}


