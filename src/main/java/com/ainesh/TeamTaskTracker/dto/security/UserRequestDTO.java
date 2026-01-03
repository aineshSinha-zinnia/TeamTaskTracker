package com.ainesh.TeamTaskTracker.dto.security;

public record UserRequestDTO(
  String name,
  String email,
  String password
) {
}
