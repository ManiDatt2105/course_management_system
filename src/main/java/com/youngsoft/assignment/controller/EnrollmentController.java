package com.youngsoft.assignment.controller;

import com.youngsoft.assignment.dto.CourseResponse;
import com.youngsoft.assignment.dto.EnrollmentRequest;
import com.youngsoft.assignment.dto.EnrollmentResponse;
import com.youngsoft.assignment.dto.StudentEnrolled;
import com.youngsoft.assignment.service.CourseService;
import com.youngsoft.assignment.service.EnrollmentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("enrollement")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;
    private final CourseService courseService;

    public EnrollmentController(EnrollmentService enrollmentService,CourseService courseService){
        this.enrollmentService=enrollmentService;
        this.courseService=courseService;
    }

    @PostMapping("/enroll")
    @Operation(summary="Create a new enrollment")
    public ResponseEntity<EnrollmentResponse> enrollStudent(@RequestBody EnrollmentRequest enrollRequest) {
        EnrollmentResponse response = enrollmentService.enrollStudent(enrollRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("get_all")
    @Operation(summary = "Get all the Course details")
    public Page<CourseResponse> all(Pageable page) {
        return courseService.getall(page);
    }

    @GetMapping("/get_by_course/{courseTitle}")//returns students with the course name apecifies
    @Operation(summary = "Returns the enrolled student details for the specified course")
    public Page<StudentEnrolled> getAll(@PathVariable String courseTitle, Pageable page) {
        log.debug("Request for student details in course :{}",courseTitle);
        return enrollmentService.getByName(courseTitle,page);
    }

    @GetMapping("/search/{studentName}")
    @Operation(summary ="Returns all the courses where the specified student has enrolled")
    public Page<EnrollmentResponse> searchByName(@PathVariable String studentName,Pageable page){
        return enrollmentService.search(studentName,page);
    }

//    public List<EnrollmentResponse> searchByName(@RequestParam String studentName){
//        return enrollmentService.search(studentName);
//    }
}
