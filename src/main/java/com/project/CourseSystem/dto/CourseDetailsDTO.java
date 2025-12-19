package com.project.CourseSystem.dto;

import com.project.CourseSystem.entity.Course;
import lombok.*;

import java.sql.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDetailsDTO {
    
    private Integer courseDetailsID;

    private String courseDetailsContent;

    private String courseRequirements;

    private String courseDescription;

    private String forWho;

    private Date updatedDate;

    private Course courseID;
}
