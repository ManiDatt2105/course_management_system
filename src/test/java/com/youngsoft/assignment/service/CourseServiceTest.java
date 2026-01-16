package com.youngsoft.assignment.service;

import com.youngsoft.assignment.dto.CourseRequest;
import com.youngsoft.assignment.dto.CourseResponse;
import com.youngsoft.assignment.entity.Course;
import com.youngsoft.assignment.repository.CourseRepository;
import jakarta.inject.Inject;
import jakarta.persistence.Table;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {
    @Mock
    private CourseRepository courseRepo;
    @InjectMocks
    private CourseService courseService;

    @Test
    void CreateCourse(){
        CourseRequest courseRequest=new CourseRequest(
                "Java",
                "This covers the basics of Java and also includes database connectivity using JDBC and servlets",
                "Computer Science"
        );
        Course course=new Course();
        course.setId(1);
        course.setTitle(courseRequest.getTitle());
        course.setDescription(courseRequest.getDescription());
        course.setDepartment(courseRequest.getDepartment());

        when(courseRepo.save(any(Course.class))).thenReturn(course);
        CourseResponse crp=courseService.createCourse(courseRequest);
        assertThat(crp).isNotNull();
        assertThat(crp.getId()).isEqualTo(1);
        assertThat(crp.getTitle()).isEqualTo("Java");
        assertThat(crp.getDescription()).isEqualTo("This covers the basics of Java and also includes database connectivity using JDBC and servlets");
        assertThat(crp.getDepartment()).isEqualTo("Computer Science");
    }
    @Test
    void getAllCourses(){
        Pageable page= PageRequest.of(0,2);
        Course c1=new Course();
        c1.setId(2);
        c1.setTitle("C Programming");
        c1.setDescription("This covers the basics of C and progrmming concpts");
        c1.setDepartment("IT");

        Course c2=new Course();
        c2.setId(1);
        c2.setTitle("Python");
        c2.setDescription("Basics of Python and into to ML concepts");
        c2.setDepartment("Computer Science");
        Page<Course> coursePage=new PageImpl<>(List.of(c1,c2),page,2);
        when(courseRepo.findAll(page)).thenReturn(coursePage);

        Page<CourseResponse> result=courseService.getall(page);
        assertEquals(2,result.getContent().size());
        assertEquals("C Programming",result.getContent().get(0).getTitle());
        assertEquals("Python",result.getContent().get(1).getTitle());
        assertEquals(2,result.getContent().get(0).getId());

    }
    @Test
    public void returnEmptyPage(){
        Pageable page=PageRequest.of(0,2);
        Page<Course> empty=new PageImpl<>(List.of(),page,0);
        when(courseRepo.findAll(page)).thenReturn(empty);
        Page<CourseResponse> res=courseService.getall(page);
        assertEquals(0,res.getContent().size());
        assertEquals(0,res.getTotalElements());
    }

}
