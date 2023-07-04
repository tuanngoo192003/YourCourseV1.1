package com.project.CourseSystem.service;

import com.project.CourseSystem.entity.UserInfo;

public interface UserService {
    public UserInfo findUser(int accountID);

    public void saveUser(UserInfo userInfo);

    public void updateUser(UserInfo userInfo);

    public void saveAvatar(String fileID, int userID);

    public int findUserIDByAccountID(int accountID);
}
