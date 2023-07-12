package com.project.CourseSystem.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PassedStatusCheck {
    Integer checkStatus;
    String statusContent;
}
