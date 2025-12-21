package com.project.CourseSystem.repository;

import com.project.CourseSystem.dto.RatingCourseDTO;
import com.project.CourseSystem.entity.RatingCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingCourseRepository extends JpaRepository<RatingCourse, Integer> {

    @Query(value = """
            SELECT
                rc.rating_id as ratingID,
                rc.stars_rated as rating,
                rc.comment as comment,
                rc.course_id as courseID,
                rc.user_id as userID
            FROM rating_course rc
            WHERE
                rc.course_id = :courseID
                AND rc.user_id = :userID
            """, nativeQuery = true)
    public RatingCourseDTO getRatingCourseByCourseIdAndUserId(@Param("courseID") Integer courseId,
            @Param("userID") Integer userId);

    @Query(value = """
                SELECT
                    rc.rating_id as ratingID,
                    rc.stars_rated as rating,
                    rc.comment as comment,
                    rc.course_id as courseID,
                    rc.user_id as userID
                FROM rating_course rc
                WHERE
                    rc.course_id = :courseID
            """, nativeQuery = true)
    List<RatingCourse> getRatingCourseByCourseId(@Param("courseID") Integer courseID);
}
