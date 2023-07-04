package com.project.CourseSystem.controller;

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

    QuizService quizService;

    QuestionService questionService;

    AnswerService answerService;

    AuthController authController;

    QuizRevisionService quizRevisionService;

    CategoryService categoryService;

    ReportService reportService;

    public QuizController(QuizService quizService, QuestionService questionService
    , AnswerService answerService, AuthController authController, QuizRevisionService quizRevisionService,
                          CategoryService categoryService, ReportService reportService){
        this.quizService = quizService;
        this.questionService = questionService;
        this.answerService = answerService;
        this.authController = authController;
        this.quizRevisionService = quizRevisionService;
        this.categoryService = categoryService;
        this.reportService = reportService;
    }

    //Quiz handle
    @GetMapping("quiz")
    public String getQuiz(@RequestParam("lessonID") Integer lessonID, Model model, HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        if(session.getAttribute("CSys")==null){
            return authController.loginPage(model, request, response);
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
    @GetMapping("quizReview")
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
            model.addAttribute("questions", questionList);
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
        }

        CategoryDTO cDto = new CategoryDTO();
        model.addAttribute("categoryDTO", cDto);
        CourseDTO courseDTO = new CourseDTO();
        model.addAttribute("courseDTO", courseDTO);
        model.addAttribute("category", categoryService.getAllCategories());
        model.addAttribute("system_account", new SystemAccountDTO());
        return "quizReview";
    }

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
        }

        CategoryDTO cDto = new CategoryDTO();
        model.addAttribute("categoryDTO", cDto);
        CourseDTO courseDTO = new CourseDTO();
        model.addAttribute("courseDTO", courseDTO);
        model.addAttribute("category", categoryService.getAllCategories());
        model.addAttribute("system_account", new SystemAccountDTO());
        return "quizReview";
    }

}
