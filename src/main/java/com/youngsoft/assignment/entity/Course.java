package com.youngsoft.assignment.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    private String department;
    @OneToMany(mappedBy = "course")
    private List<Enrollment> enrollments;

    public Course(int i, String java, String javaBasicsAndFundamentals, String computerScience) {
    }
}
