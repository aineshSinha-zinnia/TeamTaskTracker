package com.ainesh.TeamTaskTracker.models.mongoModels;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import com.ainesh.TeamTaskTracker.enums.UserRoles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

  @Id
  private String userId;

  private String name;

  @Indexed(unique = true)
  private String email;
  private String password;

  private UserRoles role;

}
