package com.youngsoft.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentEnrolled {
    private Integer id;
    private String studentName;
    private String email;
}
