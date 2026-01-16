package com.youngsoft.assignment.dto;

import lombok.Data;
@Data
public class CourseRequest {
    private String title;
    private String description;
    private String department;

    public CourseRequest(String title, String description, String department) {
        this.title = title;
        this.description = description;
        this.department = department;
    }
}
