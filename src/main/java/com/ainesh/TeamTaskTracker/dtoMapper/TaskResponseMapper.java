package com.ainesh.TeamTaskTracker.dtoMapper;

import java.util.function.Function;
import org.springframework.stereotype.Service;
import com.ainesh.TeamTaskTracker.dto.TaskResponseDTO;
import com.ainesh.TeamTaskTracker.models.Task;

@Service
public class TaskResponseMapper implements Function<Task, TaskResponseDTO> {

  @Override
  public TaskResponseDTO apply(Task task) {
    return new TaskResponseDTO(task.getId(), task.getTitle(), task.getDescription(), task.getStatus());
  }
  
}
