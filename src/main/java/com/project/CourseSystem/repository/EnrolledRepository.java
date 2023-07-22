package com.project.CourseSystem.repository;

import com.project.CourseSystem.entity.Enrolled;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

public interface EnrolledRepository extends JpaRepository<Enrolled, Integer> {

    @Query(value = "SELECT * FROM enrolled WHERE accountID = ?1 and courseID = ?2", nativeQuery = true)
    public Enrolled findByAccountIdAndCourseID(Integer accountID, Integer courseID);

    @Query(value = "SELECT * FROM enrolled WHERE accountID = ?1", nativeQuery = true)
    public List<Enrolled> findByAccountId(Integer accountID);

    @Query(value = "SELECT * FROM enrolled WHERE paymentid = ?1", nativeQuery = true)
    List<Enrolled> findByPaymentID(Integer paymentID);

    @Query(value = "SELECT * FROM enrolled WHERE courseid = ?1", nativeQuery = true)
    List<Enrolled> findByCourseID(Integer courseID);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO enrolled (enrolled_date, accountid, courseid, paymentid) VALUES (?1, ?2, ?3, ?4)", nativeQuery = true)
    void insertEnrolled(Date enrolledDate, Integer accountID, Integer courseID, Integer paymentID);
}
