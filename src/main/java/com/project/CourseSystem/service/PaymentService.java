package com.project.CourseSystem.service;

import com.project.CourseSystem.entity.Payment;

import java.util.List;

public interface PaymentService {

    void addPaymentForOne(Payment payment);

    Payment addPayment(Payment payment);

    List<Payment> getAllPayment();

    List<Payment> getPaymentByMonth(int endMonth, int startMonth);

    List<Payment> findPaymentByUserID(Integer userID);

    Payment getPaymentByID(int paymentID);

    void updatePayment(Payment payment);

    List<Payment> getAllConfirmedPayment();
}
