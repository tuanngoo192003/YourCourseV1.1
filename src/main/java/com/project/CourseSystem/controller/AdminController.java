package com.project.CourseSystem.controller;

import com.project.CourseSystem.converter.*;
import com.project.CourseSystem.dto.*;
import com.project.CourseSystem.entity.*;
import com.project.CourseSystem.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.sql.Time;
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

    CourseConverter courseConverter;

    UserService userService;

    PaymentService paymentService;

    LessonConverter lessonConverter;

    EnrolledService enrolledService;

    DiscountService discountService;

    RatingCourseService ratingCourseService;

    ReportService reportService;

    QuizRevisionService quizRevisionService;

    AdminController(CourseController courseController, AuthController authController,
                    AccountService accountService, CourseService courseService,
                    GoogleDriveService driveService, CategoryConverter categoryConverter,
                    CategoryService categoryService, LearnController learnController,
                    LessonService lessonService, LearningMaterialService learningMaterialService,
                    QuizService quizService, QuestionService questionService, AnswerService answerService,
                    AnswerConverter answerConverter, QuestionConverter questionConverter,
                    QuizConverter quizConverter, CourseConverter courseConverter,
                    UserService userService, PaymentService paymentService,
                    LessonConverter lessonConverter, EnrolledService enrolledService,
                    DiscountService discountService, DiscountConverter discountConverter,
                    RatingCourseService ratingCourseService, ReportService reportService,
                    QuizRevisionService quizRevisionService){
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
        this.courseConverter = courseConverter;
        this.userService = userService;
        this.paymentService = paymentService;
        this.lessonConverter = lessonConverter;
        this.enrolledService = enrolledService;
        this.discountService = discountService;
        this.ratingCourseService = ratingCourseService;
        this.reportService = reportService;
        this.quizRevisionService = quizRevisionService;
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
            return learnController.courseDetailsPage(course.getCourseID() ,model, request, response);
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
                    return learnController.courseDetailsPage(course.getCourseID() ,model, request, response);
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

                    List<Integer> listOfQuestionID = new ArrayList<>();
                    for(int i = 0; i < questionList.size(); i++){
                        listOfQuestionID.add(questionList.get(i).getQuestionID());
                    }
                    session.setAttribute("listOfQuestionID", listOfQuestionID);
                    List<Integer> listOfAnswerID = new ArrayList<>();
                    for(int i = 0; i < answerList.size(); i++){
                        listOfAnswerID.add(answerList.get(i).getAnswerID());
                    }
                    session.setAttribute("listOfAnswerID", listOfAnswerID);
                    session.setAttribute("numberOfNewQuestion", 0);

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
    public String updateQuiz(@RequestParam("questionDTO-content")String questionContents,
                             @RequestParam("answerDTO-content") String answerContents,
                             Model model, HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        Integer courseID = (Integer) session.getAttribute("courseIDSession");
        //Update quiz
        String quizID = request.getParameter("quizID");
        String quizName = request.getParameter("quizName");
        String quizDes = request.getParameter("quizDes");
        Quiz quiz = new Quiz();
        quiz.setQuizID(Integer.parseInt(quizID));
        quiz.setQuizName(quizName);
        quiz.setQuizDes(quizDes);
        Time time = new Time(0,0,0);
        quiz.setQuizPeriod(time);
        quiz.setCourseID(courseConverter.convertDtoToEtity(courseService.getCourseByID(courseID)));
        quizService.saveQuiz(quiz);

        //Update question and answer

        //Get list of questionID and answerID
        List<Integer> listOfQuestionID = (List<Integer>) session.getAttribute("listOfQuestionID");
        List<Integer> listOfAnswerID = (List<Integer>) session.getAttribute("listOfAnswerID");

        for(int i = 0; i < listOfQuestionID.size(); i++){
            Question question = new Question();
            Integer questionID = listOfQuestionID.get(i);
            String questionContent = request.getParameter("questionDTO-content" + listOfQuestionID.get(i));
            question.setQuestionID(questionID);
            question.setContent(questionContent);
            question.setQuizID(quiz);
            questionService.saveQuestion(question);
            List<AnswerDTO> answerDTOList = answerService.getAllByQuestionId(questionID);
            for (int j = 0; j < answerDTOList.size(); j++) {
                Integer answerID = answerDTOList.get(j).getAnswerID();
                Answer answer = answerService.getById(answerID);;
                String answerContent = request.getParameter("answerDTO-content" + answerDTOList.get(j).getAnswerID());
                String isCorrect = request.getParameter("isCorrect"+questionID);
                if(isCorrect.equals(answer.getAnswerOrdinal())){
                    answer.setIsCorrect("right");
                } else{
                    answer.setIsCorrect("wrong");
                }
                answer.setContent(answerContent);

                answerService.updateAnswer(answer);
            }
        }

        //Get new question and answer
        int index = questionContents.indexOf(",");
        if (index != -1) { // checks if a comma exists in the string
            questionContents = questionContents.substring(index + 1); // removes the first substring before the first comma
        }
        index = -1;
        for (int i = 0; i < 4; i++) { // iterates four times to skip the first four substrings
            index = answerContents.indexOf(",");
            if (index != -1) {
                answerContents = answerContents.substring(index + 1);
            }
        }
        System.err.println(questionContents);
        System.err.println(answerContents);

        saveNewQuestionAndAnswer(questionContents, answerContents, quiz, request, response);


        return learnController.courseDetailsPage(courseID, model, request, response);
    }

    public void saveNewQuestionAndAnswer(String questionContents, String answerContents, Quiz quiz,
                                         HttpServletRequest request, HttpServletResponse response){
        List<QuestionDTO> questionList = new ArrayList<>();
        List<AnswerDTO> answerList = new ArrayList<>();
        int tempIndex = 0;
        for(int i = 0; i < questionContents.length(); i++){
            String temp;
            if(questionContents.charAt(i) == ',' || i == questionContents.length()-1){
                QuestionDTO questionDTO = new QuestionDTO();
                temp = questionContents.substring(tempIndex, i);
                questionDTO.setContent(temp);
                questionList.add(questionDTO);
                tempIndex = i+1;
            }
        }
        tempIndex = 0;
        int ordinalCount = 0;
        for(int i = 0; i < answerContents.length(); i++){
            int isCorrectIndex = 1;
            String temp;
            AnswerDTO answerDTO = new AnswerDTO();
            if(answerContents.charAt(i) == ',' || i == answerContents.length()-1){
                temp = answerContents.substring(tempIndex, i);
                answerDTO.setContent(temp);
                String answerOrdinal = "";
                if(ordinalCount == 0){ answerDTO.setAnswerOrdinal("optionA"); answerOrdinal = "optionA"; ordinalCount=1;}
                else if (ordinalCount == 1){ answerDTO.setAnswerOrdinal("optionB"); answerOrdinal = "optionB"; ordinalCount=2;}
                else if (ordinalCount == 2){ answerDTO.setAnswerOrdinal("optionC"); answerOrdinal = "optionC"; ordinalCount=3;}
                else if (ordinalCount == 3){ answerDTO.setAnswerOrdinal("optionD"); answerOrdinal = "optionD";ordinalCount=0;}
                    String name = "isCorrect"+isCorrectIndex;
                    String isCorrect = request.getParameter(name);
                    System.out.println(name);
                    System.out.println(isCorrect);
                    System.out.println("iscorrect test---------------");
                    if(answerOrdinal.equals(isCorrect)){
                        answerDTO.setIsCorrect("right");
                    }
                    else{
                        answerDTO.setIsCorrect("wrong");
                    }
                answerList.add(answerDTO);
                isCorrectIndex++;
                tempIndex = i+1;
            }
        }
        //save Question to database
        QuestionDTO questionDTO = new QuestionDTO();
        /* Algorithm to save answer
         *  For each question, save that question and 4 answer to the database
         * */
        int plusIndex = 3;
        for(int i = 0; i < questionList.size(); i++){
            questionDTO = questionList.get(i);
            Question question = new Question();
            question.setQuizID(quiz);
            question.setContent(questionDTO.getContent());
            question = questionService.saveQuestion(question);
            if(i==questionList.size()-1){
                AnswerDTO answerDTO4 = answerList.get(answerList.size()-4);
                Answer answer3 = new Answer();
                answer3.setQuestionID(question);
                answer3.setContent(answerDTO4.getContent());
                answer3.setAnswerOrdinal(answerDTO4.getAnswerOrdinal());
                answer3.setIsCorrect(answerDTO4.getIsCorrect());
                answerService.save(answer3);

                AnswerDTO answerDTO3 = answerList.get(answerList.size()-3);
                Answer answer2 = new Answer();
                answer2.setQuestionID(question);
                answer2.setContent(answerDTO3.getContent());
                answer2.setAnswerOrdinal(answerDTO3.getAnswerOrdinal());
                answer2.setIsCorrect(answerDTO3.getIsCorrect());
                answerService.save(answer2);

                AnswerDTO answerDTO2 = answerList.get(answerList.size()-2);
                Answer answer1 = new Answer();
                answer1.setQuestionID(question);
                answer1.setContent(answerDTO2.getContent());
                answer1.setAnswerOrdinal(answerDTO2.getAnswerOrdinal());
                answer1.setIsCorrect(answerDTO2.getIsCorrect());
                answerService.save(answer1);

                AnswerDTO answerDTO1 = answerList.get(answerList.size()-1);
                Answer answer = new Answer();
                answer.setQuestionID(question);
                answer.setContent(answerDTO1.getContent());
                answer.setAnswerOrdinal(answerDTO1.getAnswerOrdinal());
                answer.setIsCorrect(answerDTO1.getIsCorrect());
                answerService.save(answer);
            }
            else{
                if(i == 0){
                    for(int j = 0; j < 4; j++){
                        AnswerDTO answerDTO1 = answerList.get(j);
                        Answer answer = new Answer();
                        answer.setQuestionID(question);
                        answer.setContent(answerDTO1.getContent());
                        answer.setAnswerOrdinal(answerDTO1.getAnswerOrdinal());
                        answer.setIsCorrect(answerDTO1.getIsCorrect());
                        answerService.save(answer);
                    }
                }else{
                    int j = i+plusIndex;
                    int n = j + 4;
                    //save Answer
                    while(j<n){
                        AnswerDTO answerDTO1 = answerList.get(j);
                        Answer answer = new Answer();
                        answer.setQuestionID(question);
                        answer.setContent(answerDTO1.getContent());
                        answer.setAnswerOrdinal(answerDTO1.getAnswerOrdinal());
                        answer.setIsCorrect(answerDTO1.getIsCorrect());
                        answerService.save(answer);
                        j++;
                    }
                    plusIndex+=3;
                }
            }
        }
    }

    @GetMapping("deleteCourse")
    public String deleteCourse(@RequestParam("courseID") Integer courseID, Model model, HttpServletRequest request, HttpServletResponse response){
        courseService.deleteCourseDetails(courseID);
        CourseDTO course = courseService.getCourseByID(courseID);
        List<LessonDTO> lessons = lessonService.getAllByCourseID(courseID);
        for (LessonDTO lesson : lessons){
            List<LearningMaterial> learningMaterial = learningMaterialService.getLearningMaterialByLessonID(lesson.getLessonID());
            for(LearningMaterial lm : learningMaterial){
                learningMaterialService.deleteLearningMaterial(lm.getLearningMaterialID());
            }
        }
        for(LessonDTO lesson : lessons){
            Lesson temp = lessonConverter.convertDtoToEntity(lesson);
            lessonService.deteteLesson(temp);
        }

        List<Enrolled> enrolleds = enrolledService.getAllByCourseID(courseID);
        for(Enrolled enrolled : enrolleds){
            enrolledService.deleteEnrolled(enrolled.getEnrolledID());
        }

        Discount discount = discountService.getDiscountByCourseId(courseID);
        if(discount != null){
            discountService.deleteDiscount(discount.getDiscountID());
        }

        List<RatingCourse> ratingCourse = ratingCourseService.getRatingCourseByCourseID(courseID);
        for(RatingCourse rating : ratingCourse){
            ratingCourseService.deleteRatingCourse(rating.getRatingID());
        }

        List<Quiz> quizzes = quizService.getAllByCourseID(courseID);
        for(Quiz quiz : quizzes){
            List<QuestionDTO> questions = questionService.getAllByQuizID(quiz.getQuizID());
            for(QuestionDTO question : questions){
                List<AnswerDTO> answers = answerService.getAllByQuestionId(question.getQuestionID());
                for(AnswerDTO answer : answers){
                    answerService.deleteAnswer(answerConverter.convertDtoToEntity(answer));
                }
                questionService.deleteQuestion(questionConverter.convertDtoToEntity(question));
            }
            List<Report> reports = reportService.getAllByQuizID(quiz.getQuizID());
            for(Report report : reports){
                List<QuizRevision> quizRevisions = quizRevisionService.getQuizRevisionByReportID(report.getReportID());
                for(QuizRevision quizRevision : quizRevisions){
                    quizRevisionService.deleteQuizRevision(quizRevision.getQuizRevisionID());
                }
                reportService.deleteReport(report.getReportID());
            }
            quizService.deleteQuiz(quiz);
        }

        courseService.deleteCourse(courseID);
        return "redirect:/allCourses";
    }

    @GetMapping("/allUsers")
    public String userList(Model model, HttpServletRequest request, HttpServletResponse response){
        return userListPage(1, "accountID", "asc", model, request, response);
    }

    @GetMapping("/allUsers/page/{pageNo}")
    public String userListPage(@PathVariable (value = "pageNo") int pageNo,
                               @RequestParam("sortField") String sortField,
                               @RequestParam("sortDir") String sortDir,
                               Model model, HttpServletRequest request, HttpServletResponse response){
        CategoryDTO cDto = new CategoryDTO();
        model.addAttribute("categoryDTO", cDto);
        CourseDTO courseDTO = new CourseDTO();
        model.addAttribute("courseDTO", courseDTO);
        model.addAttribute("category", categoryService.getAllCategories());

        List<String> roleList = new ArrayList<>();
        roleList.add("STUDENT");
        roleList.add("ADMIN");
        roleList.add("SUPPORTER");
        model.addAttribute("roleList", roleList);

        int pageSize = 10;
        List<UserInfo> userList = userService.findAllUser();
        model.addAttribute("userList", userList);
        Page<SystemAccount> accountPage = accountService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<SystemAccount> accountList = accountPage.getContent();
        model.addAttribute("accountList", accountList);

        model.addAttribute("totalPages", accountPage.getTotalPages());
        model.addAttribute("totalItems", accountPage.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc")?"desc":"asc");

        model.addAttribute("currentPage", pageNo);

        return "userList";
    }

    @GetMapping("/allUsers/sort/page/{pageNo}")
    public String userListPageSort(@PathVariable (value = "pageNo") int pageNo,
                               @RequestParam("sortField") String sortField,
                               @RequestParam("sortDir") String sortDir,
                               String feild,
                               Model model, HttpServletRequest request, HttpServletResponse response){
        CategoryDTO cDto = new CategoryDTO();
        model.addAttribute("categoryDTO", cDto);
        CourseDTO courseDTO = new CourseDTO();
        model.addAttribute("courseDTO", courseDTO);
        model.addAttribute("category", categoryService.getAllCategories());

        List<String> roleList = new ArrayList<>();
        roleList.add("STUDENT");
        roleList.add("ADMIN");
        roleList.add("SUPPORTER");
        model.addAttribute("roleList", roleList);

        int pageSize = 10;
        List<UserInfo> userList = userService.findAllUser();
        model.addAttribute("userList", userList);
        Page<SystemAccount> accountPage = accountService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<SystemAccount> accountList = accountPage.getContent();
        accountList = filter(feild, accountList);
        model.addAttribute("accountList", accountList);

        // Calculate the new total number of elements
        int totalElement = accountList.size();

        // Calculate the new total number of pages
        int totalPage = (int) Math.ceil((double) totalElement / pageSize);

        model.addAttribute("totalPages", totalPage);
        model.addAttribute("totalItems", totalElement);

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc")?"desc":"asc");

        model.addAttribute("currentPage", pageNo);

        return "userList";
    }

    @PostMapping("/userListFilter")
    public String filter(@RequestParam("role") String feild, Model model,
                         HttpServletRequest request, HttpServletResponse response){
        return userListPageSort(1, "accountID", "asc", feild, model, request, response);
    }
    private List<SystemAccount> filter(String feild, List<SystemAccount> accountList){
        List<SystemAccount> temp = new ArrayList<>();
        if(feild.equals("STUDENT")){
            for(SystemAccount account : accountList){
                if(account.getRoleID().getRoleName().equals("STUDENT")){
                    temp.add(account);
                }
            }
        }
        else if(feild.equals("ADMIN")){
            for(SystemAccount account : accountList){
                if(account.getRoleID().getRoleName().equals("ADMIN")){
                    temp.add(account);
                }
            }
        } else if (feild.equals("SUPPORTER")) {
            for(SystemAccount account : accountList){
                if(account.getRoleID().getRoleName().equals("SUPPORTER")){
                    temp.add(account);
                }
            }
        } else if (feild.equals("ALL")){
            return accountList;
        } else if(feild.equals("Most paid user")){
            UserInfo userInfo = new UserInfo();
            Float max = 0.0f;
            for(int i = 0; i< accountList.size(); i++){
                Float tempMax = 0.0f;
                SystemAccountDTO systemAccountDTO = accountService.findUserByAccountName(accountList.get(i).getAccountName());
                int userID = userService.findUserIDByAccountID(systemAccountDTO.getAccountID());
                UserInfo userInfoTemp = userService.findUser(userID);
                List<Payment> paymentList = paymentService.findPaymentByUserID(userInfoTemp.getUserID());
                for(Payment payment : paymentList){
                    tempMax += payment.getPaymentAmount();
                }
                if(max < tempMax){
                    max = tempMax;
                    userInfo = userInfoTemp;
                }
            }
            for(SystemAccount account : accountList){
                if(account.getAccountID() == userInfo.getAccountID().getAccountID()){
                    temp.add(account);
                }
            }
        } else if (feild.equals("Least paid user")) {
            UserInfo userInfo = new UserInfo();
            Float min = Float.MAX_VALUE;
            for(int i = 0; i< accountList.size(); i++){
                Float tempMin = 0.0f;
                SystemAccountDTO systemAccountDTO = accountService.findUserByAccountName(accountList.get(i).getAccountName());
                int userID = userService.findUserIDByAccountID(systemAccountDTO.getAccountID());
                UserInfo userInfoTemp = userService.findUser(userID);
                List<Payment> paymentList = paymentService.findPaymentByUserID(userInfoTemp.getUserID());
                for(Payment payment : paymentList){
                    tempMin += payment.getPaymentAmount();
                }
                if(min > tempMin){
                    min = tempMin;
                    userInfo = userInfoTemp;
                }
            }
            for(SystemAccount account : accountList){
                if(account.getAccountID() == userInfo.getAccountID().getAccountID()){
                    temp.add(account);
                }
            }
        }
        return temp;
    }
}
