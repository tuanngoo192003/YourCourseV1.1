package com.project.CourseSystem.service.impl;

import com.project.CourseSystem.converter.UserInfoConverter;
import com.project.CourseSystem.dto.UserInfoDTO;
import com.project.CourseSystem.entity.UserInfo;
import com.project.CourseSystem.repository.SystemAccountRepository;
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
                userInfoDTO.getUserName(), userInfoDTO.getAccountID().getAccountID().toString(), userInfoDTO.getUserID());
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
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        return userInfoRepository.findAll(PageRequest.of(pageNo - 1, pageSize, sort));
    }


}
