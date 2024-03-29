package com.project.CourseSystem.entity;


import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="report")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="reportID", nullable = false)
    private Integer reportID;

    @Column(name="mark", nullable = false)
    private Integer mark;

    @Column(name="completedDate", nullable = false)
    private Date completedDate;

    @Column(name="timeSpent", nullable = true)
    private Time timeSpent;

    @ManyToOne
    @JoinColumn(name="userID", nullable = false)
    private UserInfo userID;

    @ManyToOne
    @JoinColumn(name="quizID", nullable = false)
    private Quiz quizID;
}
