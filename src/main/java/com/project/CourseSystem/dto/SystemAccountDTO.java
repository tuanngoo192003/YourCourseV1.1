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

    @Email
    private String email;

    private String verificationCode;

    private Date registerDate;

    private Integer roleID;

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Component
    public static class UpdateSystemAccountDTO {
        private Integer accountID;

        private String accountName;

        private String oldPassword;

        private String newPassword;

        @Email
        private String email;

        private String verificationCode;

        private Date registerDate;

        private Integer roleID;
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Component
    public static class CreateSystemAccountDTO {
        private Integer accountID;

        private String accountName;

        private String password;

        @Email
        private String email;

        private String verificationCode;

        private Date registerDate;

        private Integer roleID;
    }
}
