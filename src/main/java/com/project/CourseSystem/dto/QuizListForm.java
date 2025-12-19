package com.project.CourseSystem.dto;

import lombok.*;

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
