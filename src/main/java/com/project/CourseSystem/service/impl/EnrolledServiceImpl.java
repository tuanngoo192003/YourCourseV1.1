package com.project.CourseSystem.service.impl;

import com.project.CourseSystem.entity.Enrolled;
import com.project.CourseSystem.repository.EnrolledRepository;
import com.project.CourseSystem.service.EnrolledService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrolledServiceImpl implements EnrolledService {

    final private EnrolledRepository enrolledRepository;

    public EnrolledServiceImpl(EnrolledRepository enrolledRepository) {
        this.enrolledRepository = enrolledRepository;
    }

    @Override
    public Enrolled findByAccountIdAndCourseID(Integer accountID, Integer courseID) {
        return enrolledRepository.findByAccountIdAndCourseID(accountID, courseID);
    }

    @Override
    public List<Enrolled> findByAccountId(Integer accountID) {
        return enrolledRepository.findByAccountId(accountID);
    }

    @Override
    public void addEnrolled(List<Enrolled> enrolled) {
        for (Enrolled value : enrolled) {
            enrolledRepository.insertEnrolled(value.getEnrolledDate(), value.getAccountID().getAccountID(), value.getCourseID().getCourseID(), value.getPaymentID().getPaymentID());
        }
    }

    @Override
    public List<Enrolled> getEnrolledByPaymentID(Integer paymentID) {
        return enrolledRepository.findByPaymentID(paymentID);
    }

    @Override
    public List<Enrolled> getAllByCourseID(Integer courseID) {
        return enrolledRepository.findByCourseID(courseID);
    }

    @Override
    public void deleteEnrolled(Integer enrolledID) {
        enrolledRepository.deleteById(enrolledID);
    }

    @Override
    public void saveEnrolled(Enrolled enrolled) {
        enrolledRepository.save(enrolled);
    }
}
