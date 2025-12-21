package com.project.CourseSystem.service.impl;

import com.project.CourseSystem.converter.QuestionConverter;
import com.project.CourseSystem.dto.QuestionDTO;
import com.project.CourseSystem.entity.Question;
import com.project.CourseSystem.repository.QuestionRepository;
import com.project.CourseSystem.service.QuestionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    final private QuestionConverter questionConverter;
    final private QuestionRepository questionRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository, QuestionConverter questionConverter) {
        this.questionRepository = questionRepository;
        this.questionConverter = questionConverter;
    }

    @Override
    public List<QuestionDTO> getAllByQuizID(Integer quizID) {
        List<Question> questions = questionRepository.getAllByQuizID(quizID);
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        if (questions != null) {
            for (Question question : questions) {
                QuestionDTO questionDTO = new QuestionDTO();
                questionDTO = questionConverter.convertEntityToDto(question);
                questionDTOS.add(questionDTO);
            }
            return questionDTOS;
        } else {
            return questionDTOS;
        }
    }

    @Override
    public Question getQuestionByContentAndQuizId(String content, Integer quizId) {
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
