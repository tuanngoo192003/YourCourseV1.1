package com.project.CourseSystem.repository;

import com.project.CourseSystem.dto.ReportDTO;
import com.project.CourseSystem.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

    @Query(value = """
                SELECT
                    r.report_id as reportID,
                    r.mark as mark,
                    r.completed_date as completedDate,
                    r.time_spent as timeSpent,
                    r.user_id as userID,
                    r.quiz_id as quizID
                FROM report r
                WHERE
                    r.user_id = :userID
            """, nativeQuery = true)
    List<ReportDTO> findAllByUserID(@Param("userID") Integer userID);

    @Query(value = """
                SELECT
                    r.report_id as reportID,
                    r.mark as mark,
                    r.completed_date as completedDate,
                    r.time_spent as timeSpent,
                    r.user_id as userID,
                    r.quiz_id as quizID
                FROM report r
                WHERE
                    r.quiz_id = :quizID
            """, nativeQuery = true)
    List<ReportDTO> findAllByQuizID(@Param("quizID") Integer quizID);
}
