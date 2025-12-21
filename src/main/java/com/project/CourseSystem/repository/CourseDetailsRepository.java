package com.project.CourseSystem.repository;

import com.project.CourseSystem.dto.CourseDetailsDTO;
import com.project.CourseSystem.entity.CourseDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CourseDetailsRepository extends JpaRepository<CourseDetails, Integer> {

    @Query(value = """
            SELECT
                cd.course_details_id as courseDetailsID,
                cd.course_id as courseID,
                cd.content as courseDetailsContent,
                cd.requirements as courseRequirements,
                cd.for_user as forWho,
                cd.updated_date as updatedDate
            FROM course_details cd
            WHERE
                course_id = :courseID
            """, nativeQuery = true)
    public CourseDetailsDTO findCourseDetailsByCourseID(@Param("courseID") int courseID);

    @Modifying
    @Transactional
    @Query(value = """
            DELETE FROM course_details
            WHERE
                courseid = :courseID
            """, nativeQuery = true)
    void deleteCourseDetailsByCourseID(@Param("courseID") Integer courseID);
}
