package com.project.CourseSystem.repository;

import com.project.CourseSystem.entity.RatingCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RatingCourseRepository extends JpaRepository<RatingCourse, Integer> {

    @Query(value = "SELECT * FROM rating_course WHERE courseid = ?1 AND userid = ?2", nativeQuery = true)
    public RatingCourse getRatingCourseByCourseIdAndUserId(Integer courseId, Integer userId);
}
