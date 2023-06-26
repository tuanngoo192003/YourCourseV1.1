package com.project.CourseSystem.repository;

import com.project.CourseSystem.entity.Enrolled;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EnrolledRepository extends JpaRepository<Enrolled, Integer> {

    @Query(value = "SELECT * FROM enrolled WHERE accountID = ?1 and courseID = ?2", nativeQuery = true)
    public Enrolled findByAccountIdAndCourseID(Integer accountID, Integer courseID);
}
