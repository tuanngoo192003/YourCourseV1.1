package com.project.CourseSystem.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionDTO {

    private Integer questionID;

    private String content;

    private Integer quizID;
}
