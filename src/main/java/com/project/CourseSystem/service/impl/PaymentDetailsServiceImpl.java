package com.project.CourseSystem.service.impl;

import com.project.CourseSystem.converter.PaymentDetailsConverter;
import com.project.CourseSystem.dto.PaymentDetailsDTO;
import com.project.CourseSystem.entity.PaymentDetails;
import com.project.CourseSystem.repository.PaymentDetailsRepository;
import com.project.CourseSystem.service.PaymentDetailsService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentDetailsServiceImpl implements PaymentDetailsService {

    final private PaymentDetailsRepository paymentDetailsRepository;

    private final PaymentDetailsConverter paymentDetailsConverter;

    @Override
    public List<PaymentDetailsDTO> getAllPaymentDetailsByPaymentID(Integer paymentID) {
        return paymentDetailsRepository.findAllByPaymentID(paymentID);
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
    public List<PaymentDetailsDTO> getAllPaymentDetails() {
        List<PaymentDetailsDTO> paymentDetailsList = new ArrayList<>();
        paymentDetailsRepository.findAll().forEach(paymentDetails -> {
            paymentDetailsList.add(paymentDetailsConverter.convertEntityToDTO(paymentDetails));
        });

        return paymentDetailsList;
    }

    @Override
    public List<PaymentDetailsDTO> getAllByCourseID(Integer courseID) {
        return paymentDetailsRepository.findAllByCourseID(courseID);
    }
}
