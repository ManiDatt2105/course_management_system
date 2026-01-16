package com.youngsoft.assignment.repository;

import com.youngsoft.assignment.dto.EnrollmentResponse;
import com.youngsoft.assignment.dto.StudentEnrolled;
import com.youngsoft.assignment.entity.Course;
import com.youngsoft.assignment.entity.Enrollment;
import com.youngsoft.assignment.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment,Integer> {
    boolean existsByStudentAndCourse(Student student, Course course);
    @Query("""
    SELECT new com.youngsoft.assignment.dto.StudentEnrolled(
    s.id,
    s.name,
    s.email
    )
    From Enrollment e
    Join e.student s 
    Join e.course c
    where Lower(c.title)=Lower(:courseTitle)
""")
    Page<StudentEnrolled> findByCourseTitle(@Param("courseTitle") String title, Pageable page);

    @Query("""
select new com.youngsoft.assignment.dto.EnrollmentResponse(
e.id,
s.name,
c.title,
e.enrolledDate
)
from Enrollment e
JOIN e.student s 
JOIN e.course c
where (s.name like %:studentName)
""")
    Page<EnrollmentResponse> filter(@Param("studentName")String studentName,Pageable page);

//    List<EnrollmentResponse> filter(@Param("studentName")String studentName);

}
