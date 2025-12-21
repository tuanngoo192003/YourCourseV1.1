package com.project.CourseSystem.repository;

import com.project.CourseSystem.dto.CourseDTO;
import com.project.CourseSystem.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    @Query(value = """
            SELECT
                c.course_id as courseID,
                c.course_name as courseName,
                c.course_image as courseImage,
                c.description as courseDes,
                c.created_date as createdDate,
                c.start_date as startDate,
                c.end_date as endDate,
                c.price as price,
                jsonb_build_object(
                        'categoryID', ca.category_id,
                        'categoryName', ca.category_name
                ) as category
            FROM course c
            LEFT JOIN categories ca ON ca.category_id = c.category_id AND ca.category_id = :categoryID
            """, nativeQuery = true)
    List<CourseDTO> getAllByCategoryID(@Param("categoryID") int categoryID);

    @Query(value = """
            SELECT
                    c.course_id as courseID,
                    c.course_name as courseName,
                    c.course_image as courseImage,
                    c.description as courseDes,
                    c.created_date as createdDate,
                    c.start_date as startDate,
                    c.end_date as endDate,
                    c.price as price,
                    jsonb_build_object(
                            ca.category_id as categoryID,
                            ca.category_name as categoryName
                    ) as category
            FROM course c
            WHERE c.course_name LIKE %:courseName%
            """, nativeQuery = true)
    List<CourseDTO> getAllByCourseNameContaining(@Param("courseName") String courseName);

    @Query(value = "SELECT * FROM course WHERE ?1 = ?2", nativeQuery = true)
    Page<Course> findAllByAttribute(String attribute, String value, PageRequest of);

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE course
            SET
                course_name   = :courseName,
                course_image  = :courseImage,
                course_des    = :courseDes,
                created_date  = :createdDate,
                start_date    = :startDate,
                end_date      = :endDate,
                price         = :price,
                categoryid    = :categoryId
            WHERE courseid = :courseId
            """, nativeQuery = true)
    void updateCourse(
            @Param("courseName") String courseName,
            @Param("courseImage") String courseImage,
            @Param("courseDes") String courseDes,
            @Param("createdDate") Date createdDate,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate,
            @Param("price") Float price,
            @Param("categoryId") int categoryId,
            @Param("courseId") int courseId);

    @Query(value = """
            SELECT
                c.course_id as courseID,
                c.course_name as courseName,
                c.course_image as courseImage,
                c.description as courseDes,
                c.created_date as createdDate,
                c.start_date as startDate,
                c.end_date as endDate,
                c.price as price,
                jsonb_build_object(
                        ca.category_id as categoryID,
                        ca.category_name as categoryName
                ) as category
            FROM course c
            WHERE categoryID = :categoryID
            LIMIT 1
            """, nativeQuery = true)
    CourseDTO findFirstCourseByCategory(@Param("categoryID") Integer categoryID);
}
