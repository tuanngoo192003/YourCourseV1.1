package com.project.CourseSystem.controller;

import com.project.CourseSystem.dto.AddLessonFormDTO;
import com.project.CourseSystem.dto.QuizListForm;
import com.project.CourseSystem.entity.LearningMaterial;
import com.project.CourseSystem.entity.Lesson;
import com.project.CourseSystem.service.GoogleDriveService;
import com.project.CourseSystem.service.LearningMaterialService;
import com.project.CourseSystem.service.LessonService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LessonController {

    private LearningMaterialService learningMaterialService;

    private LessonService lessonService;

    private AuthController authController;

    private GoogleDriveService driveService;

    public LessonController(LearningMaterialService learningMaterialService, LessonService lessonService,
                            AuthController authController, GoogleDriveService driveService){
        this.learningMaterialService = learningMaterialService;
        this.lessonService = lessonService;
        this.authController = authController;
        this.driveService = driveService;
    }

    @PostMapping("/addLesson")
    public String addLesson(@ModelAttribute("addLessonForm") AddLessonFormDTO addLessonForm,
                            @RequestParam("InputType") String inputType,
                            Model model, HttpServletRequest request, HttpServletResponse response){
        //add lesson
        HttpSession session = request.getSession();
        if(session.getAttribute("CSys")==null){
            return authController.loginPage(model, request, response);
        }
        else{
            List<AddLessonFormDTO> addLessonFormDTOList = new ArrayList<>();
            addLessonForm.setLessonDes(inputType);
            if(inputType.equals("pdfLink") || inputType.equals("youtube")){
                String input = request.getParameter("Input");
                addLessonForm.setLearningMaterialLink(input);
            }
            else if(inputType.equals("fileVideo")){
                MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
                MultipartFile file = multipartRequest.getFile("Input");
                try{
                    String fileName = file.getOriginalFilename();
                    String mimeType = file.getContentType();
                    File tempFile = File.createTempFile("temp", null);// create a temporary file on disk

                    file.transferTo(tempFile); // save the uploaded file to the temporary file

                    com.google.api.services.drive.model.File file1 = driveService.uploadFile(tempFile.getName(), tempFile.getAbsolutePath(), "video/mp4", "LearningMaterial");
                    String fileId = file1.getId();

                    /* save material */
                    addLessonForm.setLearningMaterialLink(fileId);
                }
                catch(IOException e){

                }
            }
            else{
                MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
                MultipartFile file = multipartRequest.getFile("Input");
                try{
                    String fileName = file.getOriginalFilename();
                    String mimeType = file.getContentType();
                    File tempFile = File.createTempFile("temp", null);// create a temporary file on disk

                    file.transferTo(tempFile); // save the uploaded file to the temporary file

                    com.google.api.services.drive.model.File file1 = driveService.uploadFile(tempFile.getName(), tempFile.getAbsolutePath(), "application/pdf", "LearningMaterial");
                    String fileId = file1.getId();

                    /* save material */
                    addLessonForm.setLearningMaterialLink(fileId);
                }
                catch(IOException e){

                }
            }
            addLessonFormDTOList.add(addLessonForm);
            session.setAttribute("addLessonFormDTOList", addLessonFormDTOList);
        }
        QuizListForm quizListForm = new QuizListForm();
        model.addAttribute("quizListForm", quizListForm);
        return "addQuiz";
    }

    @GetMapping ("/addLessonTest")
    public String addLessonTest(Model model, HttpServletRequest request, HttpServletResponse response){
        //set model to input lesson and material
        AddLessonFormDTO addLessonForm = new AddLessonFormDTO();
        model.addAttribute("addLessonForm", addLessonForm);
        return "addLesson";
    }
}
