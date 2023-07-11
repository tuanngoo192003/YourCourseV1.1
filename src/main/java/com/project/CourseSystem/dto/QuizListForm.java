package com.project.CourseSystem.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizListForm {
    QuizDTO quizDTO;

    String questionContents;

    String answerContents;
}
