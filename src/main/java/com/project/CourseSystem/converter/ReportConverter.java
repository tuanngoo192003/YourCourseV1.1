package com.project.CourseSystem.converter;

import com.project.CourseSystem.dto.ReportDTO;
import com.project.CourseSystem.entity.Report;

public class ReportConverter {

    public Report convertDTOToEntity(ReportDTO reportDto) {
        Report report = new Report();
        report.setReportID(reportDto.getReportID());
        report.setCompletedDate(reportDto.getCompletedDate());
        report.setMark(reportDto.getMark());
        report.setTimeSpent(reportDto.getTimeSpent());
        report.setQuizID(reportDto.getQuizID());
        report.setUserID(reportDto.getUserID());

        return report;
    }

    public ReportDTO convertEntityToDTO(Report report){
        ReportDTO reportDto = new ReportDTO();
        reportDto.setReportID(report.getReportID());
        reportDto.setCompletedDate(report.getCompletedDate());
        reportDto.setMark(report.getMark());
        reportDto.setTimeSpent(report.getTimeSpent());
        reportDto.setQuizID(report.getQuizID());
        reportDto.setUserID(report.getUserID());

        return reportDto;
    }
}
