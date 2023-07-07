package com.project.CourseSystem.service.impl;

import com.project.CourseSystem.entity.RatingCourse;
import com.project.CourseSystem.repository.RatingCourseRepository;
import com.project.CourseSystem.service.RatingCourseService;
import org.springframework.stereotype.Service;

@Service
public class RatingCourseServiceImpl implements RatingCourseService {

    RatingCourseRepository ratingCourseRepository;

    public RatingCourseServiceImpl(RatingCourseRepository ratingCourseRepository) {
        this.ratingCourseRepository = ratingCourseRepository;
    }

    @Override
    public void addRatingCourse(RatingCourse ratingCourse) {
        ratingCourseRepository.save(ratingCourse);
    }

    @Override
    public RatingCourse getRatingCourseByCourseIdAndUserId(Integer courseId, Integer userId) {
        RatingCourse ratingCourse = ratingCourseRepository.getRatingCourseByCourseIdAndUserId(courseId, userId);
        return ratingCourse;
    }
}
