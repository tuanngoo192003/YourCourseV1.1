package com.project.CourseSystem.service.impl;

import com.project.CourseSystem.converter.LessonConverter;
import com.project.CourseSystem.dto.LessonDTO;
import com.project.CourseSystem.entity.Lesson;
import com.project.CourseSystem.repository.LessonRepository;
import com.project.CourseSystem.service.LessonService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {

    LessonRepository lessonRepository;

    LessonConverter lessonConverter;

    public LessonServiceImpl(LessonRepository lessonRepository, LessonConverter lessonConverter) {
        this.lessonRepository = lessonRepository;
        this.lessonConverter = lessonConverter;
    }

    @Override
    public List<LessonDTO> getAllByCourseID(int courseID) {
        List<Lesson> lessonList = lessonRepository.findAllByCourseID(courseID);
        List<LessonDTO> lessonDTOList = new ArrayList<>();
        for (Lesson lesson : lessonList) {
            lessonDTOList.add(lessonConverter.convertEntityToDto(lesson));
        }
        return lessonDTOList;
    }

    @Override
    public Lesson saveLesson(Lesson lesson) {
       return lessonRepository.save(lesson);
    }
}
