package com.project.CourseSystem.controller;


import com.project.CourseSystem.converter.CourseConverter;
import com.project.CourseSystem.dto.CategoryDTO;
import com.project.CourseSystem.dto.CourseDTO;
import com.project.CourseSystem.dto.SystemAccountDTO;
import com.project.CourseSystem.entity.*;
import com.project.CourseSystem.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
public class DashboardController {

    final private CategoryService categoryService;

    final private RatingCourseService ratingCourseService;

    final private PaymentService paymentService;

    final private CourseService courseService;

    final private CourseConverter courseConverter;

    final private AccountService accountService;

    final private UserService userService;

    final private AuthController authController;

    final private EnrolledService enrolledService;

    final private PaymentDetailsService paymentDetailsService;

    public DashboardController(CategoryService categoryService, RatingCourseService ratingCourseService,
                               PaymentService paymentService, CourseService courseService,
                               CourseConverter courseConverter, AccountService accountService,
                               UserService userService, AuthController authController,
                               EnrolledService enrolledService, PaymentDetailsService paymentDetailsService){
        this.categoryService = categoryService;
        this.ratingCourseService = ratingCourseService;
        this.paymentService = paymentService;
        this.courseService = courseService;
        this.courseConverter = courseConverter;
        this.accountService = accountService;
        this.userService = userService;
        this.authController = authController;
        this.enrolledService = enrolledService;
        this.paymentDetailsService = paymentDetailsService;
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
            }
            else{
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
                model.addAttribute("paymentList", paymentList);
                paymentList = paymentService.getAllConfirmedPayment();
                //Get course bought
                List<Enrolled> enrolledList = new ArrayList<>();
                int totalNumberOfCourseBought = 0;
                //Set total payment
                int totalPayment = paymentList.size();
                model.addAttribute("totalPayment", totalPayment);
                Float totalRevenue = 0.0F;
                for(int i = 0; i < paymentList.size(); i++){
                    List<Enrolled> enrolled = enrolledService.getEnrolledByPaymentID(paymentList.get(i).getPaymentID());
                    for(int j = 0; j < enrolled.size(); j++){
                        enrolledList.add(enrolled.get(j));
                    }
                    totalRevenue += paymentList.get(i).getPaymentAmount();
                }
                NumberFormat vnCurrencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                String formattedRevenue = vnCurrencyFormat.format(totalRevenue);
                model.addAttribute("totalRevenue", formattedRevenue);

                for(int i = 0; i < enrolledList.size(); i++){
                    totalNumberOfCourseBought++;
                }
                model.addAttribute("totalNumberOfCourseBought", totalNumberOfCourseBought);

                //Get revenue growth
                paymentList = paymentService.getPaymentByMonth(1,0);
                Float revenueOfOneMonthRecent = 0.0F;
                enrolledList.clear();
                for(int i = 0; i < paymentList.size(); i++){
                    List<Enrolled> enrolled = enrolledService.getEnrolledByPaymentID(paymentList.get(i).getPaymentID());
                    for(int j = 0; j < enrolled.size(); j++){
                        enrolledList.add(enrolled.get(j));
                    }
                    revenueOfOneMonthRecent += paymentList.get(i).getPaymentAmount();
                }
                model.addAttribute("revenueOfOneMonthRecent", revenueOfOneMonthRecent);

                //set growth of payment in 30 day recent
                double growthPayment = ((totalPayment - paymentList.size())/totalPayment)*100;
                DecimalFormat df = new DecimalFormat("#.##");
                String formattedGrowthPayment = df.format(growthPayment);
                model.addAttribute("growthPayment", formattedGrowthPayment);

                //set growth of course bought in 30 day recent
                double growthCourseBought = ((totalNumberOfCourseBought - enrolledList.size())/totalNumberOfCourseBought)*100;
                String formattedGrowthCourseBought = df.format(growthCourseBought);
                model.addAttribute("growthCourseBought", formattedGrowthCourseBought);

                //set growth of revenue in 30 day recent
                double growthRevenue = ((totalRevenue - revenueOfOneMonthRecent) / totalRevenue) * 100;
                String formattedGrowthRevenue = df.format(growthRevenue);
                model.addAttribute("growthRevenue", formattedGrowthRevenue);

                int totalPaymentOfOneMonthRecent = paymentList.size();
                paymentList = paymentService.getPaymentByMonth(2, 1);
                float revenueOfTwoMonthRecentToOneMonthRecent = 0.0F;
                enrolledList.clear();
                for (int i = 0; i < paymentList.size(); i++) {
                    List<Enrolled> enrolled = enrolledService.getEnrolledByPaymentID(paymentList.get(i).getPaymentID());
                    for (int j = 0; j < enrolled.size(); j++) {
                        enrolledList.add(enrolled.get(j));
                    }
                    revenueOfTwoMonthRecentToOneMonthRecent += paymentList.get(i).getPaymentAmount();
                }
                String formattedRevenueOfTwoMonthRecentToOneMonthRecent = df.format(revenueOfTwoMonthRecentToOneMonthRecent);
                model.addAttribute("revenueOfTwoMonthRecent", formattedRevenueOfTwoMonthRecentToOneMonthRecent);

                //set growth of payment of 2 month recent to 1 month recent
                double growthPaymentOfOneMonthRecent = ((totalPaymentOfOneMonthRecent - paymentList.size()) / (double)totalPaymentOfOneMonthRecent) * 100;
                double tempPayment = growthPayment - growthPaymentOfOneMonthRecent;
                if (tempPayment < 0) {
                    tempPayment = tempPayment * (-1);
                    model.addAttribute("statusOfGrowthPayment", "decrease");
                } else {
                    model.addAttribute("statusOfGrowthPayment", "increase");
                }
                String formattedGrowthPaymentOfOneMonthRecent = df.format(tempPayment);
                model.addAttribute("growthPaymentOfOneMonthRecent", formattedGrowthPaymentOfOneMonthRecent);

                //set growth of course bought of 2 month recent to 1 month recent
                int totalNumberOfCourseBoughtOfOneMonthRecent = enrolledList.size();
                double growthCourseBoughtOfOneMonthRecent = ((totalNumberOfCourseBoughtOfOneMonthRecent - enrolledList.size()) / (double)totalNumberOfCourseBoughtOfOneMonthRecent) * 100;
                double tempCourseBought = growthCourseBought - growthCourseBoughtOfOneMonthRecent;
                if (tempCourseBought < 0) {
                    tempCourseBought = tempCourseBought * (-1);
                    model.addAttribute("statusOfGrowthCourseBought", "decrease");
                } else {
                    model.addAttribute("statusOfGrowthCourseBought", "increase");
                }
                String formattedGrowthCourseBoughtOfOneMonthRecent = df.format(tempCourseBought);
                model.addAttribute("growthCourseBoughtOfOneMonthRecent", formattedGrowthCourseBoughtOfOneMonthRecent);

                //set growth of revenue of 2 month recent to 1 month recent
                double growthRevenueOfOneMonthRecent = ((revenueOfOneMonthRecent - revenueOfTwoMonthRecentToOneMonthRecent) / revenueOfOneMonthRecent) * 100;
                double tempRevenue = growthRevenue - growthRevenueOfOneMonthRecent;
                if (tempRevenue < 0) {
                    tempRevenue = tempRevenue * (-1);
                    model.addAttribute("statusOfGrowthRevenue", "decrease");
                } else {
                    model.addAttribute("statusOfGrowthRevenue", "increase");
                }
                String formattedGrowthRevenueOfOneMonthRecent = df.format(tempRevenue);
                model.addAttribute("growthRevenueOfOneMonthRecent", formattedGrowthRevenueOfOneMonthRecent);


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
                model.addAttribute("allCourses", allCourses);
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
                //getAllUser
                List<UserInfo> userList = userService.findAllUser();
                model.addAttribute("userList", userList);
                List<PaymentDetails> paymentDetailsList = paymentDetailsService.getAllPaymentDetails();
                model.addAttribute("paymentDetailsList", paymentDetailsList);

                int userID = userService.findUserIDByAccountID(systemAccountDTO.getAccountID());
                UserInfo userInfo = userService.findUser(userID);
                model.addAttribute("userInfo", userInfo);
            }

            String msg = (String) session.getAttribute("successMessage");
            if(msg != null){
                model.addAttribute("successMessage", msg);
                session.removeAttribute("successMessage");
            }
            String errorMsg = (String) session.getAttribute("errorMsg");
            if(errorMsg != null){
                model.addAttribute("errorMsg", errorMsg);
                session.removeAttribute("errorMsg");
            }
            return "dashboard";
        }
    }
}
