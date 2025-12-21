package com.project.CourseSystem.dto;

import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class SystemAccountDTO {

    private Integer accountID;

    private String accountName;

    private String accountPassword;

    @Email
    private String gmail;

    private String verificationCode;

    private Date registerDate;

    private Integer roleID;
}
