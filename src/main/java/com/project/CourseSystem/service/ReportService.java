package com.project.CourseSystem.service;

import com.project.CourseSystem.entity.Report;

public interface ReportService {

    Report getReportByReportID(Integer reportID);

    void saveReport(Report report);
}
