package com.project.CourseSystem.controller;

import com.project.CourseSystem.dto.*;
import com.project.CourseSystem.entity.Course;
import com.project.CourseSystem.entity.CourseDetails;
import com.project.CourseSystem.entity.Enrolled;
import com.project.CourseSystem.entity.Lesson;
import com.project.CourseSystem.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    private CategoryService categoryService;

    private AccountService accountService;

    private CourseService courseService;

    private EnrolledService enrolledService;

    private DashboardController dashboardController;

    private LessonService lessonService;

    public HomeController(AccountService accountService, CategoryService categoryService,
                          CourseService courseService, EnrolledService enrolledService,
                          DashboardController dashboardController, LessonService lessonService) {
        this.categoryService = categoryService;
        this.accountService = accountService;
        this.courseService = courseService;
        this.enrolledService = enrolledService;
        this.dashboardController = dashboardController;
        this.lessonService = lessonService;
    }

    /* home page */
    @GetMapping("/home")
    public String homePage(Model model, HttpServletRequest request, HttpServletResponse response){
        /* set up object */
        CategoryDTO cDto = new CategoryDTO();
        model.addAttribute("categoryDTO", cDto);
        CourseDTO courseDTO = new CourseDTO();
        model.addAttribute("courseDTO", courseDTO);
        List<CategoryDTO> categoryList = categoryService.getAllCategories();
        model.addAttribute("category", categoryList);

        Course course = new Course();
        CourseDetailsDTO courseDetailsDTO = new CourseDetailsDTO();
        List<String> tempDetails = new ArrayList<>();
        List<LessonDTO> lessonList = new ArrayList<>();

        //category: Language Learning
        Course course1 = courseService.findFirstCourseByCategoryID(1);
        model.addAttribute("course1", course1);
        CourseDetailsDTO courseDetailsDTO1 = courseService.getCourseDetailsByID(course1.getCourseID());
        model.addAttribute("courseDetailsDTO1", courseDetailsDTO1);
        String temp1 = courseDetailsDTO1.getForWho();
        List<String> tempDetails1 = new ArrayList<>();
        for(String s : temp1.split("/")){
            tempDetails1.add(s);
        }
        model.addAttribute("forWho1", tempDetails1);
        temp1 = courseDetailsDTO1.getCourseRequirements();
        tempDetails1.clear();
        for(String s : temp1.split("/")){
            tempDetails1.add(s);
        }
        model.addAttribute("courseRequirements1", tempDetails1);
        List<LessonDTO> lessonList1 = lessonService.getAllByCourseID(course1.getCourseID());
        model.addAttribute("numberOfLesson1", lessonList1.size());

        //category: Web development
        Course course2 = courseService.findFirstCourseByCategoryID(2);
        model.addAttribute("course2", course2);
        CourseDetailsDTO courseDetailsDTO2 = courseService.getCourseDetailsByID(course2.getCourseID());
        model.addAttribute("courseDetailsDTO2", courseDetailsDTO2);
        String temp2 = courseDetailsDTO2.getForWho();
        List<String> tempDetails2 = new ArrayList<>();
        for(String s : temp2.split("/")){
            tempDetails2.add(s);
        }
        model.addAttribute("forWho2", tempDetails2);
        temp2 = courseDetailsDTO2.getCourseRequirements();
        tempDetails2.clear();
        for(String s : temp2.split("/")){
            tempDetails2.add(s);
        }
        model.addAttribute("courseRequirements2", tempDetails2);
        List<LessonDTO> lessonList2 = lessonService.getAllByCourseID(course2.getCourseID());
        model.addAttribute("numberOfLesson2", lessonList2.size());

        //category: Web design
        Course course3 = courseService.findFirstCourseByCategoryID(3);
        model.addAttribute("course3", course3);
        CourseDetailsDTO courseDetailsDTO3 = courseService.getCourseDetailsByID(course3.getCourseID());
        model.addAttribute("courseDetailsDTO3", courseDetailsDTO3);
        String temp3 = courseDetailsDTO3.getForWho();
        List<String> tempDetails3 = new ArrayList<>();
        for(String s : temp3.split("/")){
            tempDetails3.add(s);
        }
        model.addAttribute("forWho3", tempDetails3);
        temp3 = courseDetailsDTO3.getCourseRequirements();
        tempDetails3.clear();
        for(String s : temp3.split("/")){
            tempDetails3.add(s);
        }
        model.addAttribute("courseRequirements3", tempDetails3);
        List<LessonDTO> lessonList3 = lessonService.getAllByCourseID(course3.getCourseID());
        model.addAttribute("numberOfLesson3", lessonList3.size());

        //category: IT Fundamental
        Course course4 = courseService.findFirstCourseByCategoryID(4);
        model.addAttribute("course4", course4);
        CourseDetailsDTO courseDetailsDTO4 = courseService.getCourseDetailsByID(course4.getCourseID());
        model.addAttribute("courseDetailsDTO4", courseDetailsDTO4);
        String temp4 = courseDetailsDTO4.getForWho();
        List<String> tempDetails4 = new ArrayList<>();
        for(String s : temp4.split("/")){
            tempDetails4.add(s);
        }
        model.addAttribute("forWho4", tempDetails4);
        temp4 = courseDetailsDTO4.getCourseRequirements();
        tempDetails4.clear();
        for(String s : temp4.split("/")){
            tempDetails4.add(s);
        }
        model.addAttribute("courseRequirements4", tempDetails4);
        List<LessonDTO> lessonList4 = lessonService.getAllByCourseID(course4.getCourseID());
        model.addAttribute("numberOfLesson4", lessonList4.size());

        //category: Mathematics
        Course course5 = courseService.findFirstCourseByCategoryID(5);
        model.addAttribute("course5", course5);
        CourseDetailsDTO courseDetailsDTO5 = courseService.getCourseDetailsByID(course5.getCourseID());
        model.addAttribute("courseDetailsDTO5", courseDetailsDTO5);
        String temp5 = courseDetailsDTO5.getForWho();
        List<String> tempDetails5 = new ArrayList<>();
        for(String s : temp5.split("/")){
            tempDetails5.add(s);
        }
        model.addAttribute("forWho5", tempDetails5);
        temp5 = courseDetailsDTO5.getCourseRequirements();
        tempDetails5.clear();
        for(String s : temp5.split("/")){
            tempDetails5.add(s);
        }
        model.addAttribute("courseRequirements5", tempDetails5);
        List<LessonDTO> lessonList5 = lessonService.getAllByCourseID(course5.getCourseID());
        model.addAttribute("numberOfLesson5", lessonList5.size());

        //category: Fundamental Programming
        Course course6 = courseService.findFirstCourseByCategoryID(6);
        model.addAttribute("course6", course6);
        CourseDetailsDTO courseDetailsDTO6 = courseService.getCourseDetailsByID(course6.getCourseID());
        model.addAttribute("courseDetailsDTO6", courseDetailsDTO6);
        String temp6 = courseDetailsDTO6.getForWho();
        List<String> tempDetails6 = new ArrayList<>();
        for(String s : temp6.split("/")){
            tempDetails6.add(s);
        }
        model.addAttribute("forWho6", tempDetails6);
        temp6 = courseDetailsDTO6.getCourseRequirements();
        tempDetails6.clear();
        for(String s : temp6.split("/")){
            tempDetails6.add(s);
        }
        model.addAttribute("courseRequirements6", tempDetails6);
        List<LessonDTO> lessonList6 = lessonService.getAllByCourseID(course6.getCourseID());
        model.addAttribute("numberOfLesson6", lessonList6.size());


        return "home";
    }

    /* Verify login */
    @PostMapping("/welcome")
    public String welcome(@ModelAttribute("system_account") SystemAccountDTO system_accountDTO, HttpServletRequest request, HttpServletResponse response,
                          Model model){
        HttpSession session = request.getSession();
        /* check if user is already exist or not */
        SystemAccountDTO system_accountDTO1 = accountService.findUser(system_accountDTO.getAccountName(), system_accountDTO.getAccountPassword());
        /* check password */
        if(system_accountDTO1 != null){
            /* get inputed password */
            String check = system_accountDTO.getAccountPassword();

            //decrypt password
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if(passwordEncoder.matches(check, system_accountDTO1.getAccountPassword())){
                /* set up object */
                CategoryDTO cDto = new CategoryDTO();
                model.addAttribute("categoryDTO", cDto);
                CourseDTO courseDTO = new CourseDTO();
                model.addAttribute("courseDTO", courseDTO);
                model.addAttribute("category", categoryService.getAllCategories());
                /* set up session */
                session.setAttribute("CSys", system_accountDTO1.getAccountName());
                /* set up enrolled */
                List<Enrolled> enrolledList = enrolledService.findByAccountId(system_accountDTO1.getAccountID());
                session.setAttribute("enrolledList", enrolledList);
                /* set up role */
                int role = system_accountDTO1.getRoleID().getRoleID();
                session.setAttribute("role", role);
                if(role==2){
                    return dashboardController.getDashboard(model, request, response);
                }
                else{
                    return "/home";
                }
            }
            else{
                /* set up object */
                CategoryDTO cDto = new CategoryDTO();
                model.addAttribute("categoryDTO", cDto);
                CourseDTO courseDTO = new CourseDTO();
                model.addAttribute("courseDTO", courseDTO);
                model.addAttribute("category", categoryService.getAllCategories());
                /* set up object */
                model.addAttribute("error", "Invalid Account Name or Password");
                return "login";
            }
        }
        else {
            /* set up object */
            CategoryDTO cDto = new CategoryDTO();
            model.addAttribute("categoryDTO", cDto);
            CourseDTO courseDTO = new CourseDTO();
            model.addAttribute("courseDTO", courseDTO);
            model.addAttribute("category", categoryService.getAllCategories());
            /* send message */
            model.addAttribute("error", "Unknown error");
            return "login";
        }
    }

    @PostMapping("/search")
    public String search(Model model, HttpServletRequest request, HttpServletResponse response,
                         @ModelAttribute("courseDTO") CourseDTO courseDTO){
        CourseDTO courseDTO1 = new CourseDTO();
        model.addAttribute("courseDTO", courseDTO);
        CategoryDTO temp = new CategoryDTO();
        model.addAttribute("categoryDTO", temp);
        model.addAttribute("category", categoryService.getAllCategories());
        model.addAttribute("courseList", courseService.getAllCoursesByCourseNameContaining(courseDTO.getCourseName()));
        return "list";
    }
}
