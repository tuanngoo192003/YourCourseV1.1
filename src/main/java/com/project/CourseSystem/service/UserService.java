package com.project.CourseSystem.service;

import com.project.CourseSystem.entity.Course;
import com.project.CourseSystem.entity.UserInfo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    UserInfo findUser(int accountID);

    void saveUser(UserInfo userInfo);

    void updateUser(UserInfo userInfo);

    void saveAvatar(String fileID, int userID);

    int findUserIDByAccountID(int accountID);

    List<UserInfo> findAllUser();

    Page<UserInfo> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
