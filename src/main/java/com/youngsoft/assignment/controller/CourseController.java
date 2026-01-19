package com.youngsoft.assignment.controller;

import com.youngsoft.assignment.dto.CourseRequest;
import com.youngsoft.assignment.dto.CourseResponse;
import com.youngsoft.assignment.dto.PageResponse;
import com.youngsoft.assignment.dto.StudentCourseCount;
import com.youngsoft.assignment.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("courses")
public class CourseController {
    private final CourseService courseService;
    public CourseController(CourseService courseService){
        this.courseService=courseService;
    }

    @PostMapping("/create_course")
    @Operation(summary = "Creating a new Course")
    private ResponseEntity<CourseResponse> create_Course(@RequestBody CourseRequest courseRequest){
        log.info("Recieved request to create course {}", courseRequest.getTitle());
        CourseResponse response=courseService.createCourse(courseRequest);
        log.info("Course created with id:{}",response.getId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping("details/{title}")
    @Operation(summary = "Get course details of specified name")
    public List<CourseResponse> getWithName(@PathVariable String title){
        log.info("Received request to get course details of {} ",title);
        return courseService.getCourse(title);
    }
    @GetMapping("/course_count/{courseName}")
    @Operation(summary = "Get Student count of specified course")
    public StudentCourseCount getCount(@PathVariable String courseName){
        return courseService.getCountWithName(courseName);
    }
    @GetMapping("/course_count/all")
    @Operation(summary = "Get all courses student count")
    public PageResponse<StudentCourseCount> getallCount(Pageable page){
         return courseService.getCount(page);
    }
}
