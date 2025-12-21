package com.project.CourseSystem.converter;

import com.project.CourseSystem.dto.SystemAccountDTO;
import com.project.CourseSystem.entity.SystemAccount;
import org.springframework.stereotype.Component;

@Component
public class SystemAccountConverter {

    public SystemAccountDTO convertEntityToDTO(SystemAccount systemAccount) {
        SystemAccountDTO systemAccountDTO = new SystemAccountDTO();
        systemAccountDTO.setAccountID(systemAccount.getAccountID());
        systemAccountDTO.setAccountName(systemAccount.getAccountName());
        systemAccountDTO.setEmail(systemAccount.getEmail());
        systemAccountDTO.setVerificationCode(systemAccount.getVerificationCode());
        systemAccountDTO.setRegisterDate(systemAccount.getRegisterDate());
        systemAccountDTO.setRoleID(systemAccount.getRole().getRoleID());

        return systemAccountDTO;
    }
}
