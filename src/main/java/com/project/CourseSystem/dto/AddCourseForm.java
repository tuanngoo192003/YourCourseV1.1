package com.project.CourseSystem.dto;

import lombok.*;

import java.sql.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddCourseForm {

    private String courseName;

    private String courseImage;

    private String courseDes;

    private Date startDate;

    private Date endDate;

    private Float price;

    private String category;

    private String courseDetailsContent;

    private String courseRequirements;

    private String courseDescription;

    private String forWho;

}
