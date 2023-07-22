package com.project.CourseSystem.service;

import com.project.CourseSystem.entity.PaymentDetails;

import java.util.List;

public interface PaymentDetailsService {

    List<PaymentDetails> getAllPaymentDetailsByPaymentID(Integer paymentID);

    void savePaymentDetails(PaymentDetails paymentDetails);

    void deletePaymentDetails(Integer paymentDetailsID);

    List<PaymentDetails> getAllPaymentDetails();

    List<PaymentDetails> getAllByCourseID(Integer courseID);
}
