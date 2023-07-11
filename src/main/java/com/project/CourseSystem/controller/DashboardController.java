package com.project.CourseSystem.controller;


import com.project.CourseSystem.converter.CourseConverter;
import com.project.CourseSystem.dto.CategoryDTO;
import com.project.CourseSystem.dto.CourseDTO;
import com.project.CourseSystem.dto.SystemAccountDTO;
import com.project.CourseSystem.dto.UserInfoDTO;
import com.project.CourseSystem.entity.*;
import com.project.CourseSystem.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DashboardController {

    CategoryService categoryService;

    RatingCourseService ratingCourseService;

    PaymentService paymentService;

    CourseService courseService;

    CourseConverter courseConverter;

    AccountService accountService;

    UserService userService;

    AuthController authController;

    public DashboardController(CategoryService categoryService, RatingCourseService ratingCourseService,
                               PaymentService paymentService, CourseService courseService,
                               CourseConverter courseConverter, AccountService accountService,
                               UserService userService, AuthController authController){
        this.categoryService = categoryService;
        this.ratingCourseService = ratingCourseService;
        this.paymentService = paymentService;
        this.courseService = courseService;
        this.courseConverter = courseConverter;
        this.accountService = accountService;
        this.userService = userService;
        this.authController = authController;
    }


    @GetMapping("/dashboard")
    public String getDashboard(Model model, HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        if(session.getAttribute("CSys")==null){
            return authController.loginPage(model, request, response);
        }
        else{
            String accountName = (String) session.getAttribute("CSys");
            SystemAccountDTO systemAccountDTO = accountService.findUserByAccountName(accountName);
            if(systemAccountDTO.getRoleID().getRoleID()!=2){
                authController.loginPage(model, request, response);
                System.err.println("You are not admin");
            }
            else{
                int userID = userService.findUserIDByAccountID(systemAccountDTO.getAccountID());
                UserInfo userInfo = userService.findUser(userID);
                model.addAttribute("userInfo", userInfo);
                System.err.println("I'm here");
                //Get rating
                List<RatingCourse> ratingCourseList = ratingCourseService.getAllRating();
                model.addAttribute("userRating", ratingCourseList);
                List<Course> courseList = new ArrayList<>();
                for (int i = 0; i < ratingCourseList.size(); i++){
                    CourseDTO courseDTO = courseService.getCourseByID(ratingCourseList.get(i).getCourseID().getCourseID());
                    if(!courseList.contains(courseConverter.convertDtoToEtity(courseDTO))){
                        courseList.add(courseConverter.convertDtoToEtity(courseDTO));
                    }
                }
                model.addAttribute("courseList", courseList);

                //Get total revenue
                List<Payment> paymentList = paymentService.getAllPayment();
                Float totalRevenue = 0.0F;
                for(int i = 0; i < paymentList.size(); i++){
                    totalRevenue += paymentList.get(i).getPaymentAmount();
                }
                //Get revenue growth
                paymentList = paymentService.getPaymentByMonth(1,0);
                Float revenueOfOneMonthRecent = 0.0F;
                for(int i = 0; i < paymentList.size(); i++){
                    revenueOfOneMonthRecent += paymentList.get(i).getPaymentAmount();
                }
                model.addAttribute("revenueOfOneMonthRecent", revenueOfOneMonthRecent);
                System.out.println(revenueOfOneMonthRecent);

                paymentList = paymentService.getPaymentByMonth(2,1);
                Float revenueOfTwoMonthRecent = 0.0F;
                for(int i = 0; i < paymentList.size(); i++){
                    revenueOfTwoMonthRecent += paymentList.get(i).getPaymentAmount();
                }
                model.addAttribute("revenueOfTwoMonthRecent", revenueOfTwoMonthRecent);
                System.out.println(revenueOfTwoMonthRecent);

                paymentList = paymentService.getPaymentByMonth(3,2);
                Float revenueOfThreeMonthRecent = 0.0F;
                for(int i = 0; i < paymentList.size(); i++){
                    revenueOfThreeMonthRecent += paymentList.get(i).getPaymentAmount();
                }
                model.addAttribute("revenueOfThreeMonthRecent", revenueOfThreeMonthRecent);
                System.out.println(revenueOfThreeMonthRecent);

                paymentList = paymentService.getPaymentByMonth(4,3);
                Float revenueOfFourMonthRecent = 0.0F;
                for(int i = 0; i < paymentList.size(); i++){
                    revenueOfFourMonthRecent += paymentList.get(i).getPaymentAmount();
                }
                model.addAttribute("revenueOfFourMonthRecent", revenueOfFourMonthRecent);
                System.out.println(revenueOfFourMonthRecent);

                paymentList = paymentService.getPaymentByMonth(5,4);
                Float revenueOfFiveMonthRecent = 0.0F;
                for(int i = 0; i < paymentList.size(); i++){
                    revenueOfFiveMonthRecent += paymentList.get(i).getPaymentAmount();
                }
                model.addAttribute("revenueOfFiveMonthRecent", revenueOfFiveMonthRecent);
                System.out.println(revenueOfFiveMonthRecent);

                paymentList = paymentService.getPaymentByMonth(6,5);
                Float revenueOfSixMonthRecent = 0.0F;
                for(int i = 0; i < paymentList.size(); i++){
                    revenueOfSixMonthRecent += paymentList.get(i).getPaymentAmount();
                }
                model.addAttribute("revenueOfSixMonthRecent", revenueOfSixMonthRecent);
                System.out.println(revenueOfSixMonthRecent);


                //Get total course by category
                List<CourseDTO> allCourses = courseService.getAllCourses();
                List<CourseDTO> courseOfCate1 = courseService.getAllCoursesByCategoryID(1);
                List<CourseDTO> courseOfCate2 = courseService.getAllCoursesByCategoryID(2);
                List<CourseDTO> courseOfCate3 = courseService.getAllCoursesByCategoryID(3);
                List<CourseDTO> courseOfCate4 = courseService.getAllCoursesByCategoryID(4);
                List<CourseDTO> courseOfCate5 = courseService.getAllCoursesByCategoryID(5);
                List<CourseDTO> courseOfCate6 = courseService.getAllCoursesByCategoryID(6);

                int totalCourse = allCourses.size();
                float percentCourseOfCate1 = (float)courseOfCate1.size()/totalCourse * 100;
                float percentCourseOfCate2 = (float)courseOfCate2.size()/totalCourse * 100;
                float percentCourseOfCate3 = (float)courseOfCate3.size()/totalCourse * 100;
                float percentCourseOfCate4 = (float)courseOfCate4.size()/totalCourse * 100;
                float percentCourseOfCate5 = (float)courseOfCate5.size()/totalCourse * 100;
                float percentCourseOfCate6 = (float)courseOfCate6.size()/totalCourse * 100;
                model.addAttribute("percentCourseOfCate1", percentCourseOfCate1);
                model.addAttribute("percentCourseOfCate2", percentCourseOfCate2);
                model.addAttribute("percentCourseOfCate3", percentCourseOfCate3);
                model.addAttribute("percentCourseOfCate4", percentCourseOfCate4);
                model.addAttribute("percentCourseOfCate5", percentCourseOfCate5);
                model.addAttribute("percentCourseOfCate6", percentCourseOfCate6);

                //Get total user
                List<SystemAccount> systemAccountList = accountService.getAllAccount();
                int totalUser = systemAccountList.size();
                model.addAttribute("totalUser", totalUser);
                //Get user growth
                List<SystemAccount> recentAccountList = accountService.getRecentRegisterAccount(1);
                model.addAttribute("week1", recentAccountList.size());
                recentAccountList = accountService.getRecentRegisterAccount(2);
                model.addAttribute("week2", recentAccountList.size());
                recentAccountList = accountService.getRecentRegisterAccount(3);
                model.addAttribute("week3", recentAccountList.size());
                recentAccountList = accountService.getRecentRegisterAccount(4);
                model.addAttribute("week4", recentAccountList.size());
                recentAccountList = accountService.getRecentRegisterAccount(5);
                model.addAttribute("week5", recentAccountList.size());


                //task bar model
                CategoryDTO cDto = new CategoryDTO();
                model.addAttribute("categoryDTO", cDto);
                CourseDTO courseDTO = new CourseDTO();
                model.addAttribute("courseDTO", courseDTO);
                model.addAttribute("category", categoryService.getAllCategories());
                model.addAttribute("system_account", new SystemAccountDTO());
            }
            return "dashboard";
        }
    }
}