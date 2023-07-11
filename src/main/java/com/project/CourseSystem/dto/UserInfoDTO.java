package com.project.CourseSystem.dto;

import com.project.CourseSystem.entity.SystemAccount;
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

    private String gender;

    private String avatar;

    private SystemAccount accountID;

}
