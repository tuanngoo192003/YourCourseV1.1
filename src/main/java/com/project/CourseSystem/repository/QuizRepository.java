package com.project.CourseSystem.repository;

import com.project.CourseSystem.dto.QuizDTO;
import com.project.CourseSystem.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Integer> {

    @Query(value = "SELECT * FROM quiz WHERE quizid = ?1", nativeQuery = true)
    Quiz findByID(int quizID);
}
