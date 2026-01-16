package com.youngsoft.assignment.repository;

import com.youngsoft.assignment.dto.StudentCourse;
import com.youngsoft.assignment.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer> {
    boolean existsByEmail(String email);
    @Query("""
Select new com.youngsoft.assignment.dto.StudentCourse(
    s.id,
    s.name,
    c.title
)
from Student s
Join s.enrollments e
Join e.course c
""")
    Page<StudentCourse> fetchStudentWithCourse(Pageable page);
    boolean existsByName(String name);
}
