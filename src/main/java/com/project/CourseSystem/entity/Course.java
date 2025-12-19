package com.project.CourseSystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id", nullable = false)
    private Integer courseID;

    @Column(name = "course_name", nullable = false, length = 100)
    private String courseName;

    @Column(name = "course_image", nullable = false, length = 200)
    private String courseImage;

    @Column(name = "description", nullable = false, length = 1000)
    private String courseDes;

    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(name = "price", nullable = false)
    private Float price;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category categoryID;
}
