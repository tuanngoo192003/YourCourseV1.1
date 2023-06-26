package com.project.CourseSystem.service;

import com.project.CourseSystem.dto.UserInfoDTO;
import com.project.CourseSystem.entity.UserInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    public UserInfo findUser(int accountID);

    public void saveUser(UserInfo userInfo);

    public void updateUser(UserInfo userInfo);

    public void updateAvatar(MultipartFile file) throws IOException;

    public void saveAvatar(UserInfo userInfo);
}
