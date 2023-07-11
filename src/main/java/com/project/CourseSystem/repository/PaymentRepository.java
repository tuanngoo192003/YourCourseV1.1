package com.project.CourseSystem.repository;

import com.project.CourseSystem.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    @Query(value = "SELECT * FROM payment WHERE payment_date >= DATE_SUB(NOW(), INTERVAL ?1 MONTH) \n" +
            "AND payment_date < DATE_SUB(NOW(), INTERVAL ?2 MONTH)", nativeQuery = true)
    List<Payment> getPaymentByMonth(int endMonth, int startMonth);

    @Query(value = "SELECT * FROM payment WHERE userid = ?1", nativeQuery = true)
    List<Payment> findPaymentByUserID(Integer userID);
}
