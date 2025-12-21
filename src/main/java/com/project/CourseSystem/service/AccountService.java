package com.project.CourseSystem.service;

import com.project.CourseSystem.dto.SystemAccountDTO;
import com.project.CourseSystem.entity.SystemAccount;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AccountService {
    public SystemAccountDTO findUser(String accountName);

    public void saveUser(SystemAccountDTO.CreateSystemAccountDTO system_accountDTO);

    public void updateUser(SystemAccountDTO.UpdateSystemAccountDTO system_accountDTO);

    boolean isGmailExist(String gmail);

    boolean isUsernameExist(String account_name);

    public String generateVerificationCode();

    public SystemAccountDTO findByGmail(String gmail);

    SystemAccountDTO findByVerificationCode(String verificationCode);

    List<SystemAccountDTO> getAllAccount();

    List<SystemAccountDTO> getRecentRegisterAccount(int numberOfWeek);

    Page<SystemAccountDTO> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

    SystemAccountDTO findAccountByID(Integer accountID);
}
