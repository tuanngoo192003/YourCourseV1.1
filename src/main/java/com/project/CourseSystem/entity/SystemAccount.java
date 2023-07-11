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
@Table(name="systemAccount")
public class SystemAccount {
    @Id
    @Column(name="accountID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer accountID;

    @Column(name="accountName", nullable = false, length = 30)
    private String accountName;

    @Column(name="accountPassword", nullable = false, length = 200)
    private String accountPassword;

    @Email
    @Column(name="gmail", nullable = false, length = 50)
    private String gmail;

    @Column(name="verificationCode", nullable = true, length = 6)
    private String verificationCode;

    @Column(name="registerDate", nullable = true)
    private Date registerDate;

    @ManyToOne
    @JoinColumn(name="roleID", nullable = false)
    private Role roleID;

}
