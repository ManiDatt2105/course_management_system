package com.youngsoft.assignment.dto;

import com.youngsoft.assignment.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentResponse {
    private Integer id;
    private String name;
    private String email;

}
