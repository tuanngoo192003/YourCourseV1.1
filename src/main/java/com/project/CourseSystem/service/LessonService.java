package com.project.CourseSystem.service;

import com.project.CourseSystem.dto.LessonDTO;

import java.util.List;

public interface LessonService {

    List<LessonDTO> getAllByCourseID(int courseID);

}
