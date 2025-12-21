package com.project.CourseSystem.dto;

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

    private Integer courseID;

    private Integer userID;
}
