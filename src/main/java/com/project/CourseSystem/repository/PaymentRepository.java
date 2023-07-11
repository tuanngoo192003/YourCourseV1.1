package com.project.CourseSystem.repository;

import com.project.CourseSystem.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    @Query(value = "SELECT * FROM payment WHERE payment_date >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL ?1 MONTH), '%Y-%m-01') AND payment_date < DATE_FORMAT(DATE_SUB(DATE_SUB(NOW(), INTERVAL ?1 MONTH), INTERVAL 1 DAY), '%Y-%m-%d')", nativeQuery = true)
    List<Payment> getPaymentByMonth(int startMonth, int endMonth);
}
