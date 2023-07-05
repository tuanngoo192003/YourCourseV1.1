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

    List<QuestionDTO> questionDTOS;

    List<AnswerDTO> answerDTOS;
}
