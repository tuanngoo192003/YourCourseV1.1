package com.project.CourseSystem.converter;

import org.springframework.stereotype.Component;

import com.project.CourseSystem.dto.PaymentDTO;
import com.project.CourseSystem.entity.Payment;

@Component
public class PaymentConverter {

    public PaymentDTO convertFromEntityToDTO(Payment payment) {
        if (payment == null) {
            return null;
        }

        return PaymentDTO.builder()
                .paymentID(payment.getPaymentID())
                .paymentDate(payment.getPaymentDate())
                .nameOnCard(payment.getNameOnCard())
                .cardNumber(payment.getCardNumber())
                .expiryDate(payment.getExpiryDate())
                .cvv(payment.getCvv())
                .status(payment.getStatus())
                .paymentAmount(
                        payment.getPaymentAmount() != null
                                ? payment.getPaymentAmount().intValue()
                                : null)
                .userID(
                        payment.getUser() != null
                                ? payment.getUser().getUserID()
                                : null)
                .courseID(null) // no course field in Payment entity
                .build();
    }
}
