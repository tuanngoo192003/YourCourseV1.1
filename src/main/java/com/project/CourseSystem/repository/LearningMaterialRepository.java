package com.project.CourseSystem.repository;

import com.project.CourseSystem.entity.LearningMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LearningMaterialRepository extends JpaRepository<LearningMaterial, Integer> {
}
