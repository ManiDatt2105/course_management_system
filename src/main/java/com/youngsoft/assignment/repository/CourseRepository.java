package com.youngsoft.assignment.repository;

import com.youngsoft.assignment.dto.StudentCourseCount;
import com.youngsoft.assignment.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course,Integer> {
    List<Course> getCourseByTitle(String name);
    @Query(value="""
            SELECT c.title as courseName,
                   COUNT(e.student_id) as studentCount
                   FROM courses c 
                   LEFT JOIN enrollments e ON c.id=e.course_id
                   WHERE LOWER(c.title)=Lower(:courseName) 
                   GROUP BY c.title 
            """,nativeQuery = true)
    StudentCourseCount getCount(@Param("courseName")String courseName);
}
