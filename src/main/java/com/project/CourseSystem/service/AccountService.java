package com.project.CourseSystem.service;

import com.project.CourseSystem.dto.SystemAccountDTO;
import com.project.CourseSystem.entity.SystemAccount;

public interface AccountService {
    public SystemAccountDTO findUser(String account_name, String account_password);

    public SystemAccountDTO findUserByAccountName(String account_name);

    public void saveUser(SystemAccountDTO system_accountDTO);

    public void updateUser(SystemAccountDTO system_accountDTO);

    boolean isGmailExist(String gmail);

    boolean isUsernameExist(String account_name);

    public String generateVerificationCode();

    public SystemAccount findByGmail(String gmail);

    SystemAccount findByVerificationCode(String verificationCode);

    void updateGmail(String gmail, String accountName);

    void updateVerifyCode(String verificationCode, String accountName);
}
