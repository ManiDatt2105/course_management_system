package com.youngsoft.assignment.service;

import com.youngsoft.assignment.dto.EnrollmentRequest;
import com.youngsoft.assignment.dto.EnrollmentResponse;
import com.youngsoft.assignment.dto.StudentEnrolled;
import com.youngsoft.assignment.entity.Course;
import com.youngsoft.assignment.entity.Enrollment;
import com.youngsoft.assignment.entity.Student;
import com.youngsoft.assignment.exception.AlreadyEnrolledException;
import com.youngsoft.assignment.exception.CourseNotFoundException;
import com.youngsoft.assignment.exception.StudentNotFoundException;
import com.youngsoft.assignment.repository.CourseRepository;
import com.youngsoft.assignment.repository.EnrollmentRepository;
import com.youngsoft.assignment.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Slf4j
@Service
public class EnrollmentService {
    private final  EnrollmentRepository enrollRepo;
    private final StudentRepository studentRepo;
    private final CourseRepository courseRepo;
    public EnrollmentService(EnrollmentRepository enrollRepo, StudentRepository studentRepo, CourseRepository courseRepo) {
        this.enrollRepo = enrollRepo;
        this.studentRepo = studentRepo;
        this.courseRepo = courseRepo;
    }

    public EnrollmentResponse enrollStudent(EnrollmentRequest enrollRequest) {
        log.info("Attempt to enroll Student id:{} with Course id:{}",enrollRequest.getStudentId(),enrollRequest.getCourseId());
        Student student=studentRepo.findById(enrollRequest.getStudentId()).
                orElseThrow(()->new StudentNotFoundException("Student not found With id"+enrollRequest.getStudentId()));

        Course course=courseRepo.findById(enrollRequest.getCourseId())
                .orElseThrow(()->new CourseNotFoundException("No course found with id"+enrollRequest.getCourseId()));
        log.debug("enrolling student{} into course {}",student.getName(), course.getId());
        if(enrollRepo.existsByStudentAndCourse(student,course)){
            log.debug("Duplicate Enrollment detected with student id:{} and course id:{}",enrollRequest.getStudentId(),enrollRequest.getCourseId());
            throw new AlreadyEnrolledException("Student already enrolled into this course");
        }
        Enrollment enroll=new Enrollment();
        enroll.setStudent(student);
        enroll.setCourse(course);
        enroll.setEnrolledDate(LocalDate.now());
        Enrollment saved=enrollRepo.save(enroll);
        log.debug("Student enrolled successfully with id:{}",saved.getId());
        return new EnrollmentResponse(saved.getId(),
                saved.getStudent().getName(),
                saved.getCourse().getTitle(),
                saved.getEnrolledDate());
    }

    public Page<StudentEnrolled> getByName(String title, Pageable page) {
        log.debug("Getting student data for course {}",title);
        return enrollRepo.findByCourseTitle(title,page);
    }

    public Page<EnrollmentResponse> search(String studentName,Pageable page) {

        Page<EnrollmentResponse> result=enrollRepo.filter(studentName,page);
        if(!studentRepo.existsByName(studentName)){
            throw new StudentNotFoundException("Check the entered name or create a student of this name and enroll");
        }
        return result;
    }
//    public List<EnrollmentResponse> search(String studentName) {
//        return enrollRepo.filter(studentName);
//    }

}
