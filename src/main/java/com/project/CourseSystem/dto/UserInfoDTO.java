package com.project.CourseSystem.dto;

import com.project.CourseSystem.entity.SystemAccount;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;
import org.springframework.stereotype.Component;

import java.sql.Date;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class UserInfoDTO {
    private Integer userID;

    private String userName;

    private String aboutMe;

    private String location;

    private Date dob;

    private String phoneNums;

    private String avatar;

    private SystemAccount accountID;

}
