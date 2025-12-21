package com.project.CourseSystem.service;

import com.project.CourseSystem.dto.QuizDTO;
import com.project.CourseSystem.entity.Quiz;

import java.util.List;

public interface QuizService {

    QuizDTO getAllByLessonID(int courseID);

    Quiz getQuizById(int quizID);

    Quiz saveQuiz(Quiz quiz);

    void deleteQuiz(Quiz quiz);

    List<QuizDTO> getAllByCourseID(Integer courseID);
}
