package com.ainesh.TeamTaskTracker.dto;

import com.ainesh.TeamTaskTracker.enums.TaskStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TaskUpdationRequestDTO(
  @NotBlank 
  @Size(max = 100)
  String title,
  
  @Size(max = 1000)
  String description,

  @NotNull
  TaskStatusEnum status
) {
  
}
