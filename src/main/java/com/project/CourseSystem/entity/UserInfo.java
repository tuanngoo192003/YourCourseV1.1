package com.project.CourseSystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "user")
public class UserInfo {

    @Id
    @Column(name = "user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userID;

    @Column(name = "username", nullable = true, length = 20)
    private String userName;

    @Column(name = "about_me", nullable = true, length = 200)
    private String aboutMe;

    @Column(name = "location", nullable = true, length = 100)
    private String location;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "gender", nullable = true, length = 10)
    private String gender;

    @Column(name = "phone_number", nullable = true, length = 11)
    private String phoneNums;

    @Column(name = "avatar", nullable = true, length = 255)
    private String avatar;

    @OneToOne
    @JoinColumn(name = "account_id", nullable = false)
    private SystemAccount accountID;
}
