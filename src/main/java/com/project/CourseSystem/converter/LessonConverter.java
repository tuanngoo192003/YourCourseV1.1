package com.project.CourseSystem.converter;

import com.project.CourseSystem.dto.LessonDTO;
import com.project.CourseSystem.entity.Lesson;
import org.springframework.stereotype.Component;

@Component
public class LessonConverter {

    public Lesson convertDtoToEntity(LessonDTO lessonDTO) {
        Lesson lesson = new Lesson();
        lesson.setLessonID(lessonDTO.getLessonID());
        lesson.setLessonName(lessonDTO.getLessonName());
        lesson.setLessonDes(lesson.getLessonDes());
        lesson.setCourseID(lessonDTO.getCourseID());
        lesson.setQuizID(lessonDTO.getQuizID());
        return lesson;
    }

    public LessonDTO convertEntityToDto(Lesson lesson){
        LessonDTO lessonDTO = new LessonDTO();
        lessonDTO.setLessonID(lesson.getLessonID());
        lessonDTO.setLessonName(lesson.getLessonName());
        lessonDTO.setLessonDes(lesson.getLessonDes());
        lessonDTO.setCourseID(lesson.getCourseID());
        lessonDTO.setQuizID(lesson.getQuizID());
        return lessonDTO;
    }
}
