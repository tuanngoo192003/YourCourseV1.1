package com.project.CourseSystem.repository;

import com.project.CourseSystem.entity.QuizRevision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRevisionRepository extends JpaRepository<QuizRevision, Integer> {

    @Query(value = """
            SELECT
                qr.quiz_revision_id as quizRevisionID,
                qr.question_id as questionID,
                qr.answer_id as answerID,
                qr.report_id as reportID
            FROM
                quiz_revision qr
            WHERE
                qr.report_id= :reportID
            """, nativeQuery = true)
    List<QuizRevision> getAllByReportID(@Param("reportID") Integer reportID);
}
