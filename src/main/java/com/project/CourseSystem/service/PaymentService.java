package com.project.CourseSystem.service;

import com.project.CourseSystem.entity.Payment;

import java.util.List;

public interface PaymentService {

    void addPaymentForOne(Payment payment);

    List<Payment> getAllPayment();

    List<Payment> getPaymentByMonth(int endMonth, int startMonth);
}
