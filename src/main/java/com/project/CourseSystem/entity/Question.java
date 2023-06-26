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
@Table(name="question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="questionID", nullable = false)
    private Integer questionID;

    @Column(name="content", nullable = false, length = 200)
    private String content;

    @ManyToOne
    @JoinColumn(name="quizID", nullable = false)
    private Quiz quizID;

}
