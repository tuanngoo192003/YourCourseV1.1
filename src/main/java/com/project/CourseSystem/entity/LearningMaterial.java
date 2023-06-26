package com.project.CourseSystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="learningMaterial")
public class LearningMaterial {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name="materialID", nullable = false)
        private Integer learningMaterialID;

        @Column(name="materialName", nullable = false, length = 60)
        private String learningMaterialName;

        @Column(name="materialDes", nullable = true, length = 200)
        private String learningMaterialDes;

        @Column(name="lmUrl", nullable = false, length = 200)
        private String learningMaterialLink;

        @ManyToOne
        @JoinColumn(name="lessonID", nullable = false)
        private Lesson lessonID;
}
