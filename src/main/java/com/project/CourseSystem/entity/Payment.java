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
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id", nullable = false)
    private Integer paymentID;

    @Column(name = "date", nullable = true)
    private Date paymentDate;

    @Column(name = "name_on_card", nullable = true)
    private String nameOnCard;

    @Column(name = "card_number", nullable = true)
    private String cardNumber;

    @Column(name = "expiry_date", nullable = true)
    private String expiryDate;

    @Column(name = "cvv", nullable = true)
    private String cvv;

    @Column(name = "status", nullable = true)
    private String status;

    @Column(name = "amount", nullable = false)
    private Float paymentAmount;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserInfo userID;
}
