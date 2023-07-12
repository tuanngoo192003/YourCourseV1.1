package com.project.CourseSystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="lesson")
public class Lesson {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name="lessonID", nullable = false)
        private Integer lessonID;

        @Column(name="lessonName", nullable = false, length = 60)
        private String lessonName;

        @Column(name="lessonDes", nullable = true, length = 5000)
        private String lessonDes;

        @ManyToOne
        @JoinColumn(name="courseID", nullable = false)
        private Course courseID;

        @ManyToOne
        @JoinColumn(name="quizID", nullable = true)
        private Quiz quizID;
}
