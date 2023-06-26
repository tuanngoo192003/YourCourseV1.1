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

    QuestionConverter questionConverter;
    QuestionRepository questionRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository, QuestionConverter questionConverter){
        this.questionRepository = questionRepository;
        this.questionConverter = questionConverter;
    }

    @Override
    public List<QuestionDTO> getAllByQuizID(Integer quizID) {
        List<Question> questions = questionRepository.getAllByQuizID(quizID);
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        if(questions!=null){
            for(Question question : questions){
                QuestionDTO questionDTO = new QuestionDTO();
                questionDTO = questionConverter.convertEntityToDto(question);
                questionDTOS.add(questionDTO);
            }
            return questionDTOS;
        }
        else{
            return questionDTOS;
        }
    }
}
