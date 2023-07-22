package com.project.CourseSystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="payment")
public class Payment {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name="paymentID", nullable = false)
        private Integer paymentID;

        @Column(name="paymentDate", nullable = true)
        private Date paymentDate;

        @Column(name="nameOnCard", nullable = true)
        private String nameOnCard;

        @Column(name="cardNumber", nullable = true)
        private String cardNumber;

        @Column(name="expiryDate", nullable = true)
        private String expiryDate;

        @Column(name="cvv", nullable = true)
        private String cvv;

        @Column(name="status", nullable = true)
        private String status;

        @Column(name="paymentAmount", nullable = false)
        private Float paymentAmount;

        @ManyToOne(optional = false)
        @JoinColumn(name="userID", nullable = false)
        private UserInfo userID;

}
