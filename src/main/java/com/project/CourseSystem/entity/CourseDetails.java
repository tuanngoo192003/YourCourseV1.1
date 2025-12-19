package com.project.CourseSystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "course_details")
public class CourseDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_details_id", nullable = false)
    private Integer courseDetailsID;

    @Column(name = "content", nullable = false, length = 5000)
    private String courseDetailsContent;

    @Column(name = "requirements", nullable = false, length = 1000)
    private String courseRequirements;

    @Column(name = "description", nullable = false, length = 5000)
    private String courseDescription;

    @Column(name = "for_user", nullable = false, length = 1000)
    private String forWho;

    @Column(name = "updated_date", nullable = true)
    private Date updatedDate;

    @OneToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course courseID;
}
