package com.project.CourseSystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "system_accounts")
public class SystemAccount {

    @Id
    @Column(name = "account_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer accountID;

    @Column(name = "account_name", nullable = false, length = 30)
    private String accountName;

    @Column(name = "password", nullable = false, length = 200)
    private String accountPassword;

    @Email
    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "verification_code", nullable = true, length = 6)
    private String verificationCode;

    @Column(name = "register_date", nullable = true)
    private Date registerDate;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

}
