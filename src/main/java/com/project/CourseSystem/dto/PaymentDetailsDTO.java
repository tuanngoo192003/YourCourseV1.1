package com.project.CourseSystem.dto;

import com.project.CourseSystem.entity.Course;
import com.project.CourseSystem.entity.Payment;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDetailsDTO {

    private Integer paymentDetailsID;

    private Payment payment;

    private Course course;
}
