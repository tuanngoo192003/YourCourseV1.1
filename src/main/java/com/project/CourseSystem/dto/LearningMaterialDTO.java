package com.project.CourseSystem.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LearningMaterialDTO {

    private Integer learningMaterialID;

    private String learningMaterialName;

    private String learningMaterialDes;

    private String learningMaterialLink;

    private Integer lessonID;
}
