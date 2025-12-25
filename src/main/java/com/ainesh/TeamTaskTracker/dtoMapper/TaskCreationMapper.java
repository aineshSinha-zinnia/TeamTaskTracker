package com.ainesh.TeamTaskTracker.dtoMapper;

import java.util.function.Function;
import org.springframework.stereotype.Service;
import com.ainesh.TeamTaskTracker.dto.TaskCreationRequestDTO;
import com.ainesh.TeamTaskTracker.models.Task;

@Service
public class TaskCreationMapper implements Function<TaskCreationRequestDTO, Task> {
  @Override
  public Task apply(TaskCreationRequestDTO taskCreationRequestDTO) {
    return new Task(null, taskCreationRequestDTO.title(), taskCreationRequestDTO.description(), null, null);
  }
}
