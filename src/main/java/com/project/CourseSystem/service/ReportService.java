package com.project.CourseSystem.service;

import com.project.CourseSystem.entity.Report;

import java.util.List;

public interface ReportService {

    Report getReportByReportID(Integer reportID);

    void saveReport(Report report);

    List<Report> getAllReportByUserID(Integer userID);

    List<Report> getAllByQuizID(Integer quizID);

    void deleteReport(Integer reportID);
}
