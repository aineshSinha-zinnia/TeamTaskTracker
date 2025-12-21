package com.ainesh.TeamTaskTracker.dto;

import com.ainesh.TeamTaskTracker.enums.TaskStatusEnum;

public record TaskUpdationRequestDTO(
  String title,
  String description,
  TaskStatusEnum status
) {
  
}
