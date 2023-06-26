package com.project.CourseSystem.dto;

import com.project.CourseSystem.entity.Course;
import com.project.CourseSystem.entity.Payment;
import com.project.CourseSystem.entity.SystemAccount;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.sql.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnrolledDTO {

    private Integer enrolledID;

    private Date enrolledDate;

    private Course courseID;

    private SystemAccount accountID;

    private Payment paymentID;
}
