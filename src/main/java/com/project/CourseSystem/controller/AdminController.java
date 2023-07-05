package com.project.CourseSystem.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    CourseController courseController;

    AdminController(CourseController courseController){
        this.courseController = courseController;
    }

    @GetMapping("/allCourses")
    public String courseList(Model model, HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        session.removeAttribute("newCourse");
        session.removeAttribute("newCourseDetails");
        return courseController.getCourse(model, request, response);
    }

    @GetMapping("/allUsers")
    public String userList(Model model, HttpServletRequest request, HttpServletResponse response){
        return "userList";
    }
}
