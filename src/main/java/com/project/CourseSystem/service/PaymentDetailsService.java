package com.project.CourseSystem.service;

import com.project.CourseSystem.dto.PaymentDetailsDTO;
import com.project.CourseSystem.entity.PaymentDetails;

import java.util.List;

public interface PaymentDetailsService {

    List<PaymentDetailsDTO> getAllPaymentDetailsByPaymentID(Integer paymentID);

    void savePaymentDetails(PaymentDetails paymentDetails);

    void deletePaymentDetails(Integer paymentDetailsID);

    List<PaymentDetailsDTO> getAllPaymentDetails();

    List<PaymentDetailsDTO> getAllByCourseID(Integer courseID);
}
