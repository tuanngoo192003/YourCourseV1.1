package com.project.CourseSystem.controller;

import com.project.CourseSystem.converter.UserInfoConverter;
import com.project.CourseSystem.dto.CategoryDTO;
import com.project.CourseSystem.dto.CourseDTO;
import com.project.CourseSystem.dto.SystemAccountDTO;
import com.project.CourseSystem.dto.UserInfoDTO;
import com.project.CourseSystem.entity.UserInfo;
import com.project.CourseSystem.service.AccountService;
import com.project.CourseSystem.service.CategoryService;
import com.project.CourseSystem.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class UserController {

    private SystemAccountDTO systemAccountDTO;

    private UserService userService;

    private AccountService accountService;

    private UserInfoConverter userInfoConverter;

    private CategoryService categoryService;

    public UserController(SystemAccountDTO systemAccountDTO, UserService userService,
                          AccountService accountService,
                          UserInfoConverter userInfoConverter,
                          CategoryService categoryService){
        this.systemAccountDTO = systemAccountDTO;
        this.userService = userService;
        this.accountService = accountService;
        this.userInfoConverter = userInfoConverter;
        this.categoryService = categoryService;
    }

    @GetMapping("/profile")
    public String userProfile(Model model, HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        if(session.getAttribute("CSys")==null){
            model.addAttribute("system_account", systemAccountDTO);
            return "login";
        }
        else{
            CategoryDTO cDto = new CategoryDTO();
            model.addAttribute("categoryDTO", cDto);
            CourseDTO courseDTO = new CourseDTO();
            model.addAttribute("courseDTO", courseDTO);
            model.addAttribute("category", categoryService.getAllCategories());
            String accountName = (String) session.getAttribute("CSys");
            SystemAccountDTO systemAccountDTO = accountService.findUserByAccountName(accountName);
            UserInfo userInfo = userService.findUser(systemAccountDTO.getAccountID());
            model.addAttribute("userInfo", userInfoConverter.convertEntityToDTO(userInfo));
            String gmail = systemAccountDTO.getGmail();
            model.addAttribute("gmail", gmail);
            model.addAttribute("system_account", systemAccountDTO);
            return "userProfile";
        }
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute UserInfo userInfo, Model model, HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        userService.updateUser(userInfo);
        SystemAccountDTO systemAccountDTO = accountService.findUserByAccountName(session.getAttribute("CSys").toString());
        model.addAttribute("userInfo", userInfoConverter.convertEntityToDTO(userInfo));
        String gmail = systemAccountDTO.getGmail();
        model.addAttribute("currentGmail", gmail);
        model.addAttribute("system_account", systemAccountDTO);
        return "userProfile";
    }

    @PostMapping("/changePasswordWithOldPasswordConfirm")
    public String savePassword(@ModelAttribute("system_account") SystemAccountDTO system_accountDTO, @Param("oldPassword") String oldPassword,
                               Model model, HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        String accountName = (String) session.getAttribute("CSys");
        SystemAccountDTO systemAccountDTO1 = accountService.findUserByAccountName(accountName);
        //decrypt password and matches with old password
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if(passwordEncoder.matches(oldPassword, systemAccountDTO1.getAccountPassword())){
            String new_password = system_accountDTO.getAccountPassword();
            system_accountDTO = accountService.findUser(accountName, system_accountDTO.getAccountPassword());
            //encrypt password
            String encodedPassword = passwordEncoder.encode(new_password);
            system_accountDTO.setAccountPassword(encodedPassword);
            accountService.updateUser(system_accountDTO);
            session.removeAttribute("CSys");
            return "login";
        }
        else{
            model.addAttribute("system_account", systemAccountDTO);
            model.addAttribute("error", "Old password is incorrect");
            return "redirect:/profile?error=Old password is incorrect";
        }
    }

    @PostMapping("/updateAvatar")
    public String uploadAvatar(@Param("file") MultipartFile file, Model model, HttpServletRequest request, HttpServletResponse response){
        try{
            String avt = file.getOriginalFilename();
            System.out.println(avt);
            userService.updateAvatar(file);
        }
        catch (IOException e){
            model.addAttribute("error", "Error while uploading file");
            return "redirect:/profile?error=Error while uploading file";
        }
        /* save avatar */
        HttpSession session = request.getSession();
        String accountName = (String) session.getAttribute("CSys");
        SystemAccountDTO systemAccountDTO = accountService.findUserByAccountName(accountName);
        UserInfo userInfo = userService.findUser(systemAccountDTO.getAccountID());
        String avatar = "/photos/" + file.getOriginalFilename();
        userInfo.setAvatar(avatar);
        userService.saveAvatar(userInfo);

        /* set up object */
        CategoryDTO cDto = new CategoryDTO();
        model.addAttribute("categoryDTO", cDto);
        CourseDTO courseDTO = new CourseDTO();
        model.addAttribute("courseDTO", courseDTO);
        model.addAttribute("category", categoryService.getAllCategories());

        /* set up model */
        model.addAttribute("userInfo", userInfoConverter.convertEntityToDTO(userInfo));
        String gmail = systemAccountDTO.getGmail();
        model.addAttribute("gmail", gmail);
        model.addAttribute("system_account", systemAccountDTO);
        return "userProfile";
    }
}
