package com.project.CourseSystem.service.impl;

import com.project.CourseSystem.entity.PaymentDetails;
import com.project.CourseSystem.repository.PaymentDetailsRepository;
import com.project.CourseSystem.service.PaymentDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class PaymentDetailsServiceImpl implements PaymentDetailsService {

    PaymentDetailsRepository paymentDetailsRepository;

    PaymentDetailsServiceImpl(PaymentDetailsRepository paymentDetailsRepository) {
        this.paymentDetailsRepository = paymentDetailsRepository;
    }

    @Override
    public List<PaymentDetails> getAllPaymentDetailsByPaymentID(Integer paymentID) {
        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAllByPaymentID(paymentID);

        return paymentDetailsList;
    }

    @Override
    public void savePaymentDetails(PaymentDetails paymentDetails) {
        paymentDetailsRepository.save(paymentDetails);
    }

    @Override
    public void deletePaymentDetails(Integer paymentDetailsID) {
        paymentDetailsRepository.deleteById(paymentDetailsID);
    }

    @Override
    public List<PaymentDetails> getAllPaymentDetails() {
        List<PaymentDetails> paymentDetailsList = new ArrayList<>();
        paymentDetailsRepository.findAll().forEach(paymentDetailsList::add);

        return paymentDetailsList;
    }

    @Override
    public List<PaymentDetails> getAllByCourseID(Integer courseID) {
        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAllByCourseID(courseID);

        return paymentDetailsList;
    }
}
