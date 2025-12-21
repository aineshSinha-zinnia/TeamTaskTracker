package com.ainesh.TeamTaskTracker.dto;

import com.ainesh.TeamTaskTracker.enums.TaskStatusEnum;

public record TaskResponseDTO(
  Long id,
  String title,
  String description,
  TaskStatusEnum status
) {
  
}
