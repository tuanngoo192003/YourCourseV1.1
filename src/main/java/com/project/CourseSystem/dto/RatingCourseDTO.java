package com.project.CourseSystem.dto;

import com.project.CourseSystem.entity.Course;
import com.project.CourseSystem.entity.UserInfo;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingCourseDTO {

    private Integer ratingID;

    private Integer rating;

    private String comment;

    private Course courseID;

    private UserInfo userID;
}
