package com.project.CourseSystem.converter;

import com.project.CourseSystem.dto.QuestionDTO;
import com.project.CourseSystem.entity.Question;
import org.springframework.stereotype.Component;

@Component
public class QuestionConverter {

    public Question convertDtoToEntity(QuestionDTO questionDTO){
        Question question = new Question();
        question.setQuestionID(questionDTO.getQuestionID());
        question.setContent(questionDTO.getContent());
        question.setQuizID(questionDTO.getQuizID());
        return question;
    }

    public QuestionDTO convertEntityToDto(Question question){
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setQuestionID(question.getQuestionID());
        questionDTO.setContent(question.getContent());
        questionDTO.setQuizID(question.getQuizID());
        return questionDTO;
    }

}
