package com.youngsoft.assignment.dto;

import lombok.Data;

@Data
public class StudentRequest {

    private String name;
    private String email;

    public StudentRequest(String name, String mail) {
        this.name=name;
        this.email=mail;
    }
}
