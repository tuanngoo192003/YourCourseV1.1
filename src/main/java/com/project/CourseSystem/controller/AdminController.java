package com.project.CourseSystem.controller;

import com.project.CourseSystem.converter.AnswerConverter;
import com.project.CourseSystem.converter.CategoryConverter;
import com.project.CourseSystem.converter.QuestionConverter;
import com.project.CourseSystem.converter.QuizConverter;
import com.project.CourseSystem.dto.*;
import com.project.CourseSystem.entity.*;
import com.project.CourseSystem.service.*;
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
import java.util.Date;
import java.util.List;

@Controller
public class AdminController {

    CourseController courseController;

    AuthController authController;

    AccountService accountService;

    CourseService courseService;

    GoogleDriveService driveService;

    CategoryConverter categoryConverter;

    CategoryService categoryService;

    LearnController learnController;

    LessonService lessonService;

    LearningMaterialService learningMaterialService;

    QuizService quizService;

    QuestionService questionService;

    AnswerService answerService;

    AnswerConverter answerConverter;

    QuestionConverter questionConverter;

    QuizConverter quizConverter;

    AdminController(CourseController courseController, AuthController authController,
                    AccountService accountService, CourseService courseService,
                    GoogleDriveService driveService, CategoryConverter categoryConverter,
                    CategoryService categoryService, LearnController learnController,
                    LessonService lessonService, LearningMaterialService learningMaterialService,
                    QuizService quizService, QuestionService questionService, AnswerService answerService,
                    AnswerConverter answerConverter, QuestionConverter questionConverter, QuizConverter quizConverter){
        this.courseController = courseController;
        this.authController = authController;
        this.accountService = accountService;
        this.courseService = courseService;
        this.driveService = driveService;
        this.categoryConverter = categoryConverter;
        this.categoryService = categoryService;
        this.learnController = learnController;
        this.lessonService = lessonService;
        this.learningMaterialService = learningMaterialService;
        this.quizService = quizService;
        this.questionService = questionService;
        this.answerService = answerService;
        this.answerConverter = answerConverter;
        this.questionConverter = questionConverter;
        this.quizConverter = quizConverter;
    }

    @GetMapping("/cancel")
    public String courseList(Model model, HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        session.removeAttribute("newCourse");
        session.removeAttribute("newCourseDetails");
        return courseController.getCourse(model, request, response);
    }

    @GetMapping("/allCourses")
    public String allCourse(Model model, HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        if(session.getAttribute("CSys") == null){
            return authController.loginPage(model, request, response);
        }
        else{
            String accountName = (String) session.getAttribute("CSys");
            SystemAccountDTO systemAccountDTO = accountService.findUserByAccountName(accountName);
            if (systemAccountDTO.getRoleID().getRoleID() != 2) {
                return authController.loginPage(model, request, response);
            }
            else{
                return courseController.getCourse(model, request, response);
            }
        }
    }

    @GetMapping("/updateCourse")
    public String updateCourse(@RequestParam("courseID") Integer courseID,Model model, HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        if(session.getAttribute("CSys") == null){
            return authController.loginPage(model, request, response);
        }
        else {
            String accountName = (String) session.getAttribute("CSys");
            SystemAccountDTO systemAccountDTO = accountService.findUserByAccountName(accountName);
            if (systemAccountDTO.getRoleID().getRoleID() != 2) {
                return authController.loginPage(model, request, response);
            }
            else{
                CourseDTO courseDTO = courseService.getCourseByID(courseID);
                CourseDetailsDTO courseDetailsDTO = courseService.getCourseDetailsByID(courseID);
                AddCourseForm addCourseForm = new AddCourseForm();
                addCourseForm.setCourseName(courseDTO.getCourseName());
                addCourseForm.setCourseDes(courseDTO.getCourseDes());
                addCourseForm.setStartDate(courseDTO.getStartDate());
                addCourseForm.setEndDate(courseDTO.getEndDate());
                addCourseForm.setPrice(courseDTO.getPrice());
                addCourseForm.setCategory(courseDTO.getCategoryID().getCategoryName());
                addCourseForm.setCourseDetailsContent(courseDetailsDTO.getCourseDetailsContent());
                addCourseForm.setCourseRequirements(courseDetailsDTO.getCourseRequirements());
                addCourseForm.setCourseDescription(courseDetailsDTO.getCourseDescription());
                addCourseForm.setForWho(courseDetailsDTO.getForWho());
                model.addAttribute("addCourseForm", addCourseForm);
                session.setAttribute("courseIDSession", courseID);
                return "editCourse";
            }
        }
    }

