package com.project.CourseSystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_id", nullable = false)
    private Integer lessonID;

    @Column(name = "name", nullable = false, length = 60)
    private String lessonName;

    @Column(name = "description", nullable = true, length = 5000)
    private String lessonDes;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course courseID;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = true)
    private Quiz quizID;
}
