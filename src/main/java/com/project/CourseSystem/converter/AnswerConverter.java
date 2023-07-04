package com.project.CourseSystem.converter;

import com.project.CourseSystem.dto.AnswerDTO;
import com.project.CourseSystem.entity.Answer;
import org.springframework.stereotype.Component;

@Component
public class AnswerConverter {

    public Answer convertDtoToEntity(AnswerDTO answerDTO){
        Answer answer = new Answer();
        answer.setAnswerID(answerDTO.getAnswerID());
        answer.setContent(answerDTO.getContent());
        answer.setIsCorrect(answerDTO.getIsCorrect());
        answer.setQuestionID(answerDTO.getQuestionID());
        answer.setAnswerOrdinal(answerDTO.getAnswerOrdinal());
        return answer;
    }

    public AnswerDTO convertEntityToDto(Answer answer){
        AnswerDTO answerDTO = new AnswerDTO();
        answerDTO.setAnswerID(answer.getAnswerID());
        answerDTO.setContent(answer.getContent());
        answerDTO.setIsCorrect(answer.getIsCorrect());
        answerDTO.setQuestionID(answer.getQuestionID());
        answerDTO.setAnswerOrdinal(answer.getAnswerOrdinal());
        return answerDTO;
    }
}
