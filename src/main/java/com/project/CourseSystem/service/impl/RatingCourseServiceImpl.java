package com.project.CourseSystem.service.impl;

import com.project.CourseSystem.dto.RatingCourseDTO;
import com.project.CourseSystem.entity.RatingCourse;
import com.project.CourseSystem.repository.RatingCourseRepository;
import com.project.CourseSystem.service.RatingCourseService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingCourseServiceImpl implements RatingCourseService {

    final private RatingCourseRepository ratingCourseRepository;

    @Override
    public void addRatingCourse(RatingCourse ratingCourse) {
        ratingCourseRepository.save(ratingCourse);
    }

    @Override
    public RatingCourseDTO getRatingCourseByCourseIdAndUserId(Integer courseId, Integer userId) {
        return ratingCourseRepository.getRatingCourseByCourseIdAndUserId(courseId, userId);
    }

    @Override
    public List<RatingCourse> getAllRating() {
        List<RatingCourse> ratingCourseList = ratingCourseRepository.findAll();
        return ratingCourseList;
    }

    @Override
    public List<RatingCourse> getRatingCourseByCourseID(Integer courseID) {
        List<RatingCourse> ratingCourseList = ratingCourseRepository.getRatingCourseByCourseId(courseID);
        return ratingCourseList;
    }

    @Override
    public void deleteRatingCourse(Integer ratingCourseID) {
        ratingCourseRepository.deleteById(ratingCourseID);
    }
}
