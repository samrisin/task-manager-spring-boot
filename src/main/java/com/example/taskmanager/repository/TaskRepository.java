package com.example.taskmanager.repository;

import com.example.taskmanager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
  List<Task> findByStatus(String status);

    // Find tasks by title
    List<Task> findByTitle(String title);

    // Find tasks whose title contains a keyword
    List<Task> findByTitleContaining(String keyword);

    // Find tasks by status, ordered by title
    List<Task> findByStatusOrderByTitleAsc(String status);

    @Query(value = "SELECT * FROM task WHERE status = :status",
       nativeQuery = true)
    List<Task> findTasksUsingNativeSql(@Param("status") String status);

}