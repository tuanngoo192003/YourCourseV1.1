package com.project.CourseSystem.repository;

import com.project.CourseSystem.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {

    @Query(value = "SELECT * FROM answer WHERE questionid = ?1", nativeQuery = true)
    public List<Answer> getAnswerByQuestionID(Integer questionID);
}
