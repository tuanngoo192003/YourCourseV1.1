package com.project.CourseSystem.dto;

import lombok.*;

import java.sql.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDTO {

    private Integer paymentID;

    private Date paymentDate;

    private String nameOnCard;

    private String cardNumber;

    private String expiryDate;

    private String cvv;

    private String status;

    private Integer paymentAmount;

    private Integer userID;

    private Integer courseID;
}
