package com.project.CourseSystem.service;

import com.project.CourseSystem.dto.RatingCourseDTO;
import com.project.CourseSystem.entity.RatingCourse;

import java.util.List;

public interface RatingCourseService {

    void addRatingCourse(RatingCourse ratingCourse);

    RatingCourseDTO getRatingCourseByCourseIdAndUserId(Integer courseId, Integer userId);

    List<RatingCourse> getAllRating();

    List<RatingCourse> getRatingCourseByCourseID(Integer courseID);

    void deleteRatingCourse(Integer ratingCourseID);
}
