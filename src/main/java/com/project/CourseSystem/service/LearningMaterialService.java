package com.project.CourseSystem.service;

import com.project.CourseSystem.entity.LearningMaterial;

import java.util.List;

public interface LearningMaterialService {

    List<LearningMaterial> getLearningMaterialByLessonID(Integer lessonID);

    void saveLearningMaterial(LearningMaterial learningMaterial);
}
