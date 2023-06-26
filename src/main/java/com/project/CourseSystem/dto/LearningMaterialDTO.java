package com.project.CourseSystem.dto;

import com.project.CourseSystem.entity.Lesson;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    private Lesson lessonID;
}
