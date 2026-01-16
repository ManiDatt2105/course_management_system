package com.youngsoft.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentCourse {
    private Integer studentId;
    private String studentName;
    private String courseTitle;
}
