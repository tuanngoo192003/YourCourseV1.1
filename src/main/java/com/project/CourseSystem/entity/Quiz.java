package com.project.CourseSystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="quiz")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="quizID", nullable = false)
    private Integer quizID;

    @Column(name="quizName", nullable = false, length = 60)
    private String quizName;

    @Column(name="quizDes", nullable = true, length = 200)
    private String quizDes;

    @Column(name="quizPeriod", nullable = true)
    private Time quizPeriod;

    @ManyToOne
    @JoinColumn(name="courseID", nullable = false)
    private Course courseID;
}