    @PostMapping("/editLessons")
    public String editLessons(@RequestParam("file") MultipartFile file, @RequestParam("choice") String choice,
                            @ModelAttribute("addCourseForm") AddCourseForm addCourseForm, Model model,
                            HttpServletRequest request, HttpServletResponse response){
        Course course = new Course();
        HttpSession session = request.getSession();
        Integer courseID = (Integer) session.getAttribute("courseIDSession");
        course.setCourseID(courseID);
        course.setCourseName(addCourseForm.getCourseName());
        course.setCourseDes(addCourseForm.getCourseDes());
        course.setPrice(addCourseForm.getPrice());
        course.setCategoryID(categoryConverter.convertDtoToEtity(categoryService.getCategoryByName(addCourseForm.getCategory())));
        Date date = addCourseForm.getStartDate();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        course.setStartDate(sqlDate);
        date = addCourseForm.getEndDate();
        sqlDate = new java.sql.Date(date.getTime());
        course.setEndDate(sqlDate);
        course.setCreatedDate(new java.sql.Date(new Date().getTime()));
        try{
            String fileName = file.getOriginalFilename();
            String mimeType = file.getContentType();
            File tempFile = File.createTempFile("temp", null);// create a temporary file on disk

            file.transferTo(tempFile); // save the uploaded file to the temporary file

            com.google.api.services.drive.model.File file1 = driveService.uploadFile(tempFile.getName(), tempFile.getAbsolutePath(), "image/jpg", "CourseAvatar");
            String fileId = file1.getId();

            /* save course image */
            course.setCourseImage(fileId);
        }
        catch(IOException e){

        }
        //Save courseDetails
        CourseDetails courseDetails = new CourseDetails();
        CourseDetailsDTO courseDetailsDTO = courseService.getCourseDetailsByID(courseID);
        courseDetails.setCourseDetailsID(courseDetailsDTO.getCourseDetailsID());
        courseDetails.setCourseID(course);
        courseDetails.setCourseDescription(addCourseForm.getCourseDescription());
        courseDetails.setCourseRequirements(addCourseForm.getCourseRequirements());
        courseDetails.setCourseDetailsContent(addCourseForm.getCourseDetailsContent());
        courseDetails.setForWho(addCourseForm.getForWho());

        if(choice.equals("Edit Lessons")){
            session.setAttribute("newCourse", course);

            session.setAttribute("newCourseDetails", courseDetails);

            //set model to input lesson and material
            List<AddLessonFormDTO> addLessonFormList = new ArrayList<>();
            List<LessonDTO> lessonList = getLessonList(courseID);
            model.addAttribute("lessonList", lessonList);
            for(int i = 0; i < lessonList.size(); i++){
                AddLessonFormDTO addLessonForm = new AddLessonFormDTO();
                List<LearningMaterial> learningMaterial = learningMaterialService.getLearningMaterialByLessonID(lessonList.get(i).getLessonID());
                addLessonForm.setLessonID(lessonList.get(i).getLessonID());
                addLessonForm.setLessonDes(lessonList.get(i).getLessonDes());
                addLessonForm.setLessonName(lessonList.get(i).getLessonName());
                addLessonForm.setLearningMaterialLink(learningMaterial.get(0).getLearningMaterialLink());
                addLessonForm.setLearningMaterialName(learningMaterial.get(0).getLearningMaterialName());
                addLessonFormList.add(addLessonForm);
            }

            model.addAttribute("addLessonFormList", addLessonFormList);

            return "editLessons";
        }
        else{
            courseService.saveCourse(course);
            courseDetails.setUpdatedDate(new java.sql.Date(new Date().getTime()));
            courseService.saveCourseDetails(courseDetails);
            return learnController.learnPage(course.getCourseID() ,model, request, response);
        }
    }

