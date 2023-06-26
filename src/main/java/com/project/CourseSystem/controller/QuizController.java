package com.project.CourseSystem.controller;

import com.project.CourseSystem.dto.AnswerDTO;
import com.project.CourseSystem.dto.QuestionDTO;
import com.project.CourseSystem.dto.QuizDTO;
import com.project.CourseSystem.service.AnswerService;
import com.project.CourseSystem.service.QuestionService;
import com.project.CourseSystem.service.QuizService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class QuizController {

    QuizService quizService;

    QuestionService questionService;

    AnswerService answerService;
    public QuizController(QuizService quizService, QuestionService questionService
    , AnswerService answerService){
        this.quizService = quizService;
        this.questionService = questionService;
        this.answerService = answerService;
    }

    @GetMapping("quiz")
    public String getQuiz(@RequestParam("lessonID") Integer lessonID, Model model, HttpServletRequest request, HttpServletResponse response){
        QuizDTO quizDTO = quizService.getAllByLessonID(lessonID);
        model.addAttribute("quiz", quizDTO);
        List<QuestionDTO> questions = questionService.getAllByQuizID(quizDTO.getQuizID());
        model.addAttribute("questions", questions);
        for(int i = 0; i < questions.size(); i++){
            List<AnswerDTO> answers = answerService.getAllByQuestionId(questions.get(i).getQuestionID());
            model.addAttribute("answers" + i, answers);
        }
        return "quiz";
    }
}
