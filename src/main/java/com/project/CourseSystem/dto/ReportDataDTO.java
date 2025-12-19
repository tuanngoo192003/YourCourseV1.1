package com.project.CourseSystem.dto;

import com.project.CourseSystem.entity.Quiz;
import com.project.CourseSystem.entity.UserInfo;
import lombok.*;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportDataDTO {

    private Integer reportID;

    private Integer mark;

    private Date completedDate;

    private Time timeSpent;

    private UserInfo userID;

    private Quiz quizID;

    private List<String> answers;
    
    private List<String> askedQuestions;
}
