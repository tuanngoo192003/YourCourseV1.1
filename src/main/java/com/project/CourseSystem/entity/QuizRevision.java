package com.project.CourseSystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="quizRevision")
public class QuizRevision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="quizRevisionID", nullable = false)
    private Integer quizRevisionID;

    @ManyToOne
    @JoinColumn(name="questionID", nullable = false)
    private Question questionID;

    @ManyToOne
    @JoinColumn(name="answerID", nullable = false)
    private Answer answerID;

    @ManyToOne
    @JoinColumn(name="reportID", nullable = false)
    private Report reportID;
}
