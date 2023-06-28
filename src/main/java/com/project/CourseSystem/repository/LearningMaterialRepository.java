package com.project.CourseSystem.repository;

import com.project.CourseSystem.entity.LearningMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LearningMaterialRepository extends JpaRepository<LearningMaterial, Integer> {

    @Query(value = "select * from learning_material where lessonid = ?1", nativeQuery = true)
    List<LearningMaterial> getLearningMaterialByLessonID(Integer lessonID);
}
