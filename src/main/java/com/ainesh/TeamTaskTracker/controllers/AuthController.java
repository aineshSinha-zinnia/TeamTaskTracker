package com.ainesh.TeamTaskTracker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.ainesh.TeamTaskTracker.dto.security.UserLoginDTO;
import com.ainesh.TeamTaskTracker.dto.security.UserRequestDTO;
import com.ainesh.TeamTaskTracker.interfaces.security.AuthService;

@RestController
public class AuthController {

  @Autowired
  private AuthService authService;
  
  @PostMapping("/auth/register")
  public ResponseEntity<?> registerUser(@RequestBody UserRequestDTO userRequestDTO){
    String resp = authService.registerUser(userRequestDTO);
    return new ResponseEntity<>(resp, HttpStatus.OK);
  }

  @PostMapping("/auth/login")
  public ResponseEntity<?> loginUser(@RequestBody UserLoginDTO userLoginDTO){
    String resp = authService.loginUser(userLoginDTO);
    return ResponseEntity.ok(resp);
  }

}
