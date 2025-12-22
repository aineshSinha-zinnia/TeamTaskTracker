package com.ainesh.TeamTaskTracker.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
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

  public List<TaskResponseDTO> getAllTasks(){
    return taskDao
                .findAll()
                .stream()
                .map(taskResponseMapper::apply)
                .collect(Collectors.toList());
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
