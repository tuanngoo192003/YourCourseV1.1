package com.project.CourseSystem.service.impl;

import com.project.CourseSystem.converter.UserInfoConverter;
import com.project.CourseSystem.dto.UserInfoDTO;
import com.project.CourseSystem.entity.UserInfo;
import com.project.CourseSystem.repository.SystemAccountRepository;
import com.project.CourseSystem.repository.UserInfoRepository;
import com.project.CourseSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UserServiceImpl implements UserService {

    private UserInfoConverter userInfoConverter;
    private UserInfoRepository userInfoRepository;

    private SystemAccountRepository systemAccountRepository;

    @Autowired
    public UserServiceImpl(UserInfoRepository userInfoRepository,
                           SystemAccountRepository systemAccountRepository,
                           UserInfoConverter userInfoConverter){
        this.userInfoRepository = userInfoRepository;
        this.systemAccountRepository = systemAccountRepository;
        this.userInfoConverter = userInfoConverter;
    }

    @Override
    public UserInfo findUser(int accountID) {
        UserInfo userInfo = userInfoRepository.findByID(accountID);
        return userInfo;
    }

    @Override
    public void saveUser(UserInfo userInfo) {
        userInfoRepository.save(userInfo);
    }

    @Override
    public void updateUser(UserInfo userInfo) {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO = userInfoConverter.convertEntityToDTO(userInfo);
        userInfoRepository.updateUserInfo(userInfoDTO.getAboutMe(), userInfoDTO.getAvatar(),
                userInfoDTO.getDob().toString(), userInfoDTO.getLocation(), userInfoDTO.getPhoneNums(),
                userInfoDTO.getUserName(), userInfoDTO.getAccountID().getAccountID().toString());
    }

    @Override
    public void updateAvatar(MultipartFile file) throws IOException{
        String folder = "/photos/";
        byte[] bytes = file.getBytes();
        Path path = Paths.get(folder + file.getOriginalFilename());
        File f = new File("/photos");
            if(!f.exists()){
                f.mkdir();
                Files.write(path, bytes);
            }
            else{
                Files.write(path, bytes);
            }
    }

    @Override
    public void saveAvatar(UserInfo userInfo) {
        userInfoRepository.save(userInfo);
    }


}
