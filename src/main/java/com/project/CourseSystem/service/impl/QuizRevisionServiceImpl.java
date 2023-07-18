package com.project.CourseSystem.service.impl;

import com.project.CourseSystem.entity.QuizRevision;
import com.project.CourseSystem.repository.QuizRevisionRepository;
import com.project.CourseSystem.service.QuizRevisionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizRevisionServiceImpl implements QuizRevisionService {

    QuizRevisionRepository quizRevisionRepository;

    QuizRevisionServiceImpl(QuizRevisionRepository quizRevisionRepository){
        this.quizRevisionRepository = quizRevisionRepository;
    }

    @Override
    public void saveQuizRevision(QuizRevision quizRevision) {
        quizRevisionRepository.save(quizRevision);
    }

    @Override
    public List<QuizRevision> getQuizRevisionByReportID(Integer reportID) {
        return quizRevisionRepository.getAllByReportID(reportID);
    }

    @Override
    public void deleteQuizRevision(Integer quizRevisionID) {
        quizRevisionRepository.deleteById(quizRevisionID);
    }
}
