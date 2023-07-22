package com.project.CourseSystem.repository;

import com.project.CourseSystem.entity.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails, Integer> {

    @Query(value = "SELECT * FROM payment_details WHERE paymentid = ?1", nativeQuery = true)
    List<PaymentDetails> findAllByPaymentID(Integer paymentID);

    @Query(value = "SELECT * FROM payment_details WHERE courseid = ?1", nativeQuery = true)
    List<PaymentDetails> findAllByCourseID(Integer courseID);
}
