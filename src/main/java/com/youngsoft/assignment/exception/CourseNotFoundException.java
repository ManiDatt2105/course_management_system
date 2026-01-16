package com.youngsoft.assignment.exception;

import com.youngsoft.assignment.service.CourseService;

public class CourseNotFoundException extends RuntimeException{
    public CourseNotFoundException(String message){
        super(message);
    }
}
