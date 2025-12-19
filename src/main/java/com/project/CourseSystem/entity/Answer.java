package com.project.CourseSystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "answers")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id", nullable = false)
    private Integer answerID;

    @Column(name = "content", nullable = false, length = 200)
    private String content;

    @Column(name = "is_correct", nullable = false)
    private String isCorrect;

    @Column(name = "ordinal", nullable = true)
    private String answerOrdinal;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question questionID;
}
