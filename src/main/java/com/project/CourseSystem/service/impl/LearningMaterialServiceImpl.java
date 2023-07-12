package com.project.CourseSystem.service.impl;

import com.project.CourseSystem.entity.LearningMaterial;
import com.project.CourseSystem.repository.LearningMaterialRepository;
import com.project.CourseSystem.service.LearningMaterialService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LearningMaterialServiceImpl implements LearningMaterialService {
    LearningMaterialRepository learningMaterialRepository;

    public LearningMaterialServiceImpl(LearningMaterialRepository learningMaterialRepository) {
        this.learningMaterialRepository = learningMaterialRepository;
    }

    @Override
    public List<LearningMaterial> getLearningMaterialByLessonID(Integer lessonID) {
        List<LearningMaterial> learningMaterials = new ArrayList<>();
        learningMaterialRepository.getLearningMaterialByLessonID(lessonID).forEach(learningMaterials::add);
        return learningMaterials;
    }

    @Override
    public void saveLearningMaterial(LearningMaterial learningMaterial) {
        learningMaterialRepository.save(learningMaterial);
    }

    @Override
    public void deleteLearningMaterial(Integer learningMaterialID) {
        learningMaterialRepository.deleteById(learningMaterialID);
    }
}
