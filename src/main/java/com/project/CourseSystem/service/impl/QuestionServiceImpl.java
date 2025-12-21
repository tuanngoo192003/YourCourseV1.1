package com.project.CourseSystem.service.impl;

import com.project.CourseSystem.converter.QuestionConverter;
import com.project.CourseSystem.dto.QuestionDTO;
import com.project.CourseSystem.entity.Question;
import com.project.CourseSystem.repository.QuestionRepository;
import com.project.CourseSystem.service.QuestionService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    @Override
    public List<QuestionDTO> getAllByQuizID(Integer quizID) {
        return questionRepository.getAllByQuizID(quizID);
    }

    @Override
    public QuestionDTO getQuestionByContentAndQuizId(String content, Integer quizId) {
        return questionRepository.getQuestionByQuestionIdAndQuizId(content, quizId);
    }

    @Override
    public Question getQuestionById(Integer questionId) {
        return questionRepository.findById(questionId).get();
    }

    @Override
    public Question saveQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public void deleteQuestion(Question question) {
        questionRepository.delete(question);
    }

}
