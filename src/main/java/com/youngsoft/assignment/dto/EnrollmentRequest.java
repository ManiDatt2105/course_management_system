package com.youngsoft.assignment.dto;

import lombok.Data;
import lombok.Getter;
import org.antlr.v4.runtime.misc.NotNull;
@Getter
@Data
public class EnrollmentRequest {
    @NotNull
    private Integer studentId;

    @NotNull
    private Integer courseId;
}
