package com.project.CourseSystem.service;

import com.project.CourseSystem.entity.QuizRevision;

import java.util.List;

public interface QuizRevisionService {

    void saveQuizRevision(QuizRevision quizRevision);

    List<QuizRevision> getQuizRevisionByReportID(Integer reportID);
}
