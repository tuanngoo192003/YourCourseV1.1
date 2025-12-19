package com.project.CourseSystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "learning_materials")
public class LearningMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "material_id", nullable = false)
    private Integer learningMaterialID;

    @Column(name = "material_name", nullable = false, length = 60)
    private String learningMaterialName;

    @Column(name = "description", nullable = true, length = 200)
    private String learningMaterialDes;

    @Column(name = "source_url", nullable = false, length = 200)
    private String learningMaterialLink;

    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lessonID;
}
