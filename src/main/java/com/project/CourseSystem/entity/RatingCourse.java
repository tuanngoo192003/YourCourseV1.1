package com.project.CourseSystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "ratings")
public class RatingCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rating_id", nullable = false)
    private Integer ratingID;

    @Column(name = "stars_rated", nullable = false)
    private Integer rating;

    @Column(name = "comment", length = 1000, nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course courseID;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserInfo userID;
}
