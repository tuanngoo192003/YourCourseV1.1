package com.project.CourseSystem.dto;

import com.project.CourseSystem.entity.Course;
import com.project.CourseSystem.entity.UserInfo;
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

    private Integer paymentAmount;

    private UserInfo userID;

    private Course courseID;
}
