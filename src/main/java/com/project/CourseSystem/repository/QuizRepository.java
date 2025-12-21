package com.project.CourseSystem.repository;

import com.project.CourseSystem.dto.QuizDTO;
import com.project.CourseSystem.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {

    @Query(value = """
            SELECT
                q.quiz_id as quizID,
                q.name as quizName,
                q.description as quizDes,
                q.period as quizPeriod
            FROM quizzes q
            WHERE
                courseid = :courseID
            """, nativeQuery = true)
    List<QuizDTO> findAllByCourseID(@Param("courseID") Integer courseID);
}
