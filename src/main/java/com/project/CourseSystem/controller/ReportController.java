package com.project.CourseSystem.controller;

import com.project.CourseSystem.dto.ReportDataDTO;
import com.project.CourseSystem.dto.SystemAccountDTO;
import com.project.CourseSystem.entity.*;
import com.project.CourseSystem.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ReportController {

    ReportService reportService;

    AccountService accountService;

    UserService userService;

    AuthController authController;

    QuestionService questionService;

    AnswerService answerService;

    QuizRevisionService quizRevisionService;

    QuizController quizController;

    QuizService quizService;

    ReportController(ReportService reportService, AccountService accountService
                     , UserService userService, AuthController authController,
                     QuestionService questionService, AnswerService answerService,
                     QuizRevisionService quizRevisionService,
                     QuizController quizController, QuizService quizService){
        this.reportService = reportService;
        this.accountService = accountService;
        this.userService = userService;
        this.authController = authController;
        this.questionService = questionService;
        this.answerService = answerService;
        this.quizRevisionService = quizRevisionService;
        this.quizController = quizController;
        this.quizService = quizService;
    }

    @PostMapping("/saveReport")
    public String saveReport(@RequestParam("inputQuizID") int quizID,
            @ModelAttribute("reportDTO")ReportDataDTO reportDataDTO,
            Model model, HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        if(session.getAttribute("CSys")!=null){
            Quiz quiz = quizService.getQuizById(quizID);
            //Save report
            String accountName = (String) session.getAttribute("CSys");
            SystemAccountDTO systemAccountDTO = accountService.findUserByAccountName(accountName);
            UserInfo userInfo = userService.findUser(systemAccountDTO.getAccountID());
            Report report = new Report();
            report.setMark(reportDataDTO.getMark());
            report.setCompletedDate(new java.sql.Date(System.currentTimeMillis()));
            report.setQuizID(quiz);
            report.setUserID(userInfo);
            reportService.saveReport(report);


            //Save quizRevision
            List<String> askedQuestion = reportDataDTO.getAskedQuestions();
            List<String> chosenAnswer = reportDataDTO.getAnswers();
            for (int i = 0; i < askedQuestion.size(); i++){
                System.out.println(askedQuestion.get(i));
                System.out.println(chosenAnswer.get(i));
            }
            for(int i = 0; i < askedQuestion.size(); i++){
                Question question = questionService.getQuestionByContentAndQuizId(askedQuestion.get(i), quiz.getQuizID());
                Answer answer = answerService.getAnswerByQuestionIDAndAnswerOrdinal(question.getQuestionID(), chosenAnswer.get(i));
                System.out.println(answer.getAnswerID());
                QuizRevision quizRevision = new QuizRevision();
                quizRevision.setQuestionID(question);
                quizRevision.setAnswerID(answer);
                quizRevision.setReportID(report);
                quizRevisionService.saveQuizRevision(quizRevision);
            }
            return quizController.getQuizReview(report, model, request, response);
        }
        else{
            return authController.loginPage(model, request, response);
        }
    }
}
