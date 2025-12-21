package com.project.CourseSystem.converter;

import org.springframework.stereotype.Component;

import com.project.CourseSystem.dto.PaymentDetailsDTO;
import com.project.CourseSystem.entity.PaymentDetails;

@Component
public class PaymentDetailsConverter {

    public PaymentDetailsDTO convertEntityToDTO(PaymentDetails paymentDetails) {
        PaymentDetailsDTO paymentDetailsDTO = new PaymentDetailsDTO();
        paymentDetailsDTO.setPaymentDetailsID(paymentDetails.getPaymentDetailsID());
        paymentDetailsDTO.setCourseID(paymentDetails.getCourse().getCourseID());
        paymentDetailsDTO.setPaymentID(paymentDetails.getPayment().getPaymentID());

        return paymentDetailsDTO;
    }
}
