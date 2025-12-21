package com.ainesh.TeamTaskTracker.dtoMapper;

import java.util.function.Function;
import org.springframework.stereotype.Service;
import com.ainesh.TeamTaskTracker.dto.TaskUpdationRequestDTO;
import com.ainesh.TeamTaskTracker.models.Task;

@Service
public class TaskUpdationMapper implements Function<TaskUpdationRequestDTO, Task> {
  @Override
  public Task apply(TaskUpdationRequestDTO taskUpdationRequestDTO){
    return new Task(null, taskUpdationRequestDTO.title(), taskUpdationRequestDTO.description(), taskUpdationRequestDTO.status(), null, null);
  }
}
