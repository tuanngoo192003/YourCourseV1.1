package com.project.CourseSystem.repository;

import com.project.CourseSystem.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Integer> {

    @Query(value = "SELECT * FROM Report WHERE reportid = ?1", nativeQuery = true)
    Report findByReportID(Integer reportID);

    @Query(value = "SELECT * FROM Report WHERE userid = ?1", nativeQuery = true)
    List<Report> findAllByUserID(Integer userID);

    @Query(value = "SELECT * FROM Report WHERE quizid = ?1", nativeQuery = true)
    List<Report> findAllByQuizID(Integer quizID);
}
