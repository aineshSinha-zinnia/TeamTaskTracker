package com.ainesh.TeamTaskTracker.exceptionHandler;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.ainesh.TeamTaskTracker.dto.ApiError;
import com.ainesh.TeamTaskTracker.enums.ApiErrorCodesEnum;
import com.ainesh.TeamTaskTracker.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
  
  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ApiError handleResourceNotFoundError(
    ResourceNotFoundException resourceNotFoundException,
    HttpServletRequest request
  ){
    return new ApiError(
      HttpStatusCode.valueOf(404), 
      resourceNotFoundException.getMessage(), 
      ApiErrorCodesEnum.RESOURCE_NOT_FOUND, 
      LocalDateTime.now());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiError handleInvalidMethodArguments(
    MethodArgumentNotValidException methodArgumentNotValidException,
    HttpServletRequest request
  ){
    return new ApiError(
      HttpStatusCode.valueOf(400), 
      methodArgumentNotValidException.getMessage(), 
      ApiErrorCodesEnum.METHOD_ARGUMENT_VALIDATION_FAILED, 
    LocalDateTime.now());
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ApiError handleExceptionGeneric(
    Exception ex,
    HttpServletRequest request
  ){
    return new ApiError(
      HttpStatusCode.valueOf(500), 
      ex.getMessage(), 
      ApiErrorCodesEnum.INTERNAL_SERVER_ERROR, 
    LocalDateTime.now());
  }
}