    public List<LessonDTO> getLessonList(Integer courseID){
        List<LessonDTO> lessonList = lessonService.getAllByCourseID(courseID);
        return lessonList;
    }

    @PostMapping("/updateLessons")
    public String updateLesson(@RequestParam("lessonTitle") String lessonTitle, @RequestParam("learningMaterialName") String learningMaterialName,
                               @RequestParam("lessonDes") String lessonDes, @RequestParam("lessonID") Integer lessonID,
                               @RequestParam("InputType") String inputType, @RequestParam("choice") String choice,
                               Model model, HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        if(session.getAttribute("CSys") == null){
            return authController.loginPage(model, request, response);
        }
        else{
            String accountName = (String) session.getAttribute("CSys");
            SystemAccountDTO systemAccountDTO = accountService.findUserByAccountName(accountName);
            if (systemAccountDTO.getRoleID().getRoleID() != 2) {
                return authController.loginPage(model, request, response);
            }
            else{
                if(choice.equals("Update this lesson")){
                    //Update lesson
                    Lesson lesson = new Lesson();
                    lesson.setLessonID(lessonID);
                    lesson.setLessonName(lessonTitle);
                    lesson.setLessonDes(lessonDes);
                    lesson.setQuizID(lessonService.getLessonByLessonID(lessonID).getQuizID());
                    lesson.setCourseID(lessonService.getLessonByLessonID(lessonID).getCourseID());
                    lessonService.saveLesson(lesson);

                    //Update learning material
                    LearningMaterial learningMaterial = new LearningMaterial();
                    if(inputType.equals("pdfLink") || inputType.equals("youtube")){
                        String input = request.getParameter("Input");
                        learningMaterial.setLearningMaterialLink(input);
                    }
                    else if(inputType.equals("fileVideo")){
                        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
                        MultipartFile file = multipartRequest.getFile("Input");
                        learningMaterial.setLearningMaterialLink(updateFile(file));
                    }
                    learningMaterial.setLessonID(lesson);
                    learningMaterial.setLearningMaterialName(learningMaterialName);
                    learningMaterial.setLearningMaterialDes(inputType);
                    learningMaterialService.saveLearningMaterial(learningMaterial);

                    //update course
                    Course course = (Course) session.getAttribute("newCourse");
                    courseService.updateCourse(course);
                    CourseDetails courseDetails = (CourseDetails) session.getAttribute("newCourseDetails");
                    courseDetails.setUpdatedDate(new java.sql.Date(new Date().getTime()));
                    courseService.saveCourseDetails(courseDetails);
                    return learnController.learnPage(course.getCourseID() ,model, request, response);
                }
                else if (choice.equals("Edit this lesson's quiz")) {
                    AddLessonFormDTO addLessonForm = new AddLessonFormDTO();
                    if(inputType.equals("pdfLink") || inputType.equals("youtube")){
                        String input = request.getParameter("Input");
                        addLessonForm.setLearningMaterialLink(input);
                    }
                    else if(inputType.equals("fileVideo")){
                        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
                        MultipartFile file = multipartRequest.getFile("Input");
                        addLessonForm.setLearningMaterialLink(updateFile(file));
                    }
                    addLessonForm.setLessonID(lessonID);
                    addLessonForm.setLessonName(lessonTitle);
                    addLessonForm.setLessonDes(lessonDes);
                    addLessonForm.setLearningMaterialName(learningMaterialName);
                    addLessonForm.setLearningMaterialDes(inputType);
                    session.setAttribute("addLessonForm", addLessonForm);

                    //set model to input quiz
                    Quiz quiz = getQuiz(lessonService.getLessonByLessonID(lessonID));
                    model.addAttribute("quiz", quiz);
                    List<Question> questionList = getQuestions(quiz);
                    model.addAttribute("questionList", questionList);
                    List<Answer> answerList = getAnswers(questionList);
                    model.addAttribute("answerList", answerList);

                    return "editQuiz";
                }
                else if (choice.equals("Cancel")){
                    return courseList(model, request, response);
                } else if(choice.equals("Back to course editor")){
                    Integer courseID = (Integer) session.getAttribute("courseIDSession");
                    return updateCourse(courseID, model, request, response);
                } else{
                    //Delete lesson
                    LearningMaterial learningMaterial = learningMaterialService.getLearningMaterialByLessonID(lessonID).get(0);
                    learningMaterialService.deleteLearningMaterial(learningMaterial.getLearningMaterialID());
                    //QuizDTO quizDTO = quizService.getAllByLessonID(lessonID);
                    Lesson lesson = lessonService.getLessonByLessonID(lessonID);
                    lessonService.deteteLesson(lesson);
                    return updateCourse(lesson.getCourseID().getCourseID(), model, request, response);
                }
            }
        }
    }

