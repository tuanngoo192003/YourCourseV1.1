package com.project.CourseSystem.service.impl;

import com.project.CourseSystem.entity.Payment;
import com.project.CourseSystem.repository.PaymentRepository;
import com.project.CourseSystem.service.PaymentService;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<Payment> getAllPayment() {
        List<Payment> paymentList = paymentRepository.findAll();
        return paymentList;
    }

    @Override
    public List<Payment> getPaymentByMonth(int endMonth, int startMonth) {
        List<Payment> paymentList = paymentRepository.getPaymentByMonth(endMonth, startMonth);
        return paymentList;
    }

    @Override
    public List<Payment> findPaymentByUserID(Integer userID) {
        List<Payment> paymentList = paymentRepository.findPaymentByUserID(userID);
        return paymentList;
    }

}
