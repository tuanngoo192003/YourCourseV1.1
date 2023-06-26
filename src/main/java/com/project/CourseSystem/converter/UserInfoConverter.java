package com.project.CourseSystem.converter;

import com.project.CourseSystem.dto.UserInfoDTO;
import com.project.CourseSystem.entity.UserInfo;
import org.springframework.stereotype.Component;

@Component
public class UserInfoConverter {

    public UserInfoDTO convertEntityToDTO(UserInfo userInfo){
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUserID(userInfo.getUserID());
        userInfoDTO.setUserName(userInfo.getUserName());
        userInfoDTO.setAboutMe(userInfo.getAboutMe());
        userInfoDTO.setDob(userInfo.getDob());
        userInfoDTO.setLocation(userInfo.getLocation());
        userInfoDTO.setPhoneNums(userInfo.getPhoneNums());
        userInfoDTO.setAvatar(userInfo.getAvatar());
        userInfoDTO.setAccountID(userInfo.getAccountID());

        return userInfoDTO;
    }

    public UserInfo convertDtoToEntity(UserInfoDTO userInfoDTO){
        UserInfo userInfo = new UserInfo();
        userInfo.setUserID(userInfoDTO.getUserID());
        userInfo.setUserName(userInfoDTO.getUserName());
        userInfo.setAboutMe(userInfoDTO.getAboutMe());
        userInfo.setDob(userInfoDTO.getDob());
        userInfo.setLocation(userInfoDTO.getLocation());
        userInfo.setPhoneNums(userInfoDTO.getPhoneNums());
        userInfo.setAvatar(userInfoDTO.getAvatar());
        userInfo.setAccountID(userInfoDTO.getAccountID());
        return userInfo;
    }
}