    public String updateFile(MultipartFile file){
        try{
            String fileName = file.getOriginalFilename();
            String mimeType = file.getContentType();
            File tempFile = File.createTempFile("temp", null);// create a temporary file on disk

            file.transferTo(tempFile); // save the uploaded file to the temporary file

            com.google.api.services.drive.model.File file1 = driveService.uploadFile(tempFile.getName(), tempFile.getAbsolutePath(), "image/jpg", "CourseAvatar");
            String fileId = file1.getId();

            /* save course image */
            return fileId;
        }
        catch(IOException e){
            return null;
        }
    }

    public Quiz getQuiz(Lesson lesson){
        QuizDTO quizDTO = quizService.getAllByLessonID(lesson.getLessonID());
        Quiz quiz = quizConverter.convertDtoToEntity(quizDTO);
        return quiz;
    }

    public List<Question> getQuestions(Quiz quiz){
        List<QuestionDTO> questionDTOList = questionService.getAllByQuizID(quiz.getQuizID());
        List<Question> questionList = new ArrayList<>();
        for (int i = 0; i < questionDTOList.size(); i++) {
            Question question = questionConverter.convertDtoToEntity(questionDTOList.get(i));
            questionList.add(question);
        }
        return questionList;
    }

    public List<Answer> getAnswers(List<Question> questionList){
        List<Answer> answerList = new ArrayList<>();
        for (int i = 0; i < questionList.size(); i++) {
            List<AnswerDTO> answerDTOList = answerService.getAllByQuestionId(questionList.get(i).getQuestionID());
            for (int j = 0; j < answerDTOList.size(); j++) {
                Answer answer = answerConverter.convertDtoToEntity(answerDTOList.get(j));
                answerList.add(answer);
            }
        }
        return answerList;
    }

    public void deleteQuiz(QuizDTO quizDTO){
        if(quizDTO != null){
            List<QuestionDTO> questionList = questionService.getAllByQuizID(quizDTO.getQuizID());
            for(int i = 0; i < questionList.size(); i++){
                List<AnswerDTO> answerList = answerService.getAllByQuestionId(questionList.get(i).getQuestionID());
                for(int j = 0; j < answerList.size(); j++){
                    AnswerDTO answerDTO = answerList.get(j);
                    Answer answer = answerConverter.convertDtoToEntity(answerDTO);
                    answerService.deleteAnswer(answer);
                }
                QuestionDTO questionDTO = questionList.get(i);
                Question question = questionConverter.convertDtoToEntity(questionDTO);
                questionService.deleteQuestion(question);
            }
            Quiz quiz = quizConverter.convertDtoToEntity(quizDTO);
            quizService.deleteQuiz(quiz);
        }
    }

    @PostMapping("/updateQuiz")
    public String updateQuiz(Model model, HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        Integer courseID = (Integer) session.getAttribute("courseIDSession");
        return learnController.learnPage(courseID, model, request, response);
    }

    @GetMapping("/allUsers")
    public String userList(Model model, HttpServletRequest request, HttpServletResponse response){
        return "userList";
    }
}
