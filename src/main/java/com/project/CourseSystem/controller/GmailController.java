package com.project.CourseSystem.controller;

import com.project.CourseSystem.converter.System_AccountConverter;
import com.project.CourseSystem.converter.UserInfoConverter;
import com.project.CourseSystem.dto.SystemAccountDTO;
import com.project.CourseSystem.dto.UserInfoDTO;
import com.project.CourseSystem.entity.EmailDetails;
import com.project.CourseSystem.entity.SystemAccount;
import com.project.CourseSystem.service.AccountService;
import com.project.CourseSystem.service.EmailService;
import com.project.CourseSystem.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
public class GmailController {

    final private AccountService accountService;

    final private System_AccountConverter system_accountConverter;

    final private EmailService emailService;

    final private SystemAccountDTO system_AccountDTO;

    final private UserService userService;

    final private UserInfoConverter userInfoConverter;

    public GmailController(AccountService accountService, System_AccountConverter system_accountConverter,
                           EmailService emailService, SystemAccountDTO system_AccountDTO, UserService userService,
                           UserInfoConverter userInfoConverter) {
        this.accountService = accountService;
        this.system_accountConverter = system_accountConverter;
        this.emailService = emailService;
        this.system_AccountDTO = system_AccountDTO;
        this.userService = userService;
        this.userInfoConverter = userInfoConverter;
    }

    @PostMapping("/verifyCodeSendForChangePassword")
    public String verification(@ModelAttribute SystemAccountDTO systemAccountDTO , Model model, HttpServletRequest request
            , HttpServletResponse response){
        if(!accountService.isGmailExist(systemAccountDTO.getGmail())){
            return "redirect:/resetPassword?errorGmail";
        }
        else {
            EmailDetails emailDetails = new EmailDetails();
            emailDetails.setRecipient(systemAccountDTO.getGmail());
            emailDetails.setSubject("Please verify your password reset request");
            String verificationCode = accountService.generateVerificationCode();
            String msgBody = "Your verification code is: " + verificationCode +"\n";
            msgBody += "Thank you";
            emailDetails.setMsgBody(msgBody);
            emailDetails.getAttachment();
            SystemAccount systemAccount = accountService.findByGmail(systemAccountDTO.getGmail());
            systemAccount.setVerificationCode(verificationCode);

            accountService.updateUser(system_accountConverter.convertEntityToDTO(systemAccount));

            emailService.sendSimpleEmail(emailDetails);
            model.addAttribute("system_account", system_AccountDTO);
            HttpSession session = request.getSession();
            session.setAttribute("change", "password");
            return "verify";
        }
    }

    @PostMapping("/verifyCodeSendForChangeGmail")
    public String gmailVerification(@ModelAttribute SystemAccountDTO systemAccountDTO , Model model, HttpServletRequest request
            , HttpServletResponse response){
        HttpSession session = request.getSession();
        if(accountService.isGmailExist(systemAccountDTO.getGmail())){
            return "redirect:/userProfile?errorGmail";
        }
        else {
            EmailDetails emailDetails = new EmailDetails();
            emailDetails.setRecipient(systemAccountDTO.getGmail());
            emailDetails.setSubject("Please verify your gmail change request");
            String verificationCode = accountService.generateVerificationCode();
            String msgBody = "Your verification code is: " + verificationCode +"\n";
            msgBody += "Thank you";
            emailDetails.setMsgBody(msgBody);
            emailDetails.getAttachment();

            String accountName = (String) session.getAttribute("CSys");

            accountService.updateVerifyCode(verificationCode, accountName);

            emailService.sendSimpleEmail(emailDetails);
            model.addAttribute("system_account", system_AccountDTO);

            session.setAttribute("newGmail", systemAccountDTO.getGmail());
            session.setAttribute("change", "gmail");
            return "verify";
        }
    }

