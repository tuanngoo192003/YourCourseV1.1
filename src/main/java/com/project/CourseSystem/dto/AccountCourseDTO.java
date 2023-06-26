package com.project.CourseSystem.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountCourseDTO {

    private Integer accountID;

    private Integer courseID;
}
