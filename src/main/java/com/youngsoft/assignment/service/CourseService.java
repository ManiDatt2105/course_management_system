package com.youngsoft.assignment.service;

import com.youngsoft.assignment.dto.CourseRequest;
import com.youngsoft.assignment.dto.CourseResponse;
import com.youngsoft.assignment.dto.PageResponse;
import com.youngsoft.assignment.dto.StudentCourseCount;
import com.youngsoft.assignment.entity.Course;
import com.youngsoft.assignment.repository.CourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
public class CourseService {
    private final CourseRepository courseRepo;
    public CourseService(CourseRepository courseRepo){
        this.courseRepo=courseRepo;
    }
    public CourseResponse createCourse(CourseRequest courseRequest) {
        log.debug("Creating a new course with title:{}",courseRequest.getTitle());
        Course course=new Course();
        course.setTitle(courseRequest.getTitle());
        course.setDescription(courseRequest.getDescription());
        course.setDepartment(courseRequest.getDepartment());
        System.out.println(course.getDepartment());
        System.out.println(course.getDescription());
        Course saved=courseRepo.save(course);
        log.debug("Course saved  with title:{} and id:{}",saved.getTitle(),saved.getId());
        return new CourseResponse(saved.getId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.getDepartment()
        );
    }

    public PageResponse<CourseResponse> getall(Pageable page) {
        Page<CourseResponse> ls=courseRepo.findAll(page).map(course -> new CourseResponse(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getDepartment()
        ));;
//        Page<List<CourseResponse>> ls1= new Page<ArrayList<>>();
//        for(Course c:ls){
//            CourseResponse course=new CourseResponse();
//            course.setId(c.getId());
//            course.setDescription(c.getDescription());
//            course.setTitle(c.getTitle());
//            ls1.add(new ArrayList<>(course));
////        }
//        ls.map(course -> new CourseResponse(
//                course.getId(),
//                course.getTitle(),
//                course.getDescription(),
//                course.getDepartment()
//        ));
        return new PageResponse<CourseResponse>(
                ls.getContent(),
                ls.getNumber(),
                ls.getSize(),
                ls.getTotalElements(),
                ls.getTotalPages()
        );
    }

    public List<CourseResponse> getCourse(String name) {
        List<Course> ls=courseRepo.getCourseByTitle(name);
        List<CourseResponse> res=new ArrayList<>();
        for(Course cr:ls){
            CourseResponse course=new CourseResponse();
            course.setId(cr.getId());
            course.setTitle(cr.getTitle());
            course.setDescription(cr.getDescription());
            course.setDepartment(cr.getDepartment());
            res.add(course);
        }
        return res;
    }

    public StudentCourseCount getCountWithName(String courseName) {
        return courseRepo.getCount(courseName);
    }

    public PageResponse<StudentCourseCount> getCount(Pageable page) {
        Page<StudentCourseCount> res= courseRepo.getAllCount(page);
        return new PageResponse<StudentCourseCount>(
                res.getContent(),
                res.getNumber(),
                res.getSize(),
                res.getTotalElements(),
                res.getTotalPages()
                );
//        return res;
    }
}
