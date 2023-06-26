package com.project.CourseSystem.dto;

import com.project.CourseSystem.entity.Quiz;
import com.project.CourseSystem.entity.UserInfo;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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

    private UserInfo userID;

    private Quiz quizID;
}
