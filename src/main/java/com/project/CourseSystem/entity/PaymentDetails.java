package com.project.CourseSystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="paymentDetails")
public class PaymentDetails {

    @Id
    @Column(name="paymentDetailsID", nullable = false)
    private Integer paymentDetailsID;

    @ManyToOne(optional = false)
    @JoinColumn(name="paymentID", nullable = false)
    private Payment paymentID;

    @ManyToOne(optional = false)
    @JoinColumn(name="courseID", nullable = false)
    private Course courseID;


}
