package com.youngsoft.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

@Data
@AllArgsConstructor
@Setter
public class CourseResponse {
    private Integer id;
    private String title;
    private String description;
    private String department;

    public CourseResponse() {

    }

}
