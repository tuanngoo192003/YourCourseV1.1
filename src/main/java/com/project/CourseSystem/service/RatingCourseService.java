package com.project.CourseSystem.service;

import com.project.CourseSystem.entity.RatingCourse;

public interface RatingCourseService {

    public void addRatingCourse(RatingCourse ratingCourse);

    public RatingCourse getRatingCourseByCourseIdAndUserId(Integer courseId, Integer userId);
}
