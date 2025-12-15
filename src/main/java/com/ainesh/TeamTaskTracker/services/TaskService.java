package com.ainesh.TeamTaskTracker.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ainesh.TeamTaskTracker.dao.TaskDao;
import com.ainesh.TeamTaskTracker.models.Task;

@Service
public class TaskService {
  
  @Autowired
  private TaskDao taskDao;

  public Optional<List<Task>> getAllTasks(){
    List<Task> tasks = taskDao.findAll();
    if(tasks.size()==0)
      return Optional.empty();
    return Optional.of(tasks);
  }

  public Optional<Task> addTask(Task task){

    task.setCreatedAt(LocalDateTime.now());
    task.setUpdatedAt(LocalDateTime.now());

    try {
      Task savedTask = taskDao.save(task);
      return Optional.of(savedTask);
    } catch (Exception e) {
      return Optional.empty();
    }
  }

  public Optional<Task> getTaskById(Long id){
    return taskDao.findById(id);
  }

  public Optional<Task> updateTask(Long id, Task task){
    // take the id and search for the task. if task exists update it, if it doesn't exist, say so to the user
    Optional<Task> taskToBeUpdated = getTaskById(id);

    return taskToBeUpdated
        .map(
          updatedTask -> {
            updatedTask.setUpdatedAt(LocalDateTime.now());
            updatedTask.setTitle(task.getTitle());
            updatedTask.setDescription(task.getDescription());
            updatedTask.setStatus(task.getStatus());
            return taskDao.save(updatedTask);
          }
        );
  }

  public boolean deleteTask(Long id){
    Optional<Task> taskToBeUpdated = getTaskById(id);

    return taskToBeUpdated
              .map(
                (task) -> {
                  taskDao.deleteById(id);
                  return true;
                }
              ).orElseGet(() -> false);
  }

}
