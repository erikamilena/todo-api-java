package com.projects.ToDo.infrastructure.persistence.repository;

import com.projects.ToDo.infrastructure.persistence.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SpringDataTaskRepository extends JpaRepository<TaskEntity, Long> {

    @Query("SELECT t FROM TaskEntity t WHERE t.category.name = :nameCategory AND t.state = 'PENDING'")
    List<TaskEntity> findPendingByCategory(@Param("nameCategory") String nameCategory);
    @Query("SELECT t FROM TaskEntity t WHERE t.createdAt BETWEEN :start AND :end")
    List<TaskEntity> findByDateRange(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}

