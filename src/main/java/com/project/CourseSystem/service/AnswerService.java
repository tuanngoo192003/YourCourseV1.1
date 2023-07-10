package com.project.CourseSystem.service;

import com.project.CourseSystem.dto.AnswerDTO;
import com.project.CourseSystem.entity.Answer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AnswerService {

    public Answer getById(Integer id);

    public List<AnswerDTO> getAllByQuestionId(Integer questionId);

    public Answer getAnswerByQuestionIDAndAnswerOrdinal(Integer questionID, String answerOrdinal);

    public void save(Answer answer);

    void deleteAnswer(Answer answer);
}
