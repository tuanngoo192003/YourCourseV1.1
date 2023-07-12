package com.project.CourseSystem.service;

import com.project.CourseSystem.entity.RatingCourse;

import java.util.List;

public interface RatingCourseService {

    void addRatingCourse(RatingCourse ratingCourse);

    RatingCourse getRatingCourseByCourseIdAndUserId(Integer courseId, Integer userId);

    List<RatingCourse> getAllRating();
}
