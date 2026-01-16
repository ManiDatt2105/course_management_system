package com.youngsoft.assignment.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = EmailAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleEmailExists(EmailAlreadyExistsException ex){
        log.error("The Email already exists");
        return ResponseEntity.badRequest().
                body(new ApiError(ex.getMessage(),"EMAIL_EXISTS"));
    }
    @ExceptionHandler(value = CourseNotFoundException.class)
    public ResponseEntity<ApiError> handleCourseNotFound(CourseNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError(ex.getMessage(),"COURSE_NOT_FOUND"));
    }
    @ExceptionHandler(value = StudentNotFoundException.class)
    public ResponseEntity<ApiError> handleStudentNotFound(StudentNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError(ex.getMessage(),"STUDENT_NOT_FOUND"));
    }
    @ExceptionHandler(value= AlreadyEnrolledException.class)
    public ResponseEntity<ApiError> handleAlreadyEnrolled(AlreadyEnrolledException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiError(ex.getMessage(),"ALREADY_ENROLLED"));
    }
}
