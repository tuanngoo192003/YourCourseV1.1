package com.project.CourseSystem.repository;

import com.project.CourseSystem.entity.SystemAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface SystemAccountRepository extends JpaRepository<SystemAccount, Integer> {
    @Query(value = "SELECT * FROM system_account WHERE account_name = ?1", nativeQuery = true)
    SystemAccount findByAccount_name(String account_name);

    @Query(value = "SELECT * FROM system_account WHERE accountid = ?1", nativeQuery = true)
    SystemAccount findById(int id);

    @Query(value = "SELECT * FROM system_account WHERE gmail = ?1", nativeQuery = true)
    SystemAccount findByGmail(String gmail);

    SystemAccount save(SystemAccount systemAccount);

    @Query(value = "SELECT * FROM system_account WHERE verification_code = ?1", nativeQuery = true)
    SystemAccount findByVerificationCode(String verificationCode);

    @Modifying
    @Transactional
    @Query(value = "UPDATE system_account SET verification_code = ?1 WHERE (account_name = ?2);", nativeQuery = true)
    void updateAccountVerificationCode(String verificationCode, String accountName);

    @Modifying
    @Transactional
    @Query(value = "UPDATE system_account SET gmail = ?1 WHERE (account_name = ?2);", nativeQuery = true)
    void updateAccountGmail(String gmail, String accountName);

    @Query(value = "SELECT * FROM system_account WHERE register_date >= DATE_SUB(NOW(), INTERVAL ?1 WEEK)", nativeQuery = true)
    List<SystemAccount> findRecentRegisterAccount(int numberOfWeek);

    @Query(value = "SELECT * FROM system_account WHERE accountid = ?1", nativeQuery = true)
    SystemAccount findByAccountId(Integer accountID);
}
