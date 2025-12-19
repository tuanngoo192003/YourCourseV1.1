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
@Table(name = "discount")
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "discount_id", nullable = false)
    private Integer discountID;

    @Column(name = "start_date", nullable = false)
    private Date discountStart;

    @Column(name = "end_date", nullable = false)
    private Date discountEnd;

    @Column(name = "percentage", nullable = false)
    private int percentage;

    @Column(name = "content", nullable = true, length = 255)
    private String discountContent;

    @OneToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course courseID;
}
