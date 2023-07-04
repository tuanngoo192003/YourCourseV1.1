package com.project.CourseSystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="answer")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="answerID", nullable = false)
    private Integer answerID;

    @Column(name="content", nullable = false, length = 200)
    private String content;

    @Column(name="isCorrect", nullable = false)
    private String isCorrect;

    @Column(name="answerOrdinal", nullable = true)
    private String answerOrdinal;

    @ManyToOne
    @JoinColumn(name="questionID", nullable = false)
    private Question questionID;


}
