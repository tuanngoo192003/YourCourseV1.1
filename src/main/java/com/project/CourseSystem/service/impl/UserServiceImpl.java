package com.project.CourseSystem.service.impl;

import com.project.CourseSystem.converter.UserInfoConverter;
import com.project.CourseSystem.dto.UserInfoDTO;
import com.project.CourseSystem.entity.UserInfo;
import com.project.CourseSystem.repository.UserInfoRepository;
import com.project.CourseSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    final private UserInfoConverter userInfoConverter;
    final private UserInfoRepository userInfoRepository;

    @Autowired
    public UserServiceImpl(UserInfoRepository userInfoRepository,
            UserInfoConverter userInfoConverter) {
        this.userInfoRepository = userInfoRepository;
        this.userInfoConverter = userInfoConverter;
    }

    @Override
    public UserInfo findUser(int accountID) {
        UserInfo userInfo = userInfoRepository.findById(accountID).get();
        return userInfo;
    }

    @Override
    public void saveUser(UserInfo userInfo) {
        userInfoRepository.save(userInfo);
    }

    @Override
    public void updateUser(UserInfo userInfo) {
        userInfoRepository.updateUserInfo(userInfo.getAboutMe(), userInfo.getAvatar(),
                userInfo.getDob().toString(), userInfo.getLocation(), userInfo.getPhoneNums(),
                userInfo.getUserName(), userInfo.getAccountID().toString(),
                userInfo.getUserID());
    }

    @Override
    public void saveAvatar(String fileID, int userID) {
        userInfoRepository.updateUserAvt(fileID, userID);
    }

    @Override
    public int findUserIDByAccountID(int accountID) {
        return userInfoRepository.findUserIDByAccountID(accountID);
    }

    @Override
    public List<UserInfo> findAllUser() {
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        return userInfoList;
    }

    @Override
    public Page<UserInfo> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();
        return userInfoRepository.findAll(PageRequest.of(pageNo - 1, pageSize, sort));
    }

    @Override
    public UserInfo findByUserID(Integer userID) {
        return userInfoRepository.findById(userID).get();
    }

}
