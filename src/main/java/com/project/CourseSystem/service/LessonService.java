package com.project.CourseSystem.service;

import com.project.CourseSystem.dto.LessonDTO;
import com.project.CourseSystem.entity.Lesson;

import java.util.List;

public interface LessonService {

    List<LessonDTO> getAllByCourseID(int courseID);

}
