package com.project.CourseSystem.controller;

import com.project.CourseSystem.dto.AnswerDTO;
import com.project.CourseSystem.dto.QuestionDTO;
import com.project.CourseSystem.dto.QuizDTO;
import com.project.CourseSystem.dto.ReportDataDTO;
import com.project.CourseSystem.entity.Question;
import com.project.CourseSystem.entity.QuizRevision;
import com.project.CourseSystem.entity.Report;
import com.project.CourseSystem.service.AnswerService;
import com.project.CourseSystem.service.QuestionService;
import com.project.CourseSystem.service.QuizRevisionService;
import com.project.CourseSystem.service.QuizService;
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
    public QuizController(QuizService quizService, QuestionService questionService
    , AnswerService answerService, AuthController authController, QuizRevisionService quizRevisionService){
        this.quizService = quizService;
        this.questionService = questionService;
        this.answerService = answerService;
        this.authController = authController;
        this.quizRevisionService = quizRevisionService;
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
        }
        return "quizReview";
    }
}
