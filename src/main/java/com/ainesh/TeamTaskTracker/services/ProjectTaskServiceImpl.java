package com.ainesh.TeamTaskTracker.services;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.ainesh.TeamTaskTracker.dao.ProjectDao;
import com.ainesh.TeamTaskTracker.dao.TaskDao;
import com.ainesh.TeamTaskTracker.dto.ProjectStatsResponse;
import com.ainesh.TeamTaskTracker.dto.TaskCreationRequestDTO;
import com.ainesh.TeamTaskTracker.dto.TaskResponseDTO;
import com.ainesh.TeamTaskTracker.dto.TaskUpdationRequestDTO;
import com.ainesh.TeamTaskTracker.dto.stats.StatActivityDTO;
import com.ainesh.TeamTaskTracker.dto.stats.StatCountDTO;
import com.ainesh.TeamTaskTracker.dto.stats.StatProgressDTO;
import com.ainesh.TeamTaskTracker.dto.stats.StatSummaryDTO;
import com.ainesh.TeamTaskTracker.dtoMapper.ProjectResponseMapper;
import com.ainesh.TeamTaskTracker.dtoMapper.TaskCreationMapper;
import com.ainesh.TeamTaskTracker.dtoMapper.TaskResponseMapper;
import com.ainesh.TeamTaskTracker.dtoMapper.TaskUpdationMapper;
import com.ainesh.TeamTaskTracker.enums.ProjectStatTypeEnum;
import com.ainesh.TeamTaskTracker.enums.TaskStatusEnum;
import com.ainesh.TeamTaskTracker.exceptions.ResourceNotFoundException;
import com.ainesh.TeamTaskTracker.interfaces.ProjectTaskService;
import com.ainesh.TeamTaskTracker.models.Project;
import com.ainesh.TeamTaskTracker.models.Task;
import com.ainesh.TeamTaskTracker.utils.PageableUtil;
import jakarta.transaction.Transactional;

@Service
public class ProjectTaskServiceImpl implements ProjectTaskService {
  
  private ProjectDao projectDao;
  private TaskDao taskDao;
  private TaskResponseMapper taskResponseMapper;
  private TaskCreationMapper taskCreationMapper;
  private TaskUpdationMapper taskUpdationMapper;

  @Autowired
  public ProjectTaskServiceImpl(
    ProjectDao projectDao,
    TaskDao taskDao,
    TaskResponseMapper taskResponseMapper,
    ProjectResponseMapper projectResponseMapper,
    TaskCreationMapper taskCreationMapper,
    TaskUpdationMapper taskUpdationMapper
  ){
    this.projectDao = projectDao;
    this.taskDao = taskDao;
    this.taskResponseMapper = taskResponseMapper;
    this.taskCreationMapper = taskCreationMapper;
    this.taskUpdationMapper = taskUpdationMapper;
  }

  @Override
  public Page<TaskResponseDTO> getTasksWithinProject(
    Long projectId, 
    TaskStatusEnum taskStatusEnum,
    Pageable pageable
  ) {

    Pageable safePageable = PageableUtil.enforceSortWhiteList(
      pageable,
      Set.of("id", "title", "description", "createdAt", "updatedAt")
    );

    if(taskStatusEnum!=null){
      return taskDao
                .findByProjectIdAndStatus(projectId, taskStatusEnum, safePageable)
                .map(taskResponseMapper::apply);
    }

    return taskDao
                .findByProjectId(projectId, safePageable)
                .map(taskResponseMapper::apply);

  }

  @Override
  public TaskResponseDTO addTaskWithinProject(
    Long projectId,
    TaskCreationRequestDTO taskCreationRequestDTO
  ) {

    Task taskToBeAdded = taskCreationMapper.apply(taskCreationRequestDTO);
    Project taskOwningProject = projectDao.findById(projectId).orElseThrow(() -> new ResourceNotFoundException("Project with given id couldn't be found"));

    taskToBeAdded.setStatus(TaskStatusEnum.OPEN);
    taskToBeAdded.setProject(taskOwningProject);

    return taskResponseMapper.apply(taskDao.save(taskToBeAdded));

  }

  @Override
  public TaskResponseDTO seeTaskWithinProject(Long projectId, Long taskId) {

    Task taskToBeReturned =  taskDao.findByIdAndProjectId(taskId, projectId).orElseThrow(() -> new ResourceNotFoundException("Task not found"));

    return taskResponseMapper.apply(taskToBeReturned);

  }

  @Override
  public TaskResponseDTO updateTaskWithinProject(Long projectId, Long taskId, TaskUpdationRequestDTO taskUpdationRequestDTO){

    Task task = taskDao
                    .findById(taskId)
                    .orElseThrow(() -> new ResourceNotFoundException("Task with given id not found"));

    if(task.getProject().getId() != projectId){
      throw new ResourceNotFoundException("Invalid taskId");
    }

    Task taskToBeSaved = taskUpdationMapper.apply(taskUpdationRequestDTO);

    if(task.getStatus() != taskToBeSaved.getStatus() && taskToBeSaved.getStatus() == TaskStatusEnum.DONE){
      task.setCompletedAt(LocalDateTime.now());
    }

    task.setDescription(taskToBeSaved.getDescription());
    task.setTitle(taskToBeSaved.getTitle());
    task.setStatus(taskToBeSaved.getStatus());

    return taskResponseMapper.apply(taskDao.save(task));

  }

