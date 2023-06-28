package com.project.CourseSystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.sql.Time;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="enrolled")
public class Enrolled {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="enrolledID", nullable = false)
    private Integer enrolledID;

    @Column(name="enrolledDate", nullable = false)
    private Date enrolledDate;

    @ManyToOne
    @JoinColumn(name="courseID", nullable = false)
    private Course courseID;

    @ManyToOne
    @JoinColumn(name="accountID", nullable = false)
    private SystemAccount accountID;

    @ManyToOne
    @JoinColumn(name="paymentID", nullable = true)
    private Payment paymentID;
}
