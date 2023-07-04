package com.project.CourseSystem.dto;

import com.project.CourseSystem.entity.Quiz;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionDTO {

    private Integer questionID;

    private String content;

    private Quiz quizID;
}
