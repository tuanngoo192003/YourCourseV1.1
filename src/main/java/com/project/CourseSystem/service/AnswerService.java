package com.project.CourseSystem.service;

import com.project.CourseSystem.dto.AnswerDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AnswerService {

    public List<AnswerDTO> getAllByQuestionId(Integer questionId);
}
