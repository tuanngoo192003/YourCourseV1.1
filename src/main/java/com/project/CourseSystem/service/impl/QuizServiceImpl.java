package com.project.CourseSystem.service.impl;

import com.project.CourseSystem.converter.QuizConverter;
import com.project.CourseSystem.dto.QuizDTO;
import com.project.CourseSystem.entity.Quiz;
import com.project.CourseSystem.repository.LessonRepository;
import com.project.CourseSystem.repository.QuizRepository;
import com.project.CourseSystem.service.QuizService;
import org.springframework.stereotype.Service;

@Service
public class QuizServiceImpl implements QuizService {

    private QuizRepository quizRepository;

    private LessonRepository lessonRepository;

    private QuizConverter quizConverter;

    public QuizServiceImpl(QuizRepository quizRepository, LessonRepository lessonRepository, QuizConverter quizConverter) {
        this.lessonRepository = lessonRepository;
        this.quizRepository = quizRepository;
        this.quizConverter = quizConverter;
    }

    @Override
    public QuizDTO getAllByLessonID(int lessonID) {
        Integer quizID = lessonRepository.getQuizIDByLessonID(lessonID);
        if(quizID != null){
            int id = quizID.intValue();
            Quiz quiz = quizRepository.findByID(id);
            QuizDTO quizDTO = quizConverter.convertEntityToDto(quiz);
            return quizDTO;
        }
        else{
            return null;
        }
    }

    @Override
    public Quiz getQuizById(int quizID) {
        return quizRepository.findByID(quizID);
    }
}
