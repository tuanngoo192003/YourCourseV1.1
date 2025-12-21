package com.project.CourseSystem.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerDTO {

    private Integer answerID;

    private String content;

    private String isCorrect;

    private String answerOrdinal;

    private Integer questionID;
}
