package com.project.CourseSystem.service;

import com.project.CourseSystem.entity.Enrolled;

import java.util.List;

public interface EnrolledService {

    Enrolled findByAccountIdAndCourseID(Integer accountID, Integer courseID);

    List<Enrolled> findByAccountId(Integer accountID);

    void addEnrolled(List<Enrolled> enrolled);

    List<Enrolled> getEnrolledByPaymentID(Integer paymentID);

    List<Enrolled> getAllByCourseID(Integer courseID);

    void deleteEnrolled(Integer enrolledID);

    void saveEnrolled(Enrolled enrolled);
}
