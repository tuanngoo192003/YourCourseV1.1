package com.project.CourseSystem.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDetailsDTO {

    private Integer paymentDetailsID;

    private Integer paymentID;

    private Integer courseID;
}
