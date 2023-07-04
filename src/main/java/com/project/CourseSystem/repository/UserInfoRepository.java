package com.project.CourseSystem.repository;

import com.project.CourseSystem.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

    @Query(value = "SELECT * FROM user WHERE accountid = ?1", nativeQuery = true)
    UserInfo findByID(int id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE `user` SET about_me = ?1, " +
            "avatar = ?2, dob = ?3, location = ?4, phone_nums = ?5, user_name = ?6, accountid = ?7  WHERE (userid = ?8);", nativeQuery = true)
    void updateUserInfo(String aboutMe, String avatar, String dob, String location
    , String phoneNums, String userName, String accountID, int userID);

    @Modifying
    @Transactional
    @Query(value = "UPDATE `user` SET avatar = ?1 WHERE (userid = ?2);", nativeQuery = true)
    void updateUserAvt(String avatar, int userID);

    @Query(value = "SELECT userid FROM user WHERE accountid = ?1", nativeQuery = true)
    int findUserIDByAccountID(int accountID);

}
