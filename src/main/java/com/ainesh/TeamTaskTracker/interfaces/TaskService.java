package com.ainesh.TeamTaskTracker.interfaces;

import java.util.List;
import java.util.Optional;
import com.ainesh.TeamTaskTracker.dto.TaskCreationRequestDTO;
import com.ainesh.TeamTaskTracker.dto.TaskResponseDTO;
import com.ainesh.TeamTaskTracker.dto.TaskUpdationRequestDTO;
import com.ainesh.TeamTaskTracker.models.Task;

public interface TaskService {
  Optional<List<TaskResponseDTO>> getAllTasks();
  TaskResponseDTO addTask(TaskCreationRequestDTO taskCreationRequestDTO);
  Optional<TaskResponseDTO> getTaskById(Long id);
  Optional<TaskResponseDTO> updateTask(Long id, TaskUpdationRequestDTO taskUpdationRequestDTO);
  boolean deleteTask(Long id);
}
