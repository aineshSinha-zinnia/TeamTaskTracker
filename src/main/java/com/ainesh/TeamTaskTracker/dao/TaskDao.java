package com.ainesh.TeamTaskTracker.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
  public Page<Task> findByProjectId(Long projectId, Pageable pageable);
  public List<Task> findByProjectId(Long projectId);
  public Page<Task> findByProjectIdAndStatus(Long projectId, TaskStatusEnum status, Pageable pageable);
  public List<Task> findByProjectIdAndStatus(Long projectId, TaskStatusEnum status);
  public Optional<Task> findByIdAndProjectId(Long taskId, Long projectId);
  public int countByProjectIdAndUpdatedAtAfter(Long projectId, LocalDateTime cutoff);
  public int countByProjectIdAndCreatedAtAfter(Long projectId, LocalDateTime cutoff);
  public int countByProjectIdAndCompletedAtAfter(Long projectId, LocalDateTime cutoff);

  public Page<Task> findByProjectIdAndStatusNot(Long projectId, TaskStatusEnum status, Pageable pageable);

  public void deleteByProjectId(Long projectId);

}
