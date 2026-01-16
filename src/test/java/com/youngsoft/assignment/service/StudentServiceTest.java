package com.youngsoft.assignment.service;

import com.youngsoft.assignment.dto.StudentCourse;
import com.youngsoft.assignment.dto.StudentRequest;
import com.youngsoft.assignment.dto.StudentResponse;
import com.youngsoft.assignment.entity.Student;
import com.youngsoft.assignment.exception.EmailAlreadyExistsException;
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

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
    @Mock
    private StudentRepository studentRepo;

    @InjectMocks
    private StudentService studentService;
    @Test
    void createStudent(){
        StudentRequest request=new StudentRequest(
                "alpha",
                "alpha@gmail.com"
        );
        Student stu=new Student();
        stu.setId(1);
        stu.setName(request.getName());
        stu.setEmail(request.getEmail());

        when(studentRepo.save(any(Student.class))).thenReturn(stu);
        StudentResponse result=studentService.createStudent(request);
        assertThat(result.getId()).isEqualTo(1);
    }
    @Test
    void throwExceptionOnDuplicateEmail(){
           StudentRequest request=new StudentRequest(
                   "jack",
                   "jack@gmail.com"
           );
           when(studentRepo.existsByEmail("jack@gmail.com")).thenReturn(true);
        EmailAlreadyExistsException ex=assertThrows(
                EmailAlreadyExistsException.class,
                ()->studentService.createStudent(request)
        );
        assertEquals("Already exists......",ex.getMessage());
    }
    @Test
    void returnStudentWithCourses(){
        Pageable page= PageRequest.of(0,2);
        StudentCourse sc1=new StudentCourse(
                1,
                "alpha",
                "C Programming"
        );
        StudentCourse sc2=new StudentCourse(
                2,
                "bravo",
                "Python Programming"
        );
        Page<StudentCourse> list=new PageImpl<>(List.of(sc1,sc2),page,2);
        when(studentRepo.fetchStudentWithCourse(page)).thenReturn(list);
        Page<StudentCourse> result=studentService.fetchStudents(page);
        assertEquals(2,result.getContent().size());
        assertEquals("alpha",result.getContent().get(0).getStudentName());
    }

}
