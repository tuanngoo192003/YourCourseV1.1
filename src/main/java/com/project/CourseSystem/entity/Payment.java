package com.project.CourseSystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.Set;

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

        @Column(name="paymentDate", nullable = false)
        private Date paymentDate;

        @Column(name="paymentAmount", nullable = false)
        private Integer paymentAmount;

        @ManyToOne(optional = false)
        @JoinColumn(name="userID", nullable = false)
        private UserInfo userID;

        @ManyToMany
        @JoinTable(
                name = "paymentCourse",
                joinColumns = @JoinColumn(name = "paymentID"),
                inverseJoinColumns = @JoinColumn(name = "courseID"))
        private Set<Course> courseID;
}
