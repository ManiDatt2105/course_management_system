package com.youngsoft.assignment.service;

import com.youngsoft.assignment.dto.StudentCourse;
import com.youngsoft.assignment.dto.StudentRequest;
import com.youngsoft.assignment.dto.StudentResponse;
import com.youngsoft.assignment.entity.Student;
import com.youngsoft.assignment.exception.EmailAlreadyExistsException;
import com.youngsoft.assignment.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class StudentService {
    private final StudentRepository studentRepo;
    public StudentService(StudentRepository studentRepo){
        this.studentRepo=studentRepo;
    }
    public StudentResponse createStudent(StudentRequest studentRequest) {
        if(studentRepo.existsByEmail(studentRequest.getEmail())){
            log.debug("Student already exists with this email id");
            throw new EmailAlreadyExistsException("Already exists......");
        }
        log.debug("Creating Student:{}",studentRequest.getName());
        Student stu=new Student();
        stu.setName(studentRequest.getName());
        stu.setEmail(studentRequest.getEmail());
        Student saved=studentRepo.save(stu);
        log.debug("Created Student with name:{} and id:{}",saved.getName(),saved.getId());
    return new StudentResponse(saved.getId(),
            saved.getName(),
            saved.getEmail());
    }

    public Page<StudentCourse> fetchStudents(Pageable page) {
//        Page<StudentCourse>
        return studentRepo.fetchStudentWithCourse(page);
    }
}
