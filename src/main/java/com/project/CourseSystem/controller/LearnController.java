package com.project.CourseSystem.controller;

import com.project.CourseSystem.converter.EnrolledConverter;
import com.project.CourseSystem.dto.*;
import com.project.CourseSystem.entity.*;
import com.project.CourseSystem.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LearnController {

    private CourseService courseService;

    private QuizService quizService;

    private LessonService lessonService;

    private CategoryService categoryService;;

    private EnrolledService enrolledService;

    private EnrolledConverter enrolledConverter;

    private AccountService accountService;

    private LearningMaterialService learningMaterialService;

    private LearnController(LessonService lessonService, QuizService quizService, CourseService courseService
    , CategoryService categoryService, EnrolledService enrolledService, EnrolledConverter enrolledConverter,
                            AccountService accountService, LearningMaterialService learningMaterialService) {
        this.lessonService = lessonService;
        this.quizService = quizService;
        this.courseService = courseService;
        this.categoryService = categoryService;
        this.enrolledService = enrolledService;
        this.enrolledConverter = enrolledConverter;
        this.accountService = accountService;
        this.learningMaterialService = learningMaterialService;
    }

    @GetMapping("/learn")
    public String learnPage(@RequestParam Integer courseID, Model model,
                            HttpServletRequest request, HttpServletResponse response) {
        if(courseID == null) {
            return "redirect:/course";
        }
        else {
            int id = courseID.intValue();
            CourseDTO course = courseService.getCourseByID(id);

            /* get lesson list */
            List<LessonDTO> lessonList = lessonService.getAllByCourseID(id);
            List<QuizDTO> quizList = new ArrayList<>();
            for (LessonDTO lesson : lessonList) {
                quizList.add(quizService.getAllByLessonID(lesson.getLessonID()));
            }
            System.out.println(lessonList.size());
            //Get course details
            CourseDetailsDTO courseDetailsDTO =courseService.getCourseDetailsByID(id);

            //Get learning material
            List<LearningMaterial> learningMaterialList = new ArrayList<>();
            for(int i = 0; i < lessonList.size(); i++){
                List<LearningMaterial> temp = learningMaterialService.getLearningMaterialByLessonID(lessonList.get(i).getLessonID());
                for(int j = 0; j < temp.size(); j++){
                    learningMaterialList.add(temp.get(j));
                }
            }
            model.addAttribute("learningMaterialList", learningMaterialList);

            /* what you can learn */
            List<String> whatYouCanLearn = new ArrayList<>();
            for(String s: courseDetailsDTO.getCourseDetailsContent().split("/")){
                whatYouCanLearn.add(s);
            }
            model.addAttribute("whatYouCanLearn", whatYouCanLearn);

            /* Requirement */
            List<String> requirement = new ArrayList<>();
            for(String s: courseDetailsDTO.getCourseRequirements().split("/")){
                requirement.add(s);
            }
            model.addAttribute("requirement", requirement);

            /* Description */
            List<String> courseDes = new ArrayList<>();
            for(String s: courseDetailsDTO.getCourseDescription().split("/")){
                courseDes.add(s);
            }
            model.addAttribute("courseDes", courseDes);

            /* Who is this course for */
            List<String> whoFor = new ArrayList<>();
            for(String s: courseDetailsDTO.getForWho().split("/")){
                whoFor.add(s);
            }
            model.addAttribute("whoFor", whoFor);

            /* set model attribute */
            CategoryDTO cDto = new CategoryDTO();
            model.addAttribute("categoryDTO", cDto);
            CourseDTO courseDTO = new CourseDTO();
            model.addAttribute("courseDTO", courseDTO);
            model.addAttribute("category", categoryService.getAllCategories());
            model.addAttribute("course", course);
            model.addAttribute("lessonList", lessonList);
            model.addAttribute("quizList", quizList);

            /* check if user is enrolled */
            HttpSession session = request.getSession();
            if(session.getAttribute("CSys")!=null){
                String accountName = (String) session.getAttribute("CSysName");
                SystemAccountDTO accountDTO = accountService.findUserByAccountName(accountName);
                Enrolled enrolled = enrolledService.findByAccountIdAndCourseID(accountDTO.getAccountID(), courseID);
                if(enrolled != null){
                    EnrolledDTO enrolledDTO = enrolledConverter.convertEntityToDTO(enrolled);
                    model.addAttribute("enrolledDTO", enrolledDTO);
                }
            }
            return "learn";
        }
    }
}
