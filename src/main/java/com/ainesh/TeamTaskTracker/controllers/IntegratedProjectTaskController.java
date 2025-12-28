package com.ainesh.TeamTaskTracker.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ainesh.TeamTaskTracker.dto.ProjectStatsResponse;
import com.ainesh.TeamTaskTracker.dto.TaskCreationRequestDTO;
import com.ainesh.TeamTaskTracker.dto.TaskResponseDTO;
import com.ainesh.TeamTaskTracker.dto.TaskUpdationRequestDTO;
import com.ainesh.TeamTaskTracker.dto.stats.StatActivityDTO;
import com.ainesh.TeamTaskTracker.dto.stats.StatCountDTO;
import com.ainesh.TeamTaskTracker.dto.stats.StatProgressDTO;
import com.ainesh.TeamTaskTracker.dto.stats.StatSummaryDTO;
import com.ainesh.TeamTaskTracker.enums.TaskStatusEnum;
import com.ainesh.TeamTaskTracker.interfaces.ProjectTaskService;

@RestController
public class IntegratedProjectTaskController {
  /* different controllers
  1. see all tasks within a project
  2. see main project of a particular task */

  private ProjectTaskService projectTaskService;

  @Autowired
  public IntegratedProjectTaskController(ProjectTaskService projectTaskService){
    this.projectTaskService = projectTaskService;
  }

  @GetMapping("/projects/{projectId}/tasks")
  public ResponseEntity<?> tasksWithinSingleProject(
    @PathVariable Long projectId, 
    @RequestParam(required = false) TaskStatusEnum taskStatusEnum,
    Pageable pageable
  ){

    Page<TaskResponseDTO> taskListPage = projectTaskService.getTasksWithinProject(projectId, taskStatusEnum, pageable);

    return new ResponseEntity<>(taskListPage, HttpStatus.OK);
  }

  @PostMapping("/projects/{projectId}/task")
  public ResponseEntity<?> addTaskWithinProject(
    @PathVariable Long projectId,
    @RequestBody TaskCreationRequestDTO taskCreationRequestDTO
  ){

    TaskResponseDTO taskResponseDTO = projectTaskService.addTaskWithinProject(projectId, taskCreationRequestDTO);

    return ResponseEntity.ok(taskResponseDTO);

  }

  @GetMapping("/project/{projectId}/task/{taskId}")
  public ResponseEntity<?> seeTaskWithinProject(
    @PathVariable Long projectId,
    @PathVariable Long taskId
  ){
    TaskResponseDTO taskResponseDTO = projectTaskService.seeTaskWithinProject(projectId, taskId);
    return ResponseEntity.ok(taskResponseDTO);
  }

  @PutMapping("/project/{projectId}/task/{taskId}")
  public ResponseEntity<?> updateTaskWithinProject(
    @PathVariable Long projectId,
    @PathVariable Long taskId,
    @RequestBody TaskUpdationRequestDTO taskUpdationRequestDTO
  ){
    return ResponseEntity.ok(
      projectTaskService.updateTaskWithinProject(projectId, taskId, taskUpdationRequestDTO)
    );
  }

  @DeleteMapping("/project/{projectId}/task/{taskId}")
  public ResponseEntity<?> deleteTaskWithinProject(
    @PathVariable Long projectId,
    @PathVariable Long taskId
  ){
    projectTaskService.deleteTaskWithinProject(projectId, taskId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping("/project/{projectId}/stats/count")
  public ResponseEntity<?> getTaskCountWithinProject(
    @PathVariable Long projectId,
    @RequestParam TaskStatusEnum status
  ){

    ProjectStatsResponse<StatCountDTO> projectStatsResponse = projectTaskService.taskCountWithinProject(projectId, status);

    return ResponseEntity.ok(projectStatsResponse);

  }

  @GetMapping("/project/{projectId}/stats/summary")
  public ResponseEntity<?> getTaskSummary(
    @PathVariable Long projectId
  ){
    ProjectStatsResponse<StatSummaryDTO> projectStatsResponse = projectTaskService.taskSummary(projectId);

    return ResponseEntity.ok(projectStatsResponse);
  }

  @GetMapping("/project/{projectId}/stats/progress")
  public ResponseEntity<?> getProjectProgress(
    @PathVariable Long projectId
  ){
    ProjectStatsResponse<StatProgressDTO> projectStatsResponse = projectTaskService.projectProgress(projectId);
    return ResponseEntity.ok(projectStatsResponse);
  }

  @GetMapping("/project/{projectId}/stats/activity")
  public ResponseEntity<?> getProjectActivity(
    @PathVariable Long projectId,
    @RequestParam int days
  ){
    ProjectStatsResponse<StatActivityDTO> projectStatsResponse = projectTaskService.projectActivity(projectId, days);
    return ResponseEntity.ok(projectStatsResponse);
  }

  @GetMapping("/project/{projectId}/stats/stale")
  public ResponseEntity<?> staleTasksList(
    @PathVariable Long projectId,
    @RequestParam int limit
  ){
    ProjectStatsResponse<List<TaskResponseDTO>> projectStatsResponse = projectTaskService.getStaleTaskList(projectId, limit);
    return ResponseEntity.ok(projectStatsResponse);
  }

  @DeleteMapping("/project/{projectId}/tasks")
  public ResponseEntity<?> deleteAllTasksInProject(
    @PathVariable Long projectId
  ){
    projectTaskService.deleteAllTasksInProject(projectId);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/project/{projectId}/tasks")
  public ResponseEntity<?> bulkTaskUpdate(
    @PathVariable Long projectId,
    @RequestParam TaskStatusEnum taskStatusEnum
  ){
    List<TaskResponseDTO> taskResponseDTOs = projectTaskService.updateStatusOfAllTasksInProject(projectId, taskStatusEnum);
    return ResponseEntity.ok(taskResponseDTOs);
  }

}
