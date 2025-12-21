package com.project.CourseSystem.repository;

import com.project.CourseSystem.dto.QuestionDTO;
import com.project.CourseSystem.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    @Query(value = """
            SELECT
                q.question_id as questionID,
                q.content as content,
                q.quiz_id as quizID
            FROM question q
            WHERE
                q.quiz_id = :quizID
            """, nativeQuery = true)
    public List<QuestionDTO> getAllByQuizID(@Param("quizID") Integer quizID);

    @Query(value = """
            SELECT
                q.question_id as questionID,
                q.content as content,
                q.quiz_id as quizID
            FROM question q
            WHERE
                q.content = :content
                q.quiz_id = :quizID
            """, nativeQuery = true)
    public QuestionDTO getQuestionByQuestionIdAndQuizId(@Param("content") String content,
            @Param("quizID") Integer quizId);
}
