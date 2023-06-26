package com.project.CourseSystem.service.impl;

import com.project.CourseSystem.converter.AnswerConverter;
import com.project.CourseSystem.dto.AnswerDTO;
import com.project.CourseSystem.entity.Answer;
import com.project.CourseSystem.repository.AnswerRepository;
import com.project.CourseSystem.service.AnswerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {

    AnswerRepository answerRepository;

    AnswerConverter answerConverter;

    public AnswerServiceImpl(AnswerRepository answerRepository, AnswerConverter answerConverter){

        this.answerRepository = answerRepository;
        this.answerConverter = answerConverter;
    }

    @Override
    public List<AnswerDTO> getAllByQuestionId(Integer questionId) {
        List<Answer> answerList = answerRepository.getAnswerByQuestionID(questionId);
        List<AnswerDTO> answerDTOList = new ArrayList<>();
        if(answerList!= null){
            for(Answer answer : answerList){
                AnswerDTO answerDTO = new AnswerDTO();
                answerDTO = answerConverter.convertEntityToDto(answer);
                answerDTOList.add(answerDTO);
            }
            return answerDTOList;
        }
        else {
            return answerDTOList;
        }
    }
}
