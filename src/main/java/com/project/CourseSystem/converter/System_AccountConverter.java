package com.project.CourseSystem.converter;

import com.project.CourseSystem.dto.SystemAccountDTO;
import com.project.CourseSystem.entity.SystemAccount;
import org.springframework.stereotype.Component;

@Component
public class System_AccountConverter {

    public SystemAccountDTO convertEntityToDTO(SystemAccount system_account){
        SystemAccountDTO systemAccountDTO = new SystemAccountDTO();
        systemAccountDTO.setAccountID(system_account.getAccountID());
        systemAccountDTO.setAccountName(system_account.getAccountName());
        systemAccountDTO.setAccountPassword(system_account.getAccountPassword());
        systemAccountDTO.setGmail(system_account.getGmail());
        systemAccountDTO.setVerificationCode(system_account.getVerificationCode());
        systemAccountDTO.setRegisterDate(system_account.getRegisterDate());
        systemAccountDTO.setRoleID(system_account.getRoleID());
        return systemAccountDTO;
    }

    public SystemAccount convertDTOToEntity(SystemAccountDTO systemAccountDTO){
        SystemAccount system_account = new SystemAccount();
        system_account.setAccountID(systemAccountDTO.getAccountID());
        system_account.setAccountName(systemAccountDTO.getAccountName());
        system_account.setAccountPassword(systemAccountDTO.getAccountPassword());
        system_account.setGmail(systemAccountDTO.getGmail());
        system_account.setVerificationCode(systemAccountDTO.getVerificationCode());
        system_account.setRegisterDate(systemAccountDTO.getRegisterDate());
        system_account.setRoleID(systemAccountDTO.getRoleID());
        return system_account;
    }
}
