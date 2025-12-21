package com.project.CourseSystem.service.impl;

import com.project.CourseSystem.dto.LearningMaterialDTO;
import com.project.CourseSystem.entity.LearningMaterial;
import com.project.CourseSystem.entity.Lesson;
import com.project.CourseSystem.repository.LearningMaterialRepository;
import com.project.CourseSystem.repository.LessonRepository;
import com.project.CourseSystem.service.LearningMaterialService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LearningMaterialServiceImpl implements LearningMaterialService {

    private final LessonRepository lessonRepository;

    private final LearningMaterialRepository learningMaterialRepository;

    @Override
    public List<LearningMaterialDTO> getLearningMaterialByLessonID(Integer lessonID) {
        return learningMaterialRepository.getLearningMaterialByLessonID(lessonID);
    }

    @Override
    public void saveLearningMaterial(LearningMaterialDTO learningMaterialDTO) {
        Lesson lesson = lessonRepository.findById(learningMaterialDTO.getLessonID()).get();
        if (Objects.isNull(lesson)) {
            // TODO: error handling

            return;
        }

        LearningMaterial learningMaterial = new LearningMaterial();
        learningMaterial.setLessonID(lesson);
        learningMaterial.setLearningMaterialName(learningMaterialDTO.getLearningMaterialName());
        learningMaterial.setLearningMaterialDes(learningMaterialDTO.getLearningMaterialDes());
        learningMaterial.setLearningMaterialLink(learningMaterialDTO.getLearningMaterialLink());

        learningMaterialRepository.save(learningMaterial);
    }

    @Override
    public void deleteLearningMaterial(Integer learningMaterialID) {
        learningMaterialRepository.deleteById(learningMaterialID);
    }
}
