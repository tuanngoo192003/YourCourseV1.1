package com.project.CourseSystem.repository;

import com.project.CourseSystem.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {

    @Query(value = """
            SELECT
                a.answer_id as answerID,
                a.content as content,
                a.is_correct as isCorrect,
                a.ordinal as ordinal
            FROM answer a
            WHERE
                questionid = :questionID
            """, nativeQuery = true)
    public List<Answer> getAnswerByQuestionID(@Param("questionID") Integer questionID);

    @Query(value = """
            SELECT
                a.answer_id as answerID,
                a.content as content,
                a.is_correct as isCorrect,
                a.ordinal as ordinal
            FROM answer
            WHERE
                questionid = :questionID
                AND answer_ordinal = :ordinal
            """, nativeQuery = true)
    public Answer getAnswerByQuestionIDAndAnswerOrdinal(@Param("questionID") Integer questionID,
            @Param("ordinal") String answerOrdinal);

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE `answer`
            SET
                content = :content,
                is_correct = :isCorrect
            WHERE (answer_id = :answerID)
            """, nativeQuery = true)
    public void updateAnswer(@Param("content") String answerContent,
            @Param("isCorrect") String isCorrect,
            @Param("answerID") Integer answerID);
}
