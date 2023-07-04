package com.project.CourseSystem.controller;

import com.project.CourseSystem.converter.System_AccountConverter;
import com.project.CourseSystem.dto.CategoryDTO;
import com.project.CourseSystem.dto.CourseDTO;
import com.project.CourseSystem.entity.Course;
import com.project.CourseSystem.entity.Enrolled;
import com.project.CourseSystem.entity.SystemAccount;
import com.project.CourseSystem.service.AccountService;
import com.project.CourseSystem.service.CategoryService;
import com.project.CourseSystem.service.CourseService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CourseController {

    private CourseService courseService;

    private CategoryService categoryService;

    private AccountService accountService;

    private System_AccountConverter system_accountConverter;

    @Autowired
    public CourseController(CourseService courseService,  CategoryService categoryService,
                            AccountService accountService, System_AccountConverter system_accountConverter) {
        this.courseService = courseService;
        this.categoryService = categoryService;
        this.accountService = accountService;
        this.system_accountConverter = system_accountConverter;
    }

    @GetMapping("/course")
    public String getCourse(Model model, HttpServletRequest request, HttpServletResponse response){
        //pagination
        return getPaginated(1, "courseID", "desc", model, request, response);
    }

    @GetMapping("/myCourse")
    public String getMyCourse(Model model, HttpServletRequest request, HttpServletResponse response){
        //pagination
        return getMyCoursePaginated(1, "courseID", "desc", model, request, response);
    }

    @GetMapping("/course/page/{pageNo}")
    public String getPaginated(@PathVariable (value = "pageNo") int pageNo,
                               @RequestParam("sortField") String sortField,
                               @RequestParam("sortDir") String sortDir,
                               Model model, HttpServletRequest request, HttpServletResponse response){
        int pageSize = 2;
        //pagination attribute
        Page<Course> page = courseService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Course> courseList = page.getContent();
        List<Course> courseListTemp = new ArrayList<>();
        HttpSession session = request.getSession();
        if(session.getAttribute("CSys")!=null){
            List<Enrolled> enrolledList = (List<Enrolled>) session.getAttribute("enrolledList");
            String accountName = session.getAttribute("CSys").toString();
            SystemAccount account = system_accountConverter.convertDTOToEntity(accountService.findUserByAccountName(accountName));
            List<Integer> courseIdList = new ArrayList<>();
            for(int i = 0; i < enrolledList.size(); i++){
                if(enrolledList.get(i).getAccountID().getAccountID() == account.getAccountID()){
                    courseIdList.add(enrolledList.get(i).getCourseID().getCourseID());
                }
            }
            for(int i = 0; i < courseList.size(); i++){
                if(!courseIdList.contains(courseList.get(i).getCourseID())){
                    courseListTemp.add(courseList.get(i));
                }
            }
        }
        else {
            courseListTemp.addAll(courseList);
        }


        model.addAttribute("currentPage", pageNo);
        int totalPages = page.getTotalPages();
        int temp = pageSize;
        for(int i = 0; i < courseListTemp.size(); i++){
            if(i>temp){
                totalPages++;
                temp+=pageSize;
            }
        }
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", courseListTemp.size());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc")?"desc":"asc");

        model.addAttribute("courseList", courseListTemp);

        //nav bar attribute
        CategoryDTO cDto = new CategoryDTO();
        CourseDTO courseDTO = new CourseDTO();
        model.addAttribute("courseDTO", courseDTO);
        model.addAttribute("categoryDTO", cDto);
        model.addAttribute("category", categoryService.getAllCategories());
        return "list";
    }

    @GetMapping("/myCourse/page/{pageNo}")
    public String getMyCoursePaginated(@PathVariable (value = "pageNo") int pageNo,
                               @RequestParam("sortField") String sortField,
                               @RequestParam("sortDir") String sortDir,
                               Model model, HttpServletRequest request, HttpServletResponse response){
        int pageSize = 18;
        //pagination attribute
        Page<Course> page = courseService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Course> courseList = page.getContent();
        List<Course> courseListTemp = new ArrayList<>();
        HttpSession session = request.getSession();
        if(session.getAttribute("CSys")!=null){
            List<Enrolled> enrolledList = (List<Enrolled>) session.getAttribute("enrolledList");
            String accountName = session.getAttribute("CSys").toString();
            SystemAccount account = system_accountConverter.convertDTOToEntity(accountService.findUserByAccountName(accountName));
            List<Integer> courseIdList = new ArrayList<>();
            for(int i = 0; i < enrolledList.size(); i++){
                for(int j = 0; j < courseList.size(); j++){
                    if(courseList.get(j).getCourseID() == enrolledList.get(i).getCourseID().getCourseID()){
                        courseListTemp.add(courseList.get(j));
                    }
                }
            }
        }
        else {
            courseListTemp.addAll(courseList);
        }

        model.addAttribute("currentPage", pageNo);
        int totalPages = page.getTotalPages();
        int temp = pageSize;
        for(int i = 0; i < courseListTemp.size(); i++){
            if(i>temp){
                totalPages++;
                temp+=pageSize;
            }
        }
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", courseListTemp.size());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc")?"desc":"asc");

        model.addAttribute("courseList", courseListTemp);

        //nav bar attribute
        CategoryDTO cDto = new CategoryDTO();
        CourseDTO courseDTO = new CourseDTO();
        model.addAttribute("courseDTO", courseDTO);
        model.addAttribute("categoryDTO", cDto);
        model.addAttribute("category", categoryService.getAllCategories());
        return "myCourse";
    }

    @GetMapping("/myCourse/sort/page/{pageNo}")
    public String getMyCoursePaginatedByAttribute(@PathVariable (value = "pageNo") int pageNo,
                                          @RequestParam("sortField") String sortField,
                                          @RequestParam("sortDir") String sortDir,
                                          String attribute, String value,
                                          Model model, HttpServletRequest request, HttpServletResponse response){
        int pageSize = 18;
        //pagination attribute
        Page<Course> page = courseService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Course> courseList = page.getContent();
        List<Course> courseListTemp = new ArrayList<>();
        HttpSession session = request.getSession();
        if(session.getAttribute("CSys")!=null){
            List<Enrolled> enrolledList = (List<Enrolled>) session.getAttribute("enrolledList");
            for(int i = 0; i < enrolledList.size(); i++){
                for(int j = 0; j < courseList.size(); j++){
                    if(courseList.get(j).getCourseID() == enrolledList.get(i).getCourseID().getCourseID()){
                        courseListTemp.add(courseList.get(j));
                    }
                }
            }
        }
        else {
            courseListTemp.addAll(courseList);
        }
        List<Course> courseListTemp1 = new ArrayList<>();
        System.out.println(attribute);
        if(attribute.equals("price")){
            for(int i = 0; i < courseListTemp.size(); i++){
                if(courseListTemp.get(i).getPrice() == 0){
                    courseListTemp1.add(courseListTemp.get(i));
                }
            }
        }
        else{
            for(int i = 0; i < courseListTemp.size(); i++){
                if(courseListTemp.get(i).getCategoryID().getCategoryID() == Integer.parseInt(value)){
                    courseListTemp1.add(courseListTemp.get(i));
                }
            }
        }


        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", courseListTemp1.size());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc")?"desc":"asc");

        model.addAttribute("courseList", courseListTemp1);

        //nav bar attribute
        CategoryDTO cDto = new CategoryDTO();
        CourseDTO courseDTO = new CourseDTO();
        model.addAttribute("courseDTO", courseDTO);
        model.addAttribute("categoryDTO", cDto);
        model.addAttribute("category", categoryService.getAllCategories());
        return "myCourse";
    }

    @GetMapping("/course/sort/page/{pageNo}")
    public String getPaginatedByAttribute(@PathVariable (value = "pageNo") int pageNo,
                               @RequestParam("sortField") String sortField,
                               @RequestParam("sortDir") String sortDir,
                               String attribute, String value,
                               Model model, HttpServletRequest request, HttpServletResponse response){
        int pageSize = 18;
        //pagination attribute
        Page<Course> page = courseService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Course> courseList = page.getContent();
        List<Course> courseListTemp = new ArrayList<>();
        HttpSession session = request.getSession();
        if(session.getAttribute("CSys")!=null){
            List<Enrolled> enrolledList = (List<Enrolled>) session.getAttribute("enrolledList");
            String accountName = session.getAttribute("CSys").toString();
            SystemAccount account = system_accountConverter.convertDTOToEntity(accountService.findUserByAccountName(accountName));
            List<Integer> courseIdList = new ArrayList<>();
            for(int i = 0; i < enrolledList.size(); i++){
                if(enrolledList.get(i).getAccountID().getAccountID() == account.getAccountID()){
                    courseIdList.add(enrolledList.get(i).getCourseID().getCourseID());
                }
            }
            for(int i = 0; i < courseList.size(); i++){
                if(!courseIdList.contains(courseList.get(i).getCourseID())){
                    courseListTemp.add(courseList.get(i));
                }
            }
        }
        else {
            courseListTemp.addAll(courseList);
        }
        List<Course> courseListTemp1 = new ArrayList<>();
        System.out.println(attribute);
        if(attribute.equals("price")){
            for(int i = 0; i < courseListTemp.size(); i++){
                if(courseListTemp.get(i).getPrice() == 0){
                    courseListTemp1.add(courseListTemp.get(i));
                }
            }
        }
        else{
            for(int i = 0; i < courseListTemp.size(); i++){
                if(courseListTemp.get(i).getCategoryID().getCategoryID() == Integer.parseInt(value)){
                    courseListTemp1.add(courseListTemp.get(i));
                }
            }
        }


        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", courseListTemp1.size());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc")?"desc":"asc");

        model.addAttribute("courseList", courseListTemp1);

        //nav bar attribute
        CategoryDTO cDto = new CategoryDTO();
        CourseDTO courseDTO = new CourseDTO();
        model.addAttribute("courseDTO", courseDTO);
        model.addAttribute("categoryDTO", cDto);
        model.addAttribute("category", categoryService.getAllCategories());
        return "list";
    }

    @PostMapping("/myCourseFilter")
    public String myCourseFilter(@ModelAttribute("category") CategoryDTO categoryDTO, Model model,
                         HttpServletRequest request, HttpServletResponse response) {
        CourseDTO courseDTO = new CourseDTO();
        model.addAttribute("courseDTO", courseDTO);
        CategoryDTO temp = new CategoryDTO();
        model.addAttribute("categoryDTO", temp);
        model.addAttribute("category", categoryService.getAllCategories());
        temp = categoryService.getCategoryByName(categoryDTO.getCategoryName());
        int categoryID = temp.getCategoryID();
        model.addAttribute("courseList", courseService.getAllCoursesByCategoryID(categoryID));
        return getMyCoursePaginatedByAttribute(1, "courseID", "asc", "categoryID", String.valueOf(categoryID), model, request, response);
    }

    @PostMapping("/filter")
    public String filter(@ModelAttribute("category") CategoryDTO categoryDTO, Model model,
                         HttpServletRequest request, HttpServletResponse response) {
        CourseDTO courseDTO = new CourseDTO();
        model.addAttribute("courseDTO", courseDTO);
        CategoryDTO temp = new CategoryDTO();
        model.addAttribute("categoryDTO", temp);
        model.addAttribute("category", categoryService.getAllCategories());
        temp = categoryService.getCategoryByName(categoryDTO.getCategoryName());
        int categoryID = temp.getCategoryID();
        model.addAttribute("courseList", courseService.getAllCoursesByCategoryID(categoryID));
        return getPaginatedByAttribute(1, "courseID", "asc", "categoryID", String.valueOf(categoryID), model, request, response);
    }

    @GetMapping("/getCourseByCategoryID")
    public String getCourseByCategoryID(@RequestParam Integer categoryID, Model model,
                         HttpServletRequest request, HttpServletResponse response){
        CourseDTO courseDTO = new CourseDTO();
        model.addAttribute("courseDTO", courseDTO);
        CategoryDTO temp = new CategoryDTO();
        model.addAttribute("categoryDTO", temp);
        model.addAttribute("category", categoryService.getAllCategories());
        model.addAttribute("courseList", courseService.getAllCoursesByCategoryID(categoryID));
        return getPaginated(1, "courseID", "asc", model, request, response);
    }

    @PostMapping("/sort")
    public String sort(@ModelAttribute("courseDTO") CourseDTO courseDTO, @RequestParam("option") String option,
                       Model model, HttpServletRequest request, HttpServletResponse response){
        if(option.equals("Newest")){
            return getPaginated(1, "startDate", "desc", model, request, response);
        }
        else if(option.equals("Oldest")){
            String sortField = "startDate";
            String sortDir = "asc";
            return getPaginated(1, "startDate", "asc", model, request, response);
        }
        else if(option.equals("About to end")){
            String sortField = "endDate";
            String sortDir = "asc";
            return getPaginated(1, "endDate", "asc", model, request, response);
        }
        else if(option.equals("High to low")){
            return getPaginated(1, "price", "desc", model, request, response);
        }
        else if(option.equals("Low to high")){
            String sortField = "price";
            String sortDir = "asc";
            return getPaginated(1, "price", "asc", model, request, response);
        }
        else if(option.equals("Free")){
            String sortField = "price";
            String sortDir = "desc";
            return getPaginatedByAttribute(1, sortField, sortDir, "price", "0", model, request, response);
        }
        else{
            String sortField = "courseID";
            String sortDir = "asc";
            return getPaginated(1, "courseID", "asc", model, request, response);
        }
    }

    @PostMapping("/myCourseSort")
    public String myCourseSort(@ModelAttribute("courseDTO") CourseDTO courseDTO, @RequestParam("option") String option,
                       Model model, HttpServletRequest request, HttpServletResponse response){
        if(option.equals("Newest")){
            return getMyCoursePaginated(1, "startDate", "desc", model, request, response);
        }
        else if(option.equals("Oldest")){
            String sortField = "startDate";
            String sortDir = "asc";
            return getMyCoursePaginated(1, "startDate", "asc", model, request, response);
        }
        else if(option.equals("About to end")){
            String sortField = "endDate";
            String sortDir = "asc";
            return getMyCoursePaginated(1, "endDate", "asc", model, request, response);
        }
        else if(option.equals("High to low")){
            return getMyCoursePaginated(1, "price", "desc", model, request, response);
        }
        else if(option.equals("Low to high")){
            String sortField = "price";
            String sortDir = "asc";
            return getMyCoursePaginated(1, "price", "asc", model, request, response);
        }
        else if(option.equals("Free")){
            String sortField = "price";
            String sortDir = "desc";
            return getMyCoursePaginatedByAttribute(1, sortField, sortDir, "price", "0", model, request, response);
        }
        else{
            String sortField = "courseID";
            String sortDir = "asc";
            return getMyCoursePaginated(1, "courseID", "asc", model, request, response);
        }
    }
}