    @PostMapping("regisGmailConfirmation")
    public String registrationVerification(@ModelAttribute("system_account") SystemAccountDTO system_accountDTO, Model model,
                                           @RequestParam("userfullname") String userfullname, @RequestParam("gender") String gender,
                                           HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        if(system_accountDTO!=null){
            session.setAttribute("systemAccountRegister", system_accountDTO);
            session.setAttribute("userfullname", userfullname);
        }
        if(accountService.isGmailExist(system_accountDTO.getGmail())){
            return "redirect:/registration?errorGmail";
        }
        else{
            EmailDetails emailDetails = new EmailDetails();
            emailDetails.setRecipient(system_accountDTO.getGmail());
            emailDetails.setSubject("Please verify your gmail change request");
            String verificationCode = accountService.generateVerificationCode();
            String msgBody = "Your verification code is: " + verificationCode +"\n";
            msgBody += "Thank you";
            emailDetails.setMsgBody(msgBody);
            emailDetails.getAttachment();

            String status = emailService.sendSimpleEmail(emailDetails);
            if(status.equals("Mail sent successfully...")){
                session.setAttribute("systemAccountRegister", system_accountDTO);
                session.setAttribute("userfullname", userfullname);
                session.setAttribute("gender", gender);
                session.setAttribute("change", "registrationGmail");
                session.setAttribute("vrf", verificationCode);
                return "redirect:/verify";
            }
            return "redirect:registration?dontExistGmail";
        }
    }

    @GetMapping("/verify")
    public String verify(Model model, HttpServletRequest request, HttpServletResponse response){
        model.addAttribute("system_account", system_AccountDTO);
        return "verify";
    }

    @PostMapping("/verification/confirm")
    public String confirmation(@ModelAttribute SystemAccountDTO systemAccountDTO ,Model model, HttpServletRequest request,
                               HttpServletResponse response){
        HttpSession session = request.getSession();
        SystemAccount systemAccount = accountService.findByVerificationCode(systemAccountDTO.getVerificationCode());
        String change = (String) session.getAttribute("change");
        if(change!=null){
            if(change.equals("registrationGmail")){
                session.removeAttribute("change");
                String verificationCode = (String) session.getAttribute("vrf");
                if(systemAccountDTO.getVerificationCode().equals(verificationCode)){
                    SystemAccountDTO temp = (SystemAccountDTO) session.getAttribute("systemAccountRegister");
                    //encrypt password
                    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                    String encodedPassword = passwordEncoder.encode(temp.getAccountPassword());
                    temp.setAccountPassword(encodedPassword);
                    temp.setRegisterDate(new java.sql.Date(new Date().getTime()));
                    //save account
                    accountService.saveUser(temp);
                    //add userInfo
                    UserInfoDTO userInfoDTO1 = new UserInfoDTO();
                    SystemAccount systemAccountTemp = system_accountConverter.convertDTOToEntity(accountService.findUserByAccountName(temp.getAccountName()));
                    userInfoDTO1.setAccountID(systemAccountTemp);
                    userInfoDTO1.setUserName((String) session.getAttribute("userfullname"));
                    userInfoDTO1.setGender((String) session.getAttribute("gender"));
                    userService.saveUser(userInfoConverter.convertDtoToEntity(userInfoDTO1));
                    return "redirect:/registration?success";
                }
                else{
                    return "redirect:/verify?error";
                }
            }
            else{
                if(systemAccount!=null){
                    if(change.equals("password")){
                        session.removeAttribute("change");
                        session.setAttribute("CSys", systemAccount.getAccountName());
                        return "redirect:/changePassword";
                    } else if(change.equals("gmail")){
                        String accountName = (String) session.getAttribute("CSys");
                        accountService.updateGmail((String) session.getAttribute("newGmail"), accountName);
                        session.removeAttribute("newGmail");
                        session.removeAttribute("change");
                        return "redirect:/profile";
                    }
                }
                else {
                    return "redirect:/verify?error";
                }
            }
        }
        return null;
    }
}
