package com.project.CourseSystem.repository;

import com.project.CourseSystem.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Integer> {

    @Query(value = "SELECT * FROM Course WHERE categoryID = ?1", nativeQuery = true)
    List<Course> getAllByCategoryID(int categoryID);

    @Query(value = "SELECT * FROM Course WHERE course_name LIKE %?1%", nativeQuery = true)
    List<Course> getAllByCourseNameContaining(String courseName);

    @Query(value = "SELECT * FROM Course WHERE ?1 = ?2", nativeQuery = true)
    Page<Course> findAllByAttribute(String attribute, String value, PageRequest of);

    @Modifying
    @Transactional
    @Query(value = "UPDATE `course` SET course_name = ?1, " +
            "course_image = ?2, course_des = ?3, created_date = ?4, start_date = ?5, end_date = ?6, price = ?7, categoryid = ?8  WHERE (courseid = ?9);", nativeQuery = true)
    void updateCourse(String courseName, String courseImage, String courseDes, Date createdDate
            , Date startDate, Date endDate, Float price, int categoryID, int courseID);
}
