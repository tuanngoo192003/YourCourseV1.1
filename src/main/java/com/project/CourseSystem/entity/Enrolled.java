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
@Table(name = "enrolled")
public class Enrolled {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrolled_id", nullable = false)
    private Integer enrolledID;

    @Column(name = "enrolled_date", nullable = false)
    private Date enrolledDate;

    @ManyToOne
    @JoinColumn(name = "count_id", nullable = false)
    private Course courseID;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private SystemAccount accountID;

    @ManyToOne
    @JoinColumn(name = "payment_id", nullable = true)
    private Payment paymentID;
}
