package com.project.CourseSystem.service.impl;

import com.project.CourseSystem.entity.Payment;
import com.project.CourseSystem.repository.PaymentRepository;
import com.project.CourseSystem.service.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    PaymentRepository paymentRepository;



    PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public void addPaymentForOne(Payment payment) {
        paymentRepository.save(payment);
    }

}
