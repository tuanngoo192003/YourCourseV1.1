package com.project.CourseSystem.dto;

import com.project.CourseSystem.entity.Course;
import com.project.CourseSystem.entity.Quiz;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LessonDTO {

    private Integer lessonID;

    private String lessonName;

    private String lessonDes;

    private Course course;

    private Quiz quiz;

}
