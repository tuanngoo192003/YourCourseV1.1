package com.project.CourseSystem.service.impl;

import com.project.CourseSystem.converter.PaymentConverter;
import com.project.CourseSystem.dto.PaymentDTO;
import com.project.CourseSystem.entity.Payment;
import com.project.CourseSystem.repository.PaymentRepository;
import com.project.CourseSystem.service.PaymentService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    private final PaymentConverter paymentConverter;

    @Override
    public void addPaymentForOne(Payment payment) {
        paymentRepository.save(payment);
    }

    @Override
    public Payment addPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public List<PaymentDTO> getAllPayment() {
        List<PaymentDTO> paymentList = new ArrayList<>();
        paymentRepository.findAll().forEach(payment -> {
            paymentList.add(paymentConverter.convertFromEntityToDTO(payment));
        });

        return paymentList;
    }

    @Override
    public List<PaymentDTO> getPaymentByMonth(int endMonth, int startMonth) {
        return paymentRepository.getPaymentByMonth(endMonth, startMonth, "Confirm");
    }

    @Override
    public List<PaymentDTO> findPaymentByUserID(Integer userID) {
        return paymentRepository.findPaymentByUserID(userID);
    }

    @Override
    public PaymentDTO getPaymentByID(int paymentID) {
        Payment payment = paymentRepository.findById(paymentID).get();
        if (Objects.isNull(payment)) {
            // TODO: error handling

            return null;
        }

        return paymentConverter.convertFromEntityToDTO(payment);
    }

    @Override
    public void updatePayment(Integer paymentID, String paymentStatus) {
        Payment payment = paymentRepository.findById(paymentID).get();
        if (Objects.isNull(payment)) {
            // TODO: error handling

            return;
        }

        if (paymentStatus.equals(payment.getStatus())) {
            // TODO: error handling
        }
        if (payment.getStatus().equals("Reject")) {
            // TODO: error handling
        }

        payment.setStatus(paymentStatus);
        payment.setPaymentDate(new java.sql.Date(System.currentTimeMillis()));

        paymentRepository.save(payment);
    }

    @Override
    public List<PaymentDTO> getAllConfirmedPayment() {
        return paymentRepository.getAllConfirmedPayment("Confirm");
    }

}
