package com.project.CourseSystem.controller;

import com.project.CourseSystem.converter.System_AccountConverter;
import com.project.CourseSystem.converter.UserInfoConverter;
import com.project.CourseSystem.dto.CategoryDTO;
import com.project.CourseSystem.dto.CourseDTO;
import com.project.CourseSystem.dto.SystemAccountDTO;
import com.project.CourseSystem.dto.UserInfoDTO;
import com.project.CourseSystem.entity.EmailDetails;
import com.project.CourseSystem.entity.SystemAccount;
import com.project.CourseSystem.entity.UserInfo;
import com.project.CourseSystem.service.AccountService;
import com.project.CourseSystem.service.CategoryService;
import com.project.CourseSystem.service.EmailService;
import com.project.CourseSystem.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private AccountService accountService;
    private System_AccountConverter system_accountConverter;
    private EmailService emailService;
    private UserService userService;
    private UserInfoDTO userInfoDTO;
    private UserInfoConverter userInfoConverter;

    private CategoryService categoryService;
    SystemAccountDTO system_AccountDTO = new SystemAccountDTO();

    @Autowired
    public AuthController(AccountService accountService,
                          System_AccountConverter system_accountConverter,
                          EmailService emailService,
                          SystemAccountDTO systemAccountDTO,
                          UserService userService,
                          UserInfoDTO userInfoDTO,
                          UserInfoConverter userInfoConverter,
                          CategoryService categoryService) {
        this.accountService = accountService;
        this.system_accountConverter = system_accountConverter;
        this.emailService = emailService;
        this.system_AccountDTO = systemAccountDTO;
        this.userService = userService;
        this.userInfoDTO = userInfoDTO;
        this.userInfoConverter = userInfoConverter;
        this.categoryService = categoryService;
    }

    /* login page */
    @GetMapping("/login")
    public String loginPage(Model model, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if(session.getAttribute("CSys") != null){
            return "redirect:/home";
        }
        else{
            CategoryDTO cDto = new CategoryDTO();
            model.addAttribute("categoryDTO", cDto);
            CourseDTO courseDTO = new CourseDTO();
            model.addAttribute("courseDTO", courseDTO);
            model.addAttribute("category", categoryService.getAllCategories());
            model.addAttribute("system_account", new SystemAccountDTO());
            return "login";
        }
    }

    //handler method to handle user registration request
    @GetMapping("/registration")
    public String showRegistrationForm(Model model, HttpServletRequest request, HttpServletResponse response){
        CategoryDTO cDto = new CategoryDTO();
        model.addAttribute("categoryDTO", cDto);
        CourseDTO courseDTO = new CourseDTO();
        model.addAttribute("courseDTO", courseDTO);
        model.addAttribute("category", categoryService.getAllCategories());
        //this object holds form data
        SystemAccountDTO system_accountDTO = new SystemAccountDTO();
        model.addAttribute("system_account", system_accountDTO);
        return "register";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        session.removeAttribute("CSys");
        session.removeAttribute("enrolledList");
        return "redirect:/home";
    }

    @GetMapping("/resetPassword")
    public String resetPassword(@ModelAttribute SystemAccountDTO systemAccountDTO,Model model, HttpServletRequest request, HttpServletResponse response){
        CategoryDTO cDto = new CategoryDTO();
        model.addAttribute("categoryDTO", cDto);
        CourseDTO courseDTO = new CourseDTO();
        model.addAttribute("courseDTO", courseDTO);
        model.addAttribute("category", categoryService.getAllCategories());
        model.addAttribute("system_account", system_AccountDTO);
        return "resetPassword";
    }

    @GetMapping("/changePassword")
    public String changePassword(Model model, HttpServletRequest request
            , HttpServletResponse response){
        model.addAttribute("system_account", system_AccountDTO);
        return "changePassword";
    }

    @PostMapping("/changePassword/save")
    public String savePassword(@ModelAttribute("system_account") SystemAccountDTO system_accountDTO, Model model
            , HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        //encrypt password
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String new_password = passwordEncoder.encode(system_accountDTO.getAccountPassword());
        String account_name = (String) session.getAttribute("CSys");
        system_accountDTO = accountService.findUser(account_name, system_accountDTO.getAccountPassword());
        system_accountDTO.setAccountPassword(new_password);
        accountService.updateUser(system_accountDTO);
        session.removeAttribute("CSys");
        return "redirect:/login";
    }

    @GetMapping("/policy")
    public String policy(Model model, HttpServletRequest request, HttpServletResponse response){
        CategoryDTO cDto = new CategoryDTO();
        model.addAttribute("categoryDTO", cDto);
        CourseDTO courseDTO = new CourseDTO();
        model.addAttribute("courseDTO", courseDTO);
        model.addAttribute("category", categoryService.getAllCategories());
        return "policy";
    }
}
