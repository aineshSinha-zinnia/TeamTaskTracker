package com.ainesh.TeamTaskTracker.services;

import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ainesh.TeamTaskTracker.dao.TaskDao;
import com.ainesh.TeamTaskTracker.dto.TaskCreationRequestDTO;
import com.ainesh.TeamTaskTracker.dto.TaskResponseDTO;
import com.ainesh.TeamTaskTracker.dto.TaskUpdationRequestDTO;
import com.ainesh.TeamTaskTracker.dtoMapper.TaskCreationMapper;
import com.ainesh.TeamTaskTracker.dtoMapper.TaskResponseMapper;
import com.ainesh.TeamTaskTracker.dtoMapper.TaskUpdationMapper;
import com.ainesh.TeamTaskTracker.enums.TaskStatusEnum;
import com.ainesh.TeamTaskTracker.exceptions.ResourceNotFoundException;
import com.ainesh.TeamTaskTracker.interfaces.TaskService;
import com.ainesh.TeamTaskTracker.models.Task;
import com.ainesh.TeamTaskTracker.utils.PageableUtil;

@Service
public class TaskServiceImpl implements TaskService {
  
  private TaskResponseMapper taskResponseMapper;
  private TaskDao taskDao;
  private TaskCreationMapper taskCreationMapper;
  private TaskUpdationMapper taskUpdationMapper;

  @Autowired
  public TaskServiceImpl(TaskDao taskDao, TaskCreationMapper taskCreationMapper, TaskResponseMapper taskResponseMapper, TaskUpdationMapper taskUpdationMapper){
    this.taskCreationMapper = taskCreationMapper;
    this.taskDao = taskDao;
    this.taskResponseMapper = taskResponseMapper;
    this.taskUpdationMapper = taskUpdationMapper;
  }

  public Page<TaskResponseDTO> getAllTasks(TaskStatusEnum status, Pageable pageable){

    Pageable allowedPageable = PageableUtil.enforceSortWhiteList(
      pageable, 
      Set.of("id", "title", "description", "createdAt", "updatedAt")
    );

    if(status != null){
      return taskDao.findByStatus(status, allowedPageable).map(taskResponseMapper::apply);
    }

    return taskDao
                .findAll(allowedPageable)
                .map(taskResponseMapper::apply);
  }

  public TaskResponseDTO addTask(TaskCreationRequestDTO taskCreationRequestDTO){

    Task task = taskCreationMapper.apply(taskCreationRequestDTO);

    task.setCreatedAt(LocalDateTime.now());
    task.setUpdatedAt(LocalDateTime.now());
    task.setStatus(TaskStatusEnum.OPEN);

    return taskResponseMapper.apply(taskDao.save(task));
  }

  public TaskResponseDTO getTaskById(Long id){
    return taskDao
                .findById(id)
                .map(taskResponseMapper::apply)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found: " + id));
  }

  public TaskResponseDTO updateTask(Long id, TaskUpdationRequestDTO taskUpdationRequestDTO){
    // take the id and search for the task. if task exists update it, if it doesn't exist, say so to the user
    Task taskToBeUpdated = taskDao
                            .findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("Requested id invalid"));

    Task task = taskUpdationMapper.apply(taskUpdationRequestDTO);

    taskToBeUpdated.setTitle(task.getTitle());
    taskToBeUpdated.setDescription(task.getDescription());
    taskToBeUpdated.setStatus(task.getStatus());

    return taskResponseMapper.apply(taskDao.save(taskToBeUpdated));
  }

  public void deleteTask(Long id){
    taskDao.deleteById(id);
  }

}
