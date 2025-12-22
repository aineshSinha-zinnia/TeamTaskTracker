package com.ainesh.TeamTaskTracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TaskCreationRequestDTO(
  @NotBlank
  @Size(max = 100)
  String title,

  @Size(max = 1000)
  String description
) {
  
}
