package com.project.CourseSystem.repository;

import com.project.CourseSystem.dto.DiscountDTO;
import com.project.CourseSystem.entity.Discount;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Integer> {

    @Query(value = """
            SELECT
                d.discount_id   AS discountID,
                d.start_date    AS discountStart,
                d.end_date      AS discountEnd,
                d.percentage    AS percentage,
                d.content       AS discountContent,
                json_build_object(
                    'courseId', c.courseid,
                    'courseName', c.course_name,
                    'courseImage', c.course_image
                )::text         AS course
            FROM discount d
            JOIN course c ON d.course_id = c.courseid
            WHERE d.course_id = :courseId
            """, nativeQuery = true)
    DiscountDTO getDiscountByCourseId(@Param("courseId") Integer courseId);

    @Query(value = """
            SELECT
                d.discount_id   AS discountID,
                d.start_date    AS discountStart,
                d.end_date      AS discountEnd,
                d.percentage    AS percentage,
                d.content       AS discountContent,
                json_build_object(
                    'courseId', c.courseid,
                    'courseName', c.course_name,
                    'courseImage', c.course_image
                )::text         AS course
            FROM discount d
            JOIN course c ON d.course_id = c.courseid
            """, nativeQuery = true)
    List<DiscountDTO> findAllDiscount();

    @Query(value = """
            SELECT
                d.discount_id   AS discountID,
                d.start_date    AS discountStart,
                d.end_date      AS discountEnd,
                d.percentage    AS percentage,
                d.content       AS discountContent,
                json_build_object(
                    'courseId', c.courseid,
                    'courseName', c.course_name,
                    'courseImage', c.course_image
                )::text         AS course
            FROM discount d
            JOIN course c ON d.course_id = c.courseid
            WHERE
                end_date >= :today
            """, nativeQuery = true)
    List<DiscountDTO> findAllExpiredDiscount(@Param("today") String today);
}
