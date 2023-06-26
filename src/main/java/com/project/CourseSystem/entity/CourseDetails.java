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
@Table(name="courseDetails")
public class CourseDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="courseDetailsID", nullable = false)
    private Integer courseDetailsID;

    @Column(name="courseDetailsContent", nullable = false, length = 5000)
    private String courseDetailsContent;

    @Column(name="courseRequirements", nullable = false, length = 1000)
    private String courseRequirements;

    @Column(name="courseDescription", nullable = false, length = 5000)
    private String courseDescription;

    @Column(name="forWho", nullable = false, length = 1000)
    private String forWho;

    @Column(name="updatedDate", nullable = true)
    private Date updatedDate;

    @OneToOne
    @JoinColumn(name="courseID", nullable = false)
    private Course courseID;
}
