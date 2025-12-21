package com.ainesh.TeamTaskTracker.interfaces;

import java.util.List;
import java.util.Optional;
import com.ainesh.TeamTaskTracker.dto.TaskCreationRequestDTO;
import com.ainesh.TeamTaskTracker.dto.TaskResponseDTO;
import com.ainesh.TeamTaskTracker.dto.TaskUpdationRequestDTO;

public interface TaskService {
  List<TaskResponseDTO> getAllTasks();
  TaskResponseDTO addTask(TaskCreationRequestDTO taskCreationRequestDTO);
  Optional<TaskResponseDTO> getTaskById(Long id);
  Optional<TaskResponseDTO> updateTask(Long id, TaskUpdationRequestDTO taskUpdationRequestDTO);
  void deleteTask(Long id);
}
