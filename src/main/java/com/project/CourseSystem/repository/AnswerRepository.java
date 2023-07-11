package com.project.CourseSystem.repository;

import com.project.CourseSystem.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {

    @Query(value = "SELECT * FROM answer WHERE questionid = ?1", nativeQuery = true)
    public List<Answer> getAnswerByQuestionID(Integer questionID);

    @Query(value = "SELECT * FROM answer WHERE questionid = ?1 AND answer_ordinal = ?2", nativeQuery = true)
    public Answer getAnswerByQuestionIDAndAnswerOrdinal(Integer questionID, String answerOrdinal);

    @Query(value = "SELECT * FROM answer WHERE answerid = ?1", nativeQuery = true)
    public Answer getAnswerByID(Integer id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE `answer` SET content = ?1, is_correct = ?2 WHERE (answerid = ?3);", nativeQuery = true)
    public void updateAnswer(String answerContent, String isCorrect, Integer answerID);
}
