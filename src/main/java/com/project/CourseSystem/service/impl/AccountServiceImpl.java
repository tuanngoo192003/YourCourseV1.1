package com.project.CourseSystem.service.impl;

import com.project.CourseSystem.converter.System_AccountConverter;
import com.project.CourseSystem.dto.SystemAccountDTO;
import com.project.CourseSystem.entity.SystemAccount;
import com.project.CourseSystem.repository.SystemAccountCRUDRepository;
import com.project.CourseSystem.repository.SystemAccountRepository;
import com.project.CourseSystem.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    final private SystemAccountCRUDRepository system_accountCRUDRepository;
    final private System_AccountConverter system_accountConverter;
    final private SystemAccountRepository system_accountRespository;

    @Autowired
    public AccountServiceImpl(SystemAccountRepository system_accountRespository,
            System_AccountConverter system_accountConverter,
            SystemAccountCRUDRepository system_accountCRUDRepository) {
        this.system_accountRespository = system_accountRespository;
        this.system_accountConverter = system_accountConverter;
        this.system_accountCRUDRepository = system_accountCRUDRepository;

    }

    @Override
    public SystemAccountDTO findUser(String account_name, String account_password) {
        SystemAccountDTO system_accountDTO = new SystemAccountDTO();
        SystemAccount system_accountEntity = system_accountRespository.findByAccountName(account_name);
        if (system_accountEntity == null) {
            return system_accountDTO;
        } else {
            system_accountDTO.setAccountID(system_accountEntity.getAccountID());
            system_accountDTO.setAccountName(system_accountEntity.getAccountName());
            system_accountDTO.setAccountPassword(system_accountEntity.getAccountPassword());
            system_accountDTO.setGmail(system_accountEntity.getGmail());
            system_accountDTO.setRoleID(system_accountEntity.getRoleID());
            return system_accountDTO;
        }
    }

    @Override
    public SystemAccountDTO findUserByAccountName(String account_name) {
        SystemAccount systemAccount = system_accountRespository.findByAccount_name(account_name);
        if (systemAccount == null) {
            SystemAccountDTO systemAccountDTO = new SystemAccountDTO();
            return systemAccountDTO;
        } else {
            SystemAccountDTO systemAccountDTO = system_accountConverter.convertEntityToDTO(systemAccount);
            return systemAccountDTO;
        }
    }

    @Override
    public void saveUser(SystemAccountDTO system_accountDTO) {
        SystemAccount system_accountEntity = new SystemAccount();
        system_accountEntity.setAccountName(system_accountDTO.getAccountName());
        system_accountEntity.setAccountPassword(system_accountDTO.getAccountPassword());
        system_accountEntity.setGmail(system_accountDTO.getGmail());
        system_accountEntity.setVerificationCode("");
        system_accountEntity.setRoleID(system_accountDTO.getRoleID());
        system_accountEntity.setRegisterDate(system_accountDTO.getRegisterDate());
        system_accountRespository.save(system_accountEntity);
    }

    @Override
    public void updateUser(SystemAccountDTO system_accountDTO) {
        int id = system_accountDTO.getAccountID();
        String newPassword = system_accountDTO.getAccountPassword();
        SystemAccount system_accountEntity = system_accountRespository.findById(id).get();
        system_accountEntity.setAccountPassword(newPassword);
        system_accountDTO = system_accountConverter.convertEntityToDTO(system_accountEntity);
        system_accountDTO.setAccountPassword(newPassword);
        system_accountCRUDRepository.save(system_accountEntity);
    }

    @Override
    public boolean isGmailExist(String gmail) {
        SystemAccount system_accountEntity = system_accountRespository.findByGmail(gmail);
        if (system_accountEntity == null) {
            return false;
        }
        return true;
    }

    public SystemAccount findByGmail(String gmail) {
        return system_accountRespository.findByGmail(gmail);
    }

    @Override
    public SystemAccount findByVerificationCode(String verificationCode) {
        return system_accountRespository.findByVerificationCode(verificationCode);
    }

    @Override
    public void updateGmail(String gmail, String accountName) {
        SystemAccount system_accountEntity = system_accountRespository.findByAccount_name(accountName);
        system_accountEntity.setGmail(gmail);
        system_accountRespository.save(system_accountEntity);
    }

    @Override
    public void updateVerifyCode(String verificationCode, String accountName) {
        SystemAccount system_accountEntity = system_accountRespository.findByAccount_name(accountName);
        system_accountEntity.setVerificationCode(verificationCode);
        system_accountRespository.save(system_accountEntity);
    }

    @Override
    public List<SystemAccount> getAllAccount() {
        List<SystemAccount> systemAccountList = system_accountRespository.findAll();
        return systemAccountList;
    }

    @Override
    public List<SystemAccount> getRecentRegisterAccount(int numberOfWeek) {
        List<SystemAccount> systemAccountList = system_accountRespository.findRecentRegisterAccount(numberOfWeek);
        return systemAccountList;
    }

    @Override
    public Page<SystemAccount> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();
        return system_accountRespository.findAll(PageRequest.of(pageNo - 1, pageSize, sort));
    }

    @Override
    public SystemAccount findAccountByID(Integer accountID) {
        SystemAccount systemAccount = system_accountRespository.findById(accountID).get();
        return systemAccount;
    }

    public boolean isUsernameExist(String account_name) {
        SystemAccount system_accountEntity = system_accountRespository.findByAccount_name(account_name);
        if (system_accountEntity == null) {
            return false;
        }
        return true;
    }

    @Override
    public String generateVerificationCode() {
        String randomString = UUID.randomUUID().toString();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            if (randomString.charAt(i) != '-') {
                stringBuilder.append(randomString.charAt(i));
            }
        }
        return stringBuilder.toString();
    }

}
