package com.project.CourseSystem.converter;

import com.project.CourseSystem.dto.QuizDTO;
import com.project.CourseSystem.entity.Quiz;
import org.springframework.stereotype.Component;

@Component
public class QuizConverter {

    public Quiz convertDtoToEntity(QuizDTO quizDTO){
        Quiz quiz = new Quiz();
        quiz.setQuizID(quizDTO.getQuizID());
        quiz.setQuizName(quizDTO.getQuizName());
        quiz.setQuizDes(quizDTO.getQuizDes());
        quiz.setQuizPeriod(quizDTO.getQuizPeriod());
        return quiz;
    }

    public QuizDTO convertEntityToDto(Quiz quiz){
        QuizDTO quizDTO = new QuizDTO();
        quizDTO.setQuizID(quiz.getQuizID());
        quizDTO.setQuizName(quiz.getQuizName());
        quizDTO.setQuizDes(quiz.getQuizDes());
        quizDTO.setQuizPeriod(quiz.getQuizPeriod());

        return quizDTO;
    }
}
