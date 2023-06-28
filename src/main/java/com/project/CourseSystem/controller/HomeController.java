package com.project.CourseSystem.controller;

import com.project.CourseSystem.dto.CategoryDTO;
import com.project.CourseSystem.dto.CourseDTO;
import com.project.CourseSystem.dto.SystemAccountDTO;
import com.project.CourseSystem.entity.Category;
import com.project.CourseSystem.entity.Enrolled;
import com.project.CourseSystem.service.AccountService;
import com.project.CourseSystem.service.CategoryService;
import com.project.CourseSystem.service.CourseService;
import com.project.CourseSystem.service.EnrolledService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class HomeController {

    private CategoryService categoryService;

    private AccountService accountService;

    private CourseService courseService;

    private EnrolledService enrolledService;

    public HomeController(AccountService accountService, CategoryService categoryService,
                          CourseService courseService, EnrolledService enrolledService){
        this.categoryService = categoryService;
        this.accountService = accountService;
        this.courseService = courseService;
        this.enrolledService = enrolledService;
    }

    /* home page */
    @GetMapping("/home")
    public String homePage(Model model, HttpServletRequest request, HttpServletResponse response){
        /* set up object */
        CategoryDTO cDto = new CategoryDTO();
        model.addAttribute("categoryDTO", cDto);
        CourseDTO courseDTO = new CourseDTO();
        model.addAttribute("courseDTO", courseDTO);
        model.addAttribute("category", categoryService.getAllCategories());
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
                return "/home";
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
