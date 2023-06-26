package com.project.CourseSystem.service.impl;

import com.project.CourseSystem.entity.Enrolled;
import com.project.CourseSystem.repository.EnrolledRepository;
import com.project.CourseSystem.service.EnrolledService;
import org.springframework.stereotype.Service;

@Service
public class EnrolledServiceImpl implements EnrolledService {

    EnrolledRepository enrolledRepository;

    public EnrolledServiceImpl(EnrolledRepository enrolledRepository) {
        this.enrolledRepository = enrolledRepository;
    }

    @Override
    public Enrolled findByAccountIdAndCourseID(Integer accountID, Integer courseID) {
        return enrolledRepository.findByAccountIdAndCourseID(accountID, courseID);
    }
}
