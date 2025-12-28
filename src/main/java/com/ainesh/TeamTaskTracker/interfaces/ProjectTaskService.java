package com.ainesh.TeamTaskTracker.interfaces;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.ainesh.TeamTaskTracker.dto.ProjectStatsResponse;
import com.ainesh.TeamTaskTracker.dto.TaskCreationRequestDTO;
import com.ainesh.TeamTaskTracker.dto.TaskResponseDTO;
import com.ainesh.TeamTaskTracker.dto.TaskUpdationRequestDTO;
import com.ainesh.TeamTaskTracker.dto.stats.StatActivityDTO;
import com.ainesh.TeamTaskTracker.dto.stats.StatCountDTO;
import com.ainesh.TeamTaskTracker.dto.stats.StatProgressDTO;
import com.ainesh.TeamTaskTracker.dto.stats.StatSummaryDTO;
import com.ainesh.TeamTaskTracker.enums.TaskStatusEnum;

public interface ProjectTaskService {
  // core relationship endpoints
  public Page<TaskResponseDTO> getTasksWithinProject(Long projectId, TaskStatusEnum taskStatus, Pageable pageable);
  public TaskResponseDTO addTaskWithinProject(Long projectId, TaskCreationRequestDTO taskCreationRequestDTO);
  public TaskResponseDTO seeTaskWithinProject(Long projectId, Long taskId);
  public TaskResponseDTO updateTaskWithinProject(Long projectId, Long taskId, TaskUpdationRequestDTO taskUpdationRequestDTO);
  public void deleteTaskWithinProject(Long projectId, Long taskId);

  // statistics and summary endpoints
  public ProjectStatsResponse<StatCountDTO> taskCountWithinProject(Long projectId, TaskStatusEnum taskStatus);
  public ProjectStatsResponse<StatSummaryDTO> taskSummary(Long projectId);
  public ProjectStatsResponse<StatProgressDTO> projectProgress(Long projectId);
  public ProjectStatsResponse<StatActivityDTO> projectActivity(Long projectId, int days);
  public ProjectStatsResponse<List<TaskResponseDTO>> getStaleTaskList(Long projectId, int limit);

  /* TO-DO later:
  1. lifecycle analytics endpoint
  2. bottleneck detection endpoint */

  // bulk operations' endpoints
  public void deleteAllTasksInProject(Long projectId);
  public List<TaskResponseDTO> updateStatusOfAllTasksInProject(Long projectId, TaskStatusEnum newStatus);

}
