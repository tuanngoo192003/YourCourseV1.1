package com.project.CourseSystem.service.impl;

import com.project.CourseSystem.converter.SystemAccountConverter;
import com.project.CourseSystem.dto.SystemAccountDTO;
import com.project.CourseSystem.entity.Role;
import com.project.CourseSystem.entity.SystemAccount;
import com.project.CourseSystem.repository.RoleRepository;
import com.project.CourseSystem.repository.SystemAccountRepository;
import com.project.CourseSystem.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    private final SystemAccountRepository systemAccountRespository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final SystemAccountConverter systemAccountConverter;

    @Autowired
    public AccountServiceImpl(SystemAccountRepository systemAccountRespository,
            RoleRepository roleRepository,
            BCryptPasswordEncoder passwordEncoder,
            SystemAccountConverter systemAccountConverter) {
        this.systemAccountRespository = systemAccountRespository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.systemAccountConverter = systemAccountConverter;
    }

    @Override
    public SystemAccountDTO findUser(String accountName) {
        return systemAccountRespository.findByAccountName(accountName);
    }

    @Override
    public void saveUser(SystemAccountDTO.CreateSystemAccountDTO systemAccountDTO) {
        String encodePassword = passwordEncoder.encode(systemAccountDTO.getPassword());

        SystemAccount systemAccountEntity = new SystemAccount();
        systemAccountEntity.setAccountName(systemAccountDTO.getAccountName());
        systemAccountEntity.setAccountPassword(encodePassword);
        systemAccountEntity.setEmail(systemAccountDTO.getEmail());
        systemAccountEntity.setVerificationCode("");
        systemAccountEntity.setRegisterDate(systemAccountDTO.getRegisterDate());

        Role role = roleRepository.findById(systemAccountDTO.getRoleID()).get();
        systemAccountEntity.setRole(role);

        systemAccountRespository.save(systemAccountEntity);
    }

    @Override
    public void updateUser(SystemAccountDTO.UpdateSystemAccountDTO systemAccountDTO) {
        SystemAccount systemAccountEntity = systemAccountRespository.findById(systemAccountDTO.getAccountID()).get();
        if (systemAccountDTO.getOldPassword() != "" && systemAccountEntity.getAccountPassword() != null) {
            if (!passwordEncoder.matches(systemAccountDTO.getOldPassword(), systemAccountEntity.getAccountPassword())) {
                // throw new Exception("old password not correct!");

                // TODO: error handling

                return;
            }

            String encodeNewPassword = passwordEncoder.encode(systemAccountDTO.getNewPassword());
            systemAccountEntity.setAccountPassword(encodeNewPassword);
        }
        if (systemAccountDTO.getEmail() != "") {
            systemAccountEntity.setEmail(systemAccountDTO.getEmail());
        }
        if (systemAccountDTO.getVerificationCode() != "") {
            systemAccountEntity.setVerificationCode(systemAccountDTO.getVerificationCode());
        }

        systemAccountRespository.save(systemAccountEntity);
    }

    @Override
    public boolean isGmailExist(String gmail) {
        SystemAccountDTO systemAccountDTO = systemAccountRespository.findByGmail(gmail);

        return !Objects.isNull(systemAccountDTO);
    }

    @Override
    public SystemAccountDTO findByGmail(String gmail) {
        SystemAccountDTO systemAccountDTO = systemAccountRespository.findByGmail(gmail);
        if (Objects.isNull(systemAccountDTO)) {

            return null;
        }

        return systemAccountDTO;
    }

    @Override
    public SystemAccountDTO findByVerificationCode(String verificationCode) {
        SystemAccountDTO systemAccountDTO = systemAccountRespository.findByVerificationCode(verificationCode);
        if (Objects.isNull(systemAccountDTO)) {

            return null;
        }

        return systemAccountDTO;
    }

    @Override
    public List<SystemAccountDTO> getAllAccount() {
        return systemAccountRespository.findAllAccount();
    }

    @Override
    public List<SystemAccountDTO> getRecentRegisterAccount(int numberOfWeek) {
        return systemAccountRespository.findRecentRegisterAccount(numberOfWeek);
    }

    @Override
    public Page<SystemAccountDTO> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        // Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
        // Sort.by(sortField).ascending()
        // : Sort.by(sortField).descending();
        // return system_accountRespository.findAll(PageRequest.of(pageNo - 1, pageSize,
        // sort));

        return null;
    }

    @Override
    public SystemAccountDTO findAccountByID(Integer accountID) {
        SystemAccount systemAccountEntity = systemAccountRespository.findById(accountID).get();
        if (Objects.isNull(systemAccountEntity)) {

            return null;
        }

        return systemAccountConverter.convertEntityToDTO(systemAccountEntity);
    }

    public boolean isUsernameExist(String account_name) {
        SystemAccountDTO systemAccountDTO = systemAccountRespository.findByAccountName(account_name);
        if (Objects.isNull(systemAccountDTO)) {

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
