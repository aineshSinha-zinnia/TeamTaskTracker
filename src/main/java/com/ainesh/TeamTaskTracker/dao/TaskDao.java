package com.ainesh.TeamTaskTracker.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.ainesh.TeamTaskTracker.enums.TaskStatusEnum;
import com.ainesh.TeamTaskTracker.models.Task;

@Repository
public interface TaskDao extends JpaRepository<Task, Long> {
  @Query("SELECT t FROM Task t WHERE " + "LOWER(t.title) LIKE(CONCAT('%', :title, '%'))")
  public Task searchTaskByTitle(String title);

  public Page<Task> findByStatus(TaskStatusEnum status, Pageable pageable);
}
