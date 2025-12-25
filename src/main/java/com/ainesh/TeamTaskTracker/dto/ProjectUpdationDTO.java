package com.ainesh.TeamTaskTracker.dto;

import java.time.LocalDate;
import com.ainesh.TeamTaskTracker.enums.ProjectStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProjectUpdationDTO(
  @NotBlank
  @Size(max = 100)
  String title,

  @NotBlank
  @Size(max = 1000)
  String description,

  @NotNull
  ProjectStatusEnum status,

  @NotNull
  LocalDate projectedEndDate
) {
  
}
