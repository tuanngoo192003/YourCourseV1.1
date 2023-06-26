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
    @Column(name="ratingID", nullable = false)
    private Integer ratingID;

    @Column(name="rating",nullable = false)
    private Integer rating;

    @Column(name="comment", length = 1000,nullable = false)
    private String comment;

    @OneToOne
    @JoinColumn(name="courseID", nullable = false)
    private Course courseID;

    @OneToOne
    @JoinColumn(name="userID", nullable = false)
    private UserInfo userID;
}
