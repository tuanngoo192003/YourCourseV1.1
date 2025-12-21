package com.project.CourseSystem.repository;

import com.project.CourseSystem.dto.SystemAccountDTO;
import com.project.CourseSystem.entity.SystemAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SystemAccountRepository extends JpaRepository<SystemAccount, Integer> {
    @Query(value = """
            SELECT
                sa.account_id as accountID,
                sa.account_name as accountName,
                sa.email as email,
                sa.verification_code as verificationCode,
                sa.register_date as registerDate,
                sa.role_id as roleID
            FROM
                system_account
            WHERE
                account_name = :accountName
            """, nativeQuery = true)
    SystemAccountDTO findByAccountName(@Param("accountName") String account_name);

    @Query(value = """
            SELECT
                sa.account_id as accountID,
                sa.account_name as accountName,
                sa.email as email,
                sa.verification_code as verificationCode,
                sa.register_date as registerDate,
                sa.role_id as roleID
            FROM
                system_account
                WHERE account_id = :accountID
            """, nativeQuery = true)
    SystemAccountDTO findById(@Param("accountID") int id);

    @Query(value = """
            SELECT
                sa.account_id as accountID,
                sa.account_name as accountName,
                sa.email as email,
                sa.verification_code as verificationCode,
                sa.register_date as registerDate,
                sa.role_id as roleID
                FROM
                    system_account
                    WHERE email = :email
            """, nativeQuery = true)
    SystemAccountDTO findByGmail(@Param("email") String email);

    @Query(value = """
            SELECT
                sa.account_id as accountID,
                sa.account_name as accountName,
                sa.email as email,
                sa.verification_code as verificationCode,
                sa.register_date as registerDate,
                sa.role_id as roleID
                FROM
                    system_account
                    WHERE verification_code = :verificationCode
            """, nativeQuery = true)
    SystemAccountDTO findByVerificationCode(@Param("verificationCode") String verificationCode);

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE
                system_account
            SET
                verification_code = :verificationCode
            WHERE
                (account_name = :accountName)
            """, nativeQuery = true)
    void updateAccountVerificationCode(@Param("verificationCode") String verificationCode,
            @Param("accountName") String accountName);

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE
                system_account
            SET
                email = :email
            WHERE
                (account_name = :accountName)
            """, nativeQuery = true)
    void updateAccountGmail(@Param("email") String email, @Param("accountName") String accountName);

    @Query(value = """
            SELECT
                sa.account_id as accountID,
                sa.account_name as accountName,
                sa.email as email,
                sa.verification_code as verificationCode,
                sa.register_date as registerDate,
                sa.role_id as roleID
            FROM
                system_account
            WHERE
                register_date >= DATE_SUB(NOW(), INTERVAL :numOfWeek WEEK)
            """, nativeQuery = true)
    List<SystemAccountDTO> findRecentRegisterAccount(@Param("numOfWeek") int numberOfWeek);
}
