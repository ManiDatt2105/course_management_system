package com.youngsoft.assignment.controller;

import com.youngsoft.assignment.dto.PageResponse;
import com.youngsoft.assignment.dto.StudentCourse;
import com.youngsoft.assignment.dto.StudentRequest;
import com.youngsoft.assignment.dto.StudentResponse;
import com.youngsoft.assignment.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;
    public StudentController(StudentService studentService){
        this.studentService=studentService;
    }
    @PostMapping("/create_student")
    @Operation(summary="To create a student")
    public ResponseEntity<StudentResponse> createStudent(@RequestBody StudentRequest student_request){
        StudentResponse response=studentService.createStudent(student_request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping("/with_courses")
    @Operation(summary="Get student name and enrolled course name")
    public PageResponse<StudentCourse> studentWithCourse(Pageable page){
        return studentService.fetchStudents(page);
    }
}
