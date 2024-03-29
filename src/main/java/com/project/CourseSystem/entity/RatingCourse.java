package com.project.CourseSystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name="ratingCourse")
public class RatingCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ratingID", nullable = false)
    private Integer ratingID;

    @Column(name="rating",nullable = false)
    private Integer rating;

    @Column(name="comment", length = 1000,nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(name="courseID", nullable = false)
    private Course courseID;

    @ManyToOne
    @JoinColumn(name="userID", nullable = false)
    private UserInfo userID;
}
