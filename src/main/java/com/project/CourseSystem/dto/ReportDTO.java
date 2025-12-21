package com.project.CourseSystem.dto;

import lombok.*;

import java.sql.Date;
import java.sql.Time;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportDTO {

    private Integer reportID;

    private Integer mark;

    private Date completedDate;

    private Time timeSpent;

    private Integer userID;

    private Integer quizID;
}
