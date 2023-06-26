package com.project.CourseSystem.service;

import com.project.CourseSystem.dto.QuizDTO;

import java.util.List;

public interface QuizService {

    QuizDTO getAllByLessonID(int courseID);
}
