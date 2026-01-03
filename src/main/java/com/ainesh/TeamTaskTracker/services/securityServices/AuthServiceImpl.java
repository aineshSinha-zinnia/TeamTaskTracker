package com.ainesh.TeamTaskTracker.services.securityServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.ainesh.TeamTaskTracker.dao.mongoDao.UserRepository;
import com.ainesh.TeamTaskTracker.dto.security.UserLoginDTO;
import com.ainesh.TeamTaskTracker.dto.security.UserRequestDTO;
import com.ainesh.TeamTaskTracker.dtoMapper.security.UserLoginMapper;
import com.ainesh.TeamTaskTracker.dtoMapper.security.UserRegisterMapper;
import com.ainesh.TeamTaskTracker.interfaces.security.AuthService;
import com.ainesh.TeamTaskTracker.interfaces.security.JwtService;
import com.ainesh.TeamTaskTracker.models.mongoModels.User;

@Service
public class AuthServiceImpl implements AuthService {

  private UserRegisterMapper userRegisterMapper;
  private UserLoginMapper userLoginMapper;
  private PasswordEncoder passwordEncoder;
  private UserRepository userRepository;
  private AuthenticationManager authenticationManager;
  private JwtService jwtService;

  @Autowired
  public AuthServiceImpl(
    UserRegisterMapper userRegisterMapper,
    UserLoginMapper userLoginMapper,
    UserRepository userRepository,
    AuthenticationManager authenticationManager,
    JwtService jwtService,
    PasswordEncoder passwordEncoder
  ){
    this.userLoginMapper = userLoginMapper;
    this.userRegisterMapper = userRegisterMapper;
    this.userRepository = userRepository;
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public String registerUser(UserRequestDTO userRequestDTO) {

    User user = userRegisterMapper.apply(userRequestDTO);
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    userRepository.save(user);

    return jwtService.generateToken(user.getEmail());

  }

  @Override
  public String loginUser(UserLoginDTO userLoginDTO) {

    User user = userLoginMapper.apply(userLoginDTO);
    
    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
    );

    return authentication.isAuthenticated() ? jwtService.generateToken(user.getEmail()) : "Failure";
    
  }
  
}
