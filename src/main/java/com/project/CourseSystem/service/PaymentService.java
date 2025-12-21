package com.project.CourseSystem.service;

import com.project.CourseSystem.dto.PaymentDTO;
import com.project.CourseSystem.entity.Payment;

import java.util.List;

public interface PaymentService {

    void addPaymentForOne(Payment payment);

    Payment addPayment(Payment payment);

    List<PaymentDTO> getAllPayment();

    List<PaymentDTO> getPaymentByMonth(int endMonth, int startMonth);

    List<PaymentDTO> findPaymentByUserID(Integer userID);

    PaymentDTO getPaymentByID(int paymentID);

    void updatePayment(Integer paymentID, String paymentStatus);

    List<PaymentDTO> getAllConfirmedPayment();
}
