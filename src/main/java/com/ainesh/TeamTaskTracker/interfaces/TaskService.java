package com.ainesh.TeamTaskTracker.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.ainesh.TeamTaskTracker.dto.TaskCreationRequestDTO;
import com.ainesh.TeamTaskTracker.dto.TaskResponseDTO;
import com.ainesh.TeamTaskTracker.dto.TaskUpdationRequestDTO;
import com.ainesh.TeamTaskTracker.enums.TaskStatusEnum;

public interface TaskService {
  Page<TaskResponseDTO> getAllTasks(TaskStatusEnum status, Pageable pageable);
  TaskResponseDTO addTask(TaskCreationRequestDTO taskCreationRequestDTO);
  TaskResponseDTO getTaskById(Long id);
  TaskResponseDTO updateTask(Long id, TaskUpdationRequestDTO taskUpdationRequestDTO);
  void deleteTask(Long id);
  void deleteAll();
}
