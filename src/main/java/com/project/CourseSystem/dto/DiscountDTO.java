package com.project.CourseSystem.dto;

import com.project.CourseSystem.entity.Course;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.sql.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiscountDTO {
    private Integer discountID;

    private Date discountStart;

    private Date discountEnd;

    private int percentage;

    private String discountContent;

    private Course courseID;
}
