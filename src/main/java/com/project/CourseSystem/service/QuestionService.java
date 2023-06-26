package com.project.CourseSystem.service;

import com.project.CourseSystem.dto.QuestionDTO;
import com.project.CourseSystem.entity.Question;

import java.util.List;

public interface QuestionService {

    public List<QuestionDTO> getAllByQuizID(Integer quizID);
}
