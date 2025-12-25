package com.ainesh.TeamTaskTracker.dto;

import java.time.LocalDate;
import com.ainesh.TeamTaskTracker.enums.ProjectStatusEnum;

public record ProjectResponseDTO(
  Long id,
  String title,
  String description,
  ProjectStatusEnum status,
  LocalDate projectedEndDate
) {
  
}
