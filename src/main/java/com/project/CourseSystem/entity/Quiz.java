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
@Table(name = "quizzes")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id", nullable = false)
    private Integer quizID;

    @Column(name = "name", nullable = false, length = 60)
    private String quizName;

    @Column(name = "description", nullable = true, length = 200)
    private String quizDes;

    @Column(name = "period", nullable = true)
    private Time quizPeriod;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course courseID;
}
