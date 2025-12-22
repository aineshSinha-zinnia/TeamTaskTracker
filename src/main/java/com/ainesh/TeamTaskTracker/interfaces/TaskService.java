package com.ainesh.TeamTaskTracker.interfaces;

import java.util.List;
import com.ainesh.TeamTaskTracker.dto.TaskCreationRequestDTO;
import com.ainesh.TeamTaskTracker.dto.TaskResponseDTO;
import com.ainesh.TeamTaskTracker.dto.TaskUpdationRequestDTO;

public interface TaskService {
  List<TaskResponseDTO> getAllTasks();
  TaskResponseDTO addTask(TaskCreationRequestDTO taskCreationRequestDTO);
  TaskResponseDTO getTaskById(Long id);
  TaskResponseDTO updateTask(Long id, TaskUpdationRequestDTO taskUpdationRequestDTO);
  void deleteTask(Long id);
}
