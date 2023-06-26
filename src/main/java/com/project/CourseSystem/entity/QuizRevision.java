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
    @ManyToOne
    @JoinColumn(name="questionID", nullable = false)
    private Question questionID;

    @OneToOne
    @JoinColumn(name="answerID", nullable = false)
    private Answer answerID;

    @ManyToOne
    @JoinColumn(name="reportID", nullable = false)
    private Report reportID;
}
