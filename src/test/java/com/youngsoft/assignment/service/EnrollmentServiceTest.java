package com.youngsoft.assignment.service;

import com.youngsoft.assignment.dto.*;
import com.youngsoft.assignment.entity.Course;
import com.youngsoft.assignment.entity.Enrollment;
import com.youngsoft.assignment.entity.Student;
import com.youngsoft.assignment.exception.AlreadyEnrolledException;
import com.youngsoft.assignment.exception.CourseNotFoundException;
import com.youngsoft.assignment.exception.StudentNotFoundException;
import com.youngsoft.assignment.repository.CourseRepository;
import com.youngsoft.assignment.repository.EnrollmentRepository;
import com.youngsoft.assignment.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EnrollmentServiceTest {
    @Mock
    private EnrollmentRepository enrollRepo;
    @Mock
    private StudentRepository studentRepo;
    @Mock
    private CourseRepository courseRepo;
    @InjectMocks
    private EnrollmentService enrollService;
    @Test
    void enrollStudentSuccessfully(){
        EnrollmentRequest request=new EnrollmentRequest();
        request.setStudentId(1);
        request.setCourseId(1);
        Student student=new Student(
                1,
                "jack",
                "jack@gmail.com"
        );
        Course course=new Course();
        course.setId(1);
        course.setTitle("Java");
        course.setDescription("Java basics and fundamentals");
        course.setDepartment("CS");
        Enrollment enrollment=new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrolledDate(LocalDate.now());
        when(studentRepo.findById(1)).thenReturn(Optional.of(student));
        when(courseRepo.findById(1)).thenReturn(Optional.of(course));
        when(enrollRepo.existsByStudentAndCourse(student, course)).thenReturn(false);

        when(enrollRepo.save(any(Enrollment.class))).thenReturn(enrollment);

        EnrollmentResponse result=enrollService.enrollStudent(request);
        //assertThat(result.getStudentName()).isEqualTo("jack");
        assertThat(result.getCourseTitle()).isEqualTo("Java");
    }
    @Test
    void courseNotFoundException(){
        EnrollmentRequest request=new EnrollmentRequest();
        request.setCourseId(1);
        request.setStudentId(1);
        Student stu=new Student(
                1,
                "alpha",
                "alpha@gmail.com"
        );
        when(studentRepo.findById(1)).thenReturn(Optional.of(stu));
        when(courseRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(
                CourseNotFoundException.class,
                ()->enrollService.enrollStudent(request)
        );
    }
    @Test
    void StudentNotFound(){
        EnrollmentRequest request=new EnrollmentRequest();
        request.setStudentId(1);
        request.setCourseId(1);

        assertThrows(
                StudentNotFoundException.class,
                ()->enrollService.enrollStudent(request)
        );
    }
    @Test
    void checkDuplicate(){
        EnrollmentRequest request=new EnrollmentRequest();
        request.setStudentId(1);
        request.setCourseId(1);
        when(studentRepo.findById(1)).thenReturn(Optional.of(new Student()));
        when(courseRepo.findById(1)).thenReturn(Optional.of(new Course()));
        when(enrollRepo.existsByStudentAndCourse(any(),any())).thenReturn(true);

        assertThrows(
                AlreadyEnrolledException.class,
                ()->enrollService.enrollStudent(request)
        );
    }
    @Test
    void getStudentDetailsForCourse(){
        Pageable page= PageRequest.of(0,2);
        StudentEnrolled stu1=new StudentEnrolled(
                1,
                "jack",
                "jack@gmail.com"
        );
        StudentEnrolled stu2=new StudentEnrolled(
                2,
                "jill",
                "jill@gmail.com"
        );
        Page<StudentEnrolled> lis=new PageImpl<>(List.of(stu1,stu2),page,2);
        when(enrollRepo.findByCourseTitle("Java",page)).thenReturn(lis);

        PageResponse<StudentEnrolled> result=enrollService.getByName("Java",page);
        assertThat(result.getContent().get(1).getStudentName()).isEqualTo("jill");
        assertThat(result.getContent().size()).isEqualTo(2);
    }
    @Test
    void getCourseDetailsForStudent(){
            Pageable page=PageRequest.of(0,2);
            EnrollmentResponse er1=new EnrollmentResponse(
                    1,
                    "jack",
                    "Java",
                    LocalDate.now()
            );
            EnrollmentResponse er2=new EnrollmentResponse(
                    2,
                    "jack",
                    "Spring Boot",
                    LocalDate.now()
            );
            Page<EnrollmentResponse> lis=new PageImpl<>(List.of(er1,er2),page,2);
        when(enrollRepo.filter("jack",page)).thenReturn(lis);
        PageResponse<EnrollmentResponse> result=enrollService.search("jack",page);
        assertThat(result.getContent().get(0).getCourseTitle()).isEqualTo("Java");
        assertThat(result.getContent().get(1).getCourseTitle()).isEqualTo("Spring Boot");
    }

}
