package com.project.CourseSystem.service.impl;

import com.project.CourseSystem.entity.Report;
import com.project.CourseSystem.repository.ReportRepository;
import com.project.CourseSystem.service.ReportService;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {

    ReportRepository reportRepository;

    ReportServiceImpl(ReportRepository reportRepository){
        this.reportRepository = reportRepository;
    }

    @Override
    public Report getReportByReportID(Integer reportID) {
        return reportRepository.findByReportID(reportID);
    }

    @Override
    public void saveReport(Report report) {
        reportRepository.save(report);
    }
}
