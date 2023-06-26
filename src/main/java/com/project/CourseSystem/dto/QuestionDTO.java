package com.project.CourseSystem.dto;

import com.project.CourseSystem.entity.Quiz;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
