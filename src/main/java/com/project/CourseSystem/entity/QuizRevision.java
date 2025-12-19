package com.project.CourseSystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "quiz_revision")
public class QuizRevision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_revision_id", nullable = false)
    private Integer quizRevisionID;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question questionID;

    @ManyToOne
    @JoinColumn(name = "answer_id", nullable = false)
    private Answer answerID;

    @ManyToOne
    @JoinColumn(name = "report_id", nullable = false)
    private Report reportID;
}
