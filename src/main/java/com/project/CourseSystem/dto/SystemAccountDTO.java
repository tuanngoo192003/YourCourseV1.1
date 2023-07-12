package com.project.CourseSystem.dto;

import com.project.CourseSystem.entity.Role;
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
    private Role roleID;

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
