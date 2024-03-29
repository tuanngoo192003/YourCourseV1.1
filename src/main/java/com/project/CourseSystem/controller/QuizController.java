package com.project.CourseSystem.controller;

import com.project.CourseSystem.converter.CourseConverter;
import com.project.CourseSystem.dto.*;
import com.project.CourseSystem.entity.*;
import com.project.CourseSystem.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuizController {

    final private QuizService quizService;

    final private QuestionService questionService;

    final private AnswerService answerService;

    final private AuthController authController;

    final private QuizRevisionService quizRevisionService;

    final private CategoryService categoryService;

    final private ReportService reportService;

    final private CourseService courseService;

    final private CourseConverter courseConverter;

    public QuizController(QuizService quizService, QuestionService questionService
    , AnswerService answerService, AuthController authController, QuizRevisionService quizRevisionService,
                          CategoryService categoryService, ReportService reportService,
                          CourseService courseService,  CourseConverter courseConverter){
        this.quizService = quizService;
        this.questionService = questionService;
        this.answerService = answerService;
        this.authController = authController;
        this.quizRevisionService = quizRevisionService;
        this.categoryService = categoryService;
        this.reportService = reportService;
        this.courseService = courseService;
        this.courseConverter = courseConverter;
    }

    //Quiz handle
    @GetMapping("/courseDetails/learn/quiz")
    public String getQuiz(@RequestParam("lessonID") Integer lessonID, Model model, HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        if(session.getAttribute("CSys")==null){
            CategoryDTO cDto = new CategoryDTO();
            model.addAttribute("categoryDTO", cDto);
            CourseDTO courseDTO = new CourseDTO();
            model.addAttribute("courseDTO", courseDTO);
            model.addAttribute("category", categoryService.getAllCategories());
            model.addAttribute("system_account", new SystemAccountDTO());
            return "redirect:/login";
        }
        else{
            QuizDTO quizDTO = quizService.getAllByLessonID(lessonID);
            System.out.println(quizDTO.getQuizID());
            model.addAttribute("quiz", quizDTO);
            List<QuestionDTO> questions = questionService.getAllByQuizID(quizDTO.getQuizID());
            model.addAttribute("questions", questions);
            List<AnswerDTO> answerDTOList = new ArrayList<>();
            for(int i = 0; i < questions.size(); i++){
                List<AnswerDTO> answers = answerService.getAllByQuestionId(questions.get(i).getQuestionID());
                answerDTOList.addAll(answers);
            }
            model.addAttribute("answers", answerDTOList);

            ReportDataDTO reportDTO = new ReportDataDTO();
            model.addAttribute("reportDTO", reportDTO);
            return "quiz";
        }
    }

    //Quiz review
    @GetMapping("courseDetails/learn/quizReview")
    public String getQuizReview(Report report, Model model, HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        if(session.getAttribute("CSys")==null){
            return authController.loginPage(model, request, response);
        }
        else{
            List<QuizRevision> quizRevisions = quizRevisionService.getQuizRevisionByReportID(report.getReportID());
            model.addAttribute("quizRevisions", quizRevisions);
            //Get all questions asked in the quiz
            List<Question> questionList = new ArrayList<>();
            for(int i = 0; i < quizRevisions.size(); i++){
                Question question = questionService.getQuestionById(quizRevisions.get(i).getQuestionID().getQuestionID());
                questionList.add(question);
            }
            model.addAttribute("questionsDid", questionList);
            List<AnswerDTO> answerDTOList = new ArrayList<>();
            for(int i = 0; i < questionList.size(); i++){
                List<AnswerDTO> answers = answerService.getAllByQuestionId(questionList.get(i).getQuestionID());
                answerDTOList.addAll(answers);
            }
            model.addAttribute("answers", answerDTOList);
            model.addAttribute("report", report);

            //Get chosen answer
            List<Answer> chosenAnswers = new ArrayList<>();
            for(int i = 0; i < quizRevisions.size(); i++){
                Answer answer = answerService.getById(quizRevisions.get(i).getAnswerID().getAnswerID());
                chosenAnswers.add(answer);
            }
            model.addAttribute("chosenAnswers", chosenAnswers);
            Quiz quiz = new Quiz();
            quiz = quizService.getQuizById(report.getQuizID().getQuizID());
            model.addAttribute("quiz", quiz);

            Course course = courseConverter.convertDtoToEtity(courseService.getCourseByID(quiz.getCourseID().getCourseID()));
            model.addAttribute("currentCourse", course);
        }


        CategoryDTO cDto = new CategoryDTO();
        model.addAttribute("categoryDTO", cDto);
        CourseDTO courseDTO = new CourseDTO();
        model.addAttribute("courseDTO", courseDTO);
        model.addAttribute("category", categoryService.getAllCategories());
        model.addAttribute("system_account", new SystemAccountDTO());
        return "quizReview";
    }

    //Quiz review test
    @GetMapping("quizReviewTest")
    public String getQuizReviewTest(Model model, HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        if(session.getAttribute("CSys")==null){
            return authController.loginPage(model, request, response);
        }
        else{
            Report report = reportService.getReportByReportID(1);
            List<QuizRevision> quizRevisions = quizRevisionService.getQuizRevisionByReportID(report.getReportID());
            model.addAttribute("quizRevisions", quizRevisions);
            //Get all questions asked in the quiz
            List<Question> questionList = new ArrayList<>();
            for(int i = 0; i < quizRevisions.size(); i++){
                Question question = questionService.getQuestionById(quizRevisions.get(i).getQuestionID().getQuestionID());
                questionList.add(question);
            }
            model.addAttribute("questionsDid", questionList);
            List<AnswerDTO> answerDTOList = new ArrayList<>();
            for(int i = 0; i < questionList.size(); i++){
                List<AnswerDTO> answers = answerService.getAllByQuestionId(questionList.get(i).getQuestionID());
                answerDTOList.addAll(answers);
            }
            model.addAttribute("answers", answerDTOList);
            model.addAttribute("report", report);

            //Get chosen answer
            List<Answer> chosenAnswers = new ArrayList<>();
            for(int i = 0; i < quizRevisions.size(); i++){
                Answer answer = answerService.getById(quizRevisions.get(i).getAnswerID().getAnswerID());
                chosenAnswers.add(answer);
            }
            model.addAttribute("chosenAnswers", chosenAnswers);
            Quiz quiz = new Quiz();
            quiz = quizService.getQuizById(report.getQuizID().getQuizID());
            model.addAttribute("quiz", quiz);

            Course course = courseConverter.convertDtoToEtity(courseService.getCourseByID(quiz.getCourseID().getCourseID()));
            model.addAttribute("currentCourse", course);
        }


        CategoryDTO cDto = new CategoryDTO();
        model.addAttribute("categoryDTO", cDto);
        CourseDTO courseDTO = new CourseDTO();
        model.addAttribute("courseDTO", courseDTO);
        model.addAttribute("category", categoryService.getAllCategories());
        model.addAttribute("system_account", new SystemAccountDTO());
        return "quizReview";
    }


    //Add quiz test
    @GetMapping("addQuizTest")
    public String addQuizTest(Model model, HttpServletRequest request, HttpServletResponse response){
        QuizListForm quizListForm = new QuizListForm();
        model.addAttribute("quizListForm", quizListForm);
        return "addQuiz";
    }
}
