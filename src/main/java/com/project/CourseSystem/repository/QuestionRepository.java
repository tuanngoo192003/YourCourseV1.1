package com.project.CourseSystem.repository;

import com.project.CourseSystem.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    @Query(value = "SELECT * FROM question WHERE quizid = ?1", nativeQuery = true)
    public List<Question> getAllByQuizID(Integer quizID);
}
