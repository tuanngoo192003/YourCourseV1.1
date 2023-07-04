package com.project.CourseSystem.dto;

import lombok.*;

import java.sql.Time;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizDTO {

    private Integer quizID;

    private String quizName;

    private String quizDes;

    private Time quizPeriod;
}
