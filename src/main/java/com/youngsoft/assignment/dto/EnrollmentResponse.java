package com.youngsoft.assignment.dto;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class EnrollmentResponse {
    private Integer enrollmentId;
    private String studentName;
    private String courseTitle;
    private LocalDate enrolledDate;

    public EnrollmentResponse(Integer enrollmentId, String studentName, String courseTitle, LocalDate enrolledDate) {
        this.enrollmentId = enrollmentId;
        this.studentName = studentName;
        this.courseTitle = courseTitle;
        this.enrolledDate = enrolledDate;
    }
}

