package com.project.CourseSystem.converter;

import com.project.CourseSystem.dto.EnrolledDTO;
import com.project.CourseSystem.entity.Enrolled;
import org.springframework.stereotype.Component;

@Component
public class EnrolledConverter {

    public Enrolled convertDtoToEntity(Enrolled enrolledDTO) {
        Enrolled enrolled = new Enrolled();
        enrolled.setAccountID(enrolledDTO.getAccountID());
        enrolled.setCourseID(enrolledDTO.getCourseID());
        enrolled.setEnrolledID(enrolledDTO.getEnrolledID());
        enrolled.setEnrolledDate(enrolledDTO.getEnrolledDate());
        enrolled.setPaymentID(enrolledDTO.getPaymentID());
        return enrolled;
    }

    public EnrolledDTO convertEntityToDTO(Enrolled enrolled) {
        EnrolledDTO enrolledDTO = new EnrolledDTO();
        enrolledDTO.setAccountID(enrolled.getAccountID());
        enrolledDTO.setCourseID(enrolled.getCourseID());
        enrolledDTO.setEnrolledID(enrolled.getEnrolledID());
        enrolledDTO.setEnrolledDate(enrolled.getEnrolledDate());
        enrolledDTO.setPaymentID(enrolled.getPaymentID());
        return enrolledDTO;
    }
}
