package com.project.CourseSystem.dto;

import jakarta.persistence.Column;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO {
    private Integer categoryID;
    private String categoryName;
}