  @Override
  public void deleteTaskWithinProject(Long projectId, Long taskId){

    Task task = taskDao.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task with given id not found"));

    if(task.getProject().getId() != projectId){
      throw new ResourceNotFoundException("Invalid task");
    }

    taskDao.delete(task);

  }

  @Override
  public ProjectStatsResponse<StatCountDTO> taskCountWithinProject(
      Long projectId,
      TaskStatusEnum taskStatus
    ) {

      if(taskStatus!=null){
        List<Task> result = taskDao.findByProjectIdAndStatus(projectId, taskStatus);
        StatCountDTO statCountDTO = new StatCountDTO(
          result.size()
        );
        return new ProjectStatsResponse<StatCountDTO>(
          projectId, 
          ProjectStatTypeEnum.COUNT, 
          statCountDTO, 
          LocalDateTime.now()
        );
      }

      List<Task> result = projectDao
                              .findById(projectId)
                              .orElseThrow(() -> new ResourceNotFoundException("Project with given project id cannot be found"))
                              .getTasks();

      StatCountDTO statCountDTO = new StatCountDTO(
        result.size()
      );

      return new ProjectStatsResponse<StatCountDTO>(
        projectId, 
        ProjectStatTypeEnum.COUNT, 
        statCountDTO, 
        LocalDateTime.now()
      );

  }

  @Override
  public ProjectStatsResponse<StatSummaryDTO> taskSummary(Long projectId) {

    StatSummaryDTO statSummaryDTO = new StatSummaryDTO(
      taskCountWithinProject(projectId, TaskStatusEnum.OPEN).data().count(),
      taskCountWithinProject(projectId, TaskStatusEnum.IN_PROGRESS).data().count(),
      taskCountWithinProject(projectId, TaskStatusEnum.DONE).data().count(),
      taskCountWithinProject(projectId, TaskStatusEnum.CANCELLED).data().count(),
      taskCountWithinProject(projectId, TaskStatusEnum.BLOCKED).data().count()
    );

    return new ProjectStatsResponse<StatSummaryDTO>(
      projectId, 
      ProjectStatTypeEnum.SUMMARY, 
      statSummaryDTO, 
      LocalDateTime.now()
    );

  }

  @Override
  public ProjectStatsResponse<StatProgressDTO> projectProgress(Long projectId) {

    int tasksDone = taskCountWithinProject(projectId, TaskStatusEnum.DONE)
                          .data()
                          .count();

    int totalNumberOfTasks = taskCountWithinProject(projectId, null).data().count();

    double percentageCompletion = (double)tasksDone/(double)totalNumberOfTasks;

    StatProgressDTO statProgressDTO = new StatProgressDTO(
      tasksDone,
      totalNumberOfTasks,
      percentageCompletion
    );

    return new ProjectStatsResponse<StatProgressDTO>(projectId, ProjectStatTypeEnum.PROGRESS, statProgressDTO, LocalDateTime.now());

  }

  @Override
  public ProjectStatsResponse<StatActivityDTO> projectActivity(Long projectId, int days) {

    LocalDateTime cutoff = LocalDateTime.now().minus(days, ChronoUnit.DAYS);
    int updatedTaskCount = taskDao.countByProjectIdAndUpdatedAtAfter(projectId, cutoff);
    int createdTaskCount = taskDao.countByProjectIdAndCreatedAtAfter(projectId, cutoff);
    int completedTaskCount = taskDao.countByProjectIdAndCompletedAtAfter(projectId, cutoff);

    StatActivityDTO statActivityDTO = new StatActivityDTO(createdTaskCount, updatedTaskCount, completedTaskCount);

    return new ProjectStatsResponse<StatActivityDTO>(
      projectId, 
      ProjectStatTypeEnum.ACTIVITY, 
      statActivityDTO, 
      LocalDateTime.now()
    );

  }

  @Override
  public ProjectStatsResponse<List<TaskResponseDTO>> getStaleTaskList(Long projectId, int limit) {

    Pageable limitingPaging = PageRequest.of(0, limit, Sort.by(Sort.Direction.ASC, "createdAt"));

    List<TaskResponseDTO> taskList =  taskDao
                                        .findByProjectIdAndStatusNot(projectId, TaskStatusEnum.DONE, limitingPaging)
                                        .getContent()
                                        .stream()
                                        .map(taskResponseMapper::apply)
                                        .collect(Collectors.toList());

    return new ProjectStatsResponse<List<TaskResponseDTO>>(
      projectId, 
      ProjectStatTypeEnum.STALE, 
      taskList, 
      LocalDateTime.now()
    );
    
  }

  @Override
  @Transactional
  public void deleteAllTasksInProject(Long projectId) {
    taskDao.deleteByProjectId(projectId);
  }

  @Override
  public List<TaskResponseDTO> updateStatusOfAllTasksInProject(
    Long projectId,
    TaskStatusEnum newStatus
  ) {

    List<Task> tasks = taskDao.findByProjectId(projectId);
    int counter = 0;
    int index = 0;
    List<Task> taskSavedList = new ArrayList<>();

    for(Task task: tasks){

      task.setStatus(newStatus);
      if(newStatus == TaskStatusEnum.DONE){
        task.setCompletedAt(LocalDateTime.now());
      }

      taskSavedList.add(task);
      counter++;
      index++;

      if(counter == 200 || index==tasks.size()){
        counter = 0;
        taskDao.saveAllAndFlush(taskSavedList);
      }

    }

    return tasks.stream().map(taskResponseMapper::apply).collect(Collectors.toList());
  }

}
