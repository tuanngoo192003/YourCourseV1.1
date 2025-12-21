package com.project.CourseSystem.service.impl;

import com.project.CourseSystem.entity.Report;
import com.project.CourseSystem.repository.ReportRepository;
import com.project.CourseSystem.service.ReportService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    final private ReportRepository reportRepository;

    ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public Report getReportByReportID(Integer reportID) {
        return reportRepository.findById(reportID).get();
    }

    @Override
    public void saveReport(Report report) {
        reportRepository.save(report);
    }

    @Override
    public List<Report> getAllReportByUserID(Integer userID) {
        return reportRepository.findAllByUserID(userID);
    }

    @Override
    public List<Report> getAllByQuizID(Integer quizID) {
        return reportRepository.findAllByQuizID(quizID);
    }

    @Override
    public void deleteReport(Integer reportID) {
        reportRepository.deleteById(reportID);
    }
}
