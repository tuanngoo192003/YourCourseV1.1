package com.project.CourseSystem.dto;

import com.project.CourseSystem.entity.Category;
import lombok.*;

import java.sql.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDTO {
    
    private Integer courseID;

    private String courseName;

    private String courseImage;

    private String courseDes;

    private Date createdDate;

    private Date startDate;

    private Date endDate;

    private Float price;

    private Category categoryID;

}
