package com.youngsoft.assignment.exception;

public class AlreadyEnrolledException extends RuntimeException{
    public AlreadyEnrolledException(String message){
        super(message);
    }
}
