package com.project.CourseSystem.repository;

import com.project.CourseSystem.entity.CourseDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CourseDetailsRepository extends JpaRepository<CourseDetails, Integer>{

    @Query(value = "SELECT * FROM course_details WHERE courseid = ?1", nativeQuery = true)
    public CourseDetails findCourseDetailsByCourseID(int courseID);
}
