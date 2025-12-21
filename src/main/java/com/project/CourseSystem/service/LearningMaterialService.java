package com.project.CourseSystem.service;

import com.project.CourseSystem.dto.LearningMaterialDTO;

import java.util.List;

public interface LearningMaterialService {

    List<LearningMaterialDTO> getLearningMaterialByLessonID(Integer lessonID);

    void saveLearningMaterial(LearningMaterialDTO learningMaterial);

    void deleteLearningMaterial(Integer learningMaterialID);
}
