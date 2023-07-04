package com.project.CourseSystem.repository;

import com.project.CourseSystem.entity.QuizRevision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuizRevisionRepository extends JpaRepository<QuizRevision, Integer> {

    @Query(value = "SELECT * from quiz_revision where reportid=?1", nativeQuery = true)
    List<QuizRevision> getAllByReportID(Integer reportID);
}
