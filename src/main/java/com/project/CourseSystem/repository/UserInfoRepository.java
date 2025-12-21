package com.project.CourseSystem.repository;

import com.project.CourseSystem.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE `user`
            SET
                about_me = :aboutMe,
                avatar = :avatar,
                dob = :dob,
                location = :location,
                phone_nums = :phoneNumber,
                user_name = :username,
                accountid = :accountID
            WHERE
                (userid = :userID)
            """, nativeQuery = true)
    void updateUserInfo(
            @Param("aboutMe") String aboutMe,
            @Param("avatar") String avatar,
            @Param("dob") String dob,
            @Param("location") String location,
            @Param("phoneNumber") String phoneNums,
            @Param("username") String userName,
            @Param("accountID") String accountID,
            @Param("userID") int userID);

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE `user`
            SET
                avatar = :avatar,
            WHERE
                (userid = :userID)
            """, nativeQuery = true)
    void updateUserAvt(@Param("avatar") String avatar, @Param("userID") int userID);

    @Query(value = """
            SELECT
                u.user_id
            FROM user u
            WHERE
                account_id = :accountID
            """, nativeQuery = true)
    int findUserIDByAccountID(@Param("accountID") int accountID);
}
