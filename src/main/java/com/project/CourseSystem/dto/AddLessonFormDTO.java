package com.project.CourseSystem.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddLessonFormDTO {

    private String lessonName;

    private String lessonDes;

    private String learningMaterialName;

    private String learningMaterialDes;

    private String learningMaterialLink;
}
