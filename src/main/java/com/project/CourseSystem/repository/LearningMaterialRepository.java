package com.project.CourseSystem.repository;

import com.project.CourseSystem.dto.LearningMaterialDTO;
import com.project.CourseSystem.entity.LearningMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LearningMaterialRepository extends JpaRepository<LearningMaterial, Integer> {

    @Query(value = """
            SELECT
                lm.material_id as learningMaterialID,
                lm.material_name as learningMaterialName,
                lm.description as learningMaterialDes,
                lm.source_url as learningMaterialLink,
                json_build_object(
                    l.lesson_id as lessonID,
                    l.name as lessonName,
                    l.description as lessonDes
                ) as lesson
            FROM learning_material lm
            LEFT JOIN lesson l as l.lesson_id = lm.lesson_id
            WHERE
                lessonid = :lessonID
            """, nativeQuery = true)
    List<LearningMaterialDTO> getLearningMaterialByLessonID(@Param("lessonID") Integer lessonID);
}
