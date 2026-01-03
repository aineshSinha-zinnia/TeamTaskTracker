package com.ainesh.TeamTaskTracker.dtoMapper.security;

import java.util.function.Function;
import org.springframework.stereotype.Service;
import com.ainesh.TeamTaskTracker.dto.security.UserLoginDTO;
import com.ainesh.TeamTaskTracker.models.mongoModels.User;

@Service
public class UserLoginMapper implements Function<UserLoginDTO, User> {

  @Override
  public User apply(UserLoginDTO userLoginDTO) {
    return new User(null, null, userLoginDTO.email(), userLoginDTO.password(), null);
  }
  
}
