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
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id", nullable = false)
    private Integer reportID;

    @Column(name = "mark", nullable = false)
    private Integer mark;

    @Column(name = "completed_date", nullable = false)
    private Date completedDate;

    @Column(name = "time_spent", nullable = true)
    private Time timeSpent;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserInfo userID;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quizID;
}
