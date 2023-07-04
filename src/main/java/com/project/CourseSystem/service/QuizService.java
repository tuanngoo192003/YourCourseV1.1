package com.project.CourseSystem.service;

import com.project.CourseSystem.dto.QuizDTO;

public interface QuizService {

    QuizDTO getAllByLessonID(int courseID);
}
