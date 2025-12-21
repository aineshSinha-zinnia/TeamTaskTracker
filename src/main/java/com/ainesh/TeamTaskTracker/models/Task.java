package com.ainesh.TeamTaskTracker.models;

import java.time.LocalDateTime;
import com.ainesh.TeamTaskTracker.enums.TaskStatusEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Task {
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String title;
  private String description;
  private TaskStatusEnum status;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

}
