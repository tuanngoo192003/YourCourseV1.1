package com.project.CourseSystem.controller;

import com.project.CourseSystem.converter.CategoryConverter;
import com.project.CourseSystem.converter.System_AccountConverter;
import com.project.CourseSystem.dto.*;
import com.project.CourseSystem.entity.*;
import com.project.CourseSystem.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class CourseController {

    final private CourseService courseService;

    final private CategoryService categoryService;

    final private GoogleDriveService driveService;

    final private AuthController authController;

    final private AccountService accountService;

    final private CategoryConverter categoryConverter;

    final private EnrolledService enrolledService;

    final private System_AccountConverter system_accountConverter;

    final private LessonController lessonController;

    private QuizService quizService;

    private LessonService lessonService;

    private QuestionService questionService;

    private AnswerService answerService;

    private LearningMaterialService learningMaterialService;

    private DiscountService discountService;

    private PaymentDetailsService paymentDetailsService;

    private UserService userService;

    private PaymentService paymentService;

    @Autowired
    public CourseController(CourseService courseService,  CategoryService categoryService,
                            AuthController authController, AccountService accountService,
                            CategoryConverter categoryConverter, EnrolledService enrolledService,
                            System_AccountConverter system_accountConverter, GoogleDriveService driveService,
                            LessonController lessonController, QuizService quizService,
                            LessonService lessonService, QuestionService questionService,
                            AnswerService answerService, LearningMaterialService learningMaterialService,
                            DiscountService discountService, PaymentDetailsService paymentDetailsService,
                            UserService userService, PaymentService paymentService){
        this.courseService = courseService;
        this.categoryService = categoryService;
        this.authController = authController;
        this.accountService = accountService;
        this.categoryConverter = categoryConverter;
        this.enrolledService = enrolledService;
        this.system_accountConverter = system_accountConverter;
        this.driveService = driveService;
        this.lessonController = lessonController;
        this.quizService = quizService;
        this.lessonService = lessonService;
        this.questionService = questionService;
        this.answerService = answerService;
        this.learningMaterialService = learningMaterialService;
        this.discountService = discountService;
        this.paymentDetailsService = paymentDetailsService;
        this.userService = userService;
        this.paymentService = paymentService;
    }

    @GetMapping("/course")
    public String getCourse(Model model, HttpServletRequest request, HttpServletResponse response){
        //pagination
        return getPaginated(1, "courseID", "desc", model, request, response);
    }

    @GetMapping("/myCourse")
    public String getMyCourse(Model model, HttpServletRequest request, HttpServletResponse response){
        //pagination
        return getMyCoursePaginated(1, "courseID", "desc", model, request, response);
    }

    @GetMapping("/course/page/{pageNo}")
    public String getPaginated(@PathVariable (value = "pageNo") int pageNo,
                               @RequestParam("sortField") String sortField,
                               @RequestParam("sortDir") String sortDir,
                               Model model, HttpServletRequest request, HttpServletResponse response){
        int pageSize = 18;
        //pagination attribute
        Page<Course> page = courseService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Course> courseList = page.getContent();
        List<Course> courseListTemp = new ArrayList<>();
        HttpSession session = request.getSession();
        if(session.getAttribute("CSys")!=null){
            List<Enrolled> enrolledList = (List<Enrolled>) session.getAttribute("enrolledList");
            String accountName = session.getAttribute("CSys").toString();
            SystemAccount account = system_accountConverter.convertDTOToEntity(accountService.findUserByAccountName(accountName));
            List<Integer> courseIdList = new ArrayList<>();
            for(int i = 0; i < enrolledList.size(); i++){
                if(enrolledList.get(i).getAccountID().getAccountID() == account.getAccountID()){
                    courseIdList.add(enrolledList.get(i).getCourseID().getCourseID());
                }
            }
            for(int i = 0; i < courseList.size(); i++){
                if(!courseIdList.contains(courseList.get(i).getCourseID())){
                    courseListTemp.add(courseList.get(i));
                }
            }
        }
        else {
            courseListTemp.addAll(courseList);
        }

        if(session.getAttribute("CSys")!=null){
            List<PaymentDetails> paymentDetailsList = paymentDetailsService.getAllPaymentDetails();
            String accountName = session.getAttribute("CSys").toString();
            SystemAccount account = system_accountConverter.convertDTOToEntity(accountService.findUserByAccountName(accountName));
            int userID = userService.findUserIDByAccountID(account.getAccountID());
            UserInfo userInfo = userService.findByUserID(userID);
            List<Payment> payments = paymentService.findPaymentByUserID(userID);
            for(int i = 0; i < payments.size(); i++){
                for(int j = 0; j < paymentDetailsList.size(); j++){
                    if(payments.get(i).getPaymentID() == paymentDetailsList.get(j).getPaymentID().getPaymentID()){
                        for(int k = 0; k < courseListTemp.size(); k++){
                            if(courseListTemp.get(k).getCourseID() == paymentDetailsList.get(j).getCourseID().getCourseID()){
                                courseListTemp.remove(k);
                            }
                        }
                    }
                }
            }
        }

        model.addAttribute("currentPage", pageNo);
        int totalPages = page.getTotalPages();
        int temp = pageSize;
        for(int i = 0; i < courseListTemp.size(); i++){
            if(i>temp){
                totalPages++;
                temp+=pageSize;
            }
        }
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", courseListTemp.size());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc")?"desc":"asc");

        model.addAttribute("courseList", courseListTemp);

        //nav bar attribute
        CategoryDTO cDto = new CategoryDTO();
        CourseDTO courseDTO = new CourseDTO();
        model.addAttribute("courseDTO", courseDTO);
        model.addAttribute("categoryDTO", cDto);
        model.addAttribute("category", categoryService.getAllCategories());

        //add discount
        List<Discount> discountList = discountService.getAllDiscounts();
        if(!discountList.isEmpty()) model.addAttribute("listOfDiscount", discountList);

        String msg =(String)session.getAttribute("successMsg");
        if (msg!=null){
            model.addAttribute("successMsg", msg);
            session.removeAttribute("successMsg");
        }

        return "list";
    }

    @GetMapping("/myCourse/page/{pageNo}")
    public String getMyCoursePaginated(@PathVariable (value = "pageNo") int pageNo,
                               @RequestParam("sortField") String sortField,
                               @RequestParam("sortDir") String sortDir,
                               Model model, HttpServletRequest request, HttpServletResponse response){
        int pageSize = 18;
        //pagination attribute
        Page<Course> page = courseService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Course> courseList = page.getContent();
        List<Course> courseListTemp = new ArrayList<>();
        HttpSession session = request.getSession();
        if(session.getAttribute("CSys")!=null){
            List<Enrolled> enrolledList = (List<Enrolled>) session.getAttribute("enrolledList");
            String accountName = session.getAttribute("CSys").toString();
            SystemAccount account = system_accountConverter.convertDTOToEntity(accountService.findUserByAccountName(accountName));
            List<Integer> courseIdList = new ArrayList<>();
            for(int i = 0; i < enrolledList.size(); i++){
                for(int j = 0; j < courseList.size(); j++){
                    if(courseList.get(j).getCourseID() == enrolledList.get(i).getCourseID().getCourseID()){
                        courseListTemp.add(courseList.get(j));
                    }
                }
            }
        }
        else {
            return authController.loginPage(model, request, response);
        }

        model.addAttribute("currentPage", pageNo);
        int totalPages = page.getTotalPages();
        int temp = pageSize;
        for(int i = 0; i < courseListTemp.size(); i++){
            if(i>temp){
                totalPages++;
                temp+=pageSize;
            }
        }
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", courseListTemp.size());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc")?"desc":"asc");

        model.addAttribute("courseList", courseListTemp);

        //nav bar attribute
        CategoryDTO cDto = new CategoryDTO();
        CourseDTO courseDTO = new CourseDTO();
        model.addAttribute("courseDTO", courseDTO);
        model.addAttribute("categoryDTO", cDto);
        model.addAttribute("category", categoryService.getAllCategories());
        return "myCourse";
    }

    @GetMapping("/myCourse/sort/page/{pageNo}")
    public String getMyCoursePaginatedByAttribute(@PathVariable (value = "pageNo") int pageNo,
                                          @RequestParam("sortField") String sortField,
                                          @RequestParam("sortDir") String sortDir,
                                          String attribute, String value,
                                          Model model, HttpServletRequest request, HttpServletResponse response){
        int pageSize = 18;
        //pagination attribute
        Page<Course> page = courseService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Course> courseList = page.getContent();
        List<Course> courseListTemp = new ArrayList<>();
        HttpSession session = request.getSession();
        if(session.getAttribute("CSys")!=null){
            List<Enrolled> enrolledList = (List<Enrolled>) session.getAttribute("enrolledList");
            for(int i = 0; i < enrolledList.size(); i++){
                for(int j = 0; j < courseList.size(); j++){
                    if(courseList.get(j).getCourseID() == enrolledList.get(i).getCourseID().getCourseID()){
                        courseListTemp.add(courseList.get(j));
                    }
                }
            }
        }
        else {
            courseListTemp.addAll(courseList);
        }
        List<Course> courseListTemp1 = new ArrayList<>();
        if(attribute.equals("price")){
            for(int i = 0; i < courseListTemp.size(); i++){
                if(courseListTemp.get(i).getPrice() == 0){
                    courseListTemp1.add(courseListTemp.get(i));
                }
            }
        }
        else{
            for(int i = 0; i < courseListTemp.size(); i++){
                if(courseListTemp.get(i).getCategoryID().getCategoryID() == Integer.parseInt(value)){
                    courseListTemp1.add(courseListTemp.get(i));
                }
            }
        }


        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", courseListTemp1.size());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc")?"desc":"asc");

        model.addAttribute("courseList", courseListTemp1);

        //nav bar attribute
        CategoryDTO cDto = new CategoryDTO();
        CourseDTO courseDTO = new CourseDTO();
        model.addAttribute("courseDTO", courseDTO);
        model.addAttribute("categoryDTO", cDto);
        model.addAttribute("category", categoryService.getAllCategories());
        return "myCourse";
    }

    @GetMapping("/course/sort/page/{pageNo}")
    public String getPaginatedByAttribute(@PathVariable (value = "pageNo") int pageNo,
                               @RequestParam("sortField") String sortField,
                               @RequestParam("sortDir") String sortDir,
                               String attribute, String value,
                               Model model, HttpServletRequest request, HttpServletResponse response){
        int pageSize = 18;
        //pagination attribute
        Page<Course> page = courseService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Course> courseList = page.getContent();
        List<Course> courseListTemp = new ArrayList<>();
        HttpSession session = request.getSession();
        if(session.getAttribute("CSys")!=null){
            List<Enrolled> enrolledList = (List<Enrolled>) session.getAttribute("enrolledList");
            String accountName = session.getAttribute("CSys").toString();
            SystemAccount account = system_accountConverter.convertDTOToEntity(accountService.findUserByAccountName(accountName));
            List<Integer> courseIdList = new ArrayList<>();
            for(int i = 0; i < enrolledList.size(); i++){
                if(enrolledList.get(i).getAccountID().getAccountID() == account.getAccountID()){
                    courseIdList.add(enrolledList.get(i).getCourseID().getCourseID());
                }
            }
            for(int i = 0; i < courseList.size(); i++){
                if(!courseIdList.contains(courseList.get(i).getCourseID())){
                    courseListTemp.add(courseList.get(i));
                }
            }
        }
        else {
            courseListTemp.addAll(courseList);
        }
        List<Course> courseListTemp1 = new ArrayList<>();
        System.out.println(attribute);
        if(attribute.equals("price")){
            for(int i = 0; i < courseListTemp.size(); i++){
                if(courseListTemp.get(i).getPrice() == 0){
                    courseListTemp1.add(courseListTemp.get(i));
                }
            }
        }
        else{
            for(int i = 0; i < courseListTemp.size(); i++){
                if(courseListTemp.get(i).getCategoryID().getCategoryID() == Integer.parseInt(value)){
                    courseListTemp1.add(courseListTemp.get(i));
                }
            }
        }


        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", courseListTemp1.size());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc")?"desc":"asc");

        model.addAttribute("courseList", courseListTemp1);

        //nav bar attribute
        CategoryDTO cDto = new CategoryDTO();
        CourseDTO courseDTO = new CourseDTO();
        model.addAttribute("courseDTO", courseDTO);
        model.addAttribute("categoryDTO", cDto);
        model.addAttribute("category", categoryService.getAllCategories());

        //add discount
        List<Discount> discountList = discountService.getAllDiscounts();
        if(!discountList.isEmpty()) model.addAttribute("listOfDiscount", discountList);
        return "list";
    }

    @PostMapping("/myCourseFilter")
    public String myCourseFilter(@ModelAttribute("category") CategoryDTO categoryDTO, Model model,
                         HttpServletRequest request, HttpServletResponse response) {
        if(categoryDTO.getCategoryName().equals("All")){
            return getMyCourse(model, request, response);
        }
        else{
            CourseDTO courseDTO = new CourseDTO();
            model.addAttribute("courseDTO", courseDTO);
            CategoryDTO temp = new CategoryDTO();
            model.addAttribute("categoryDTO", temp);
            model.addAttribute("category", categoryService.getAllCategories());
            temp = categoryService.getCategoryByName(categoryDTO.getCategoryName());
            int categoryID = temp.getCategoryID();
            model.addAttribute("courseList", courseService.getAllCoursesByCategoryID(categoryID));
            return getMyCoursePaginatedByAttribute(1, "courseID", "desc", "categoryID", String.valueOf(categoryID), model, request, response);

        }
    }

    @PostMapping("/filter")
    public String filter(@ModelAttribute("category") CategoryDTO categoryDTO, Model model,
                         HttpServletRequest request, HttpServletResponse response) {
        if(categoryDTO.getCategoryName().equals("All")){
            return getPaginated(1, "courseID", "asc", model, request, response);
        }
        else{
            CourseDTO courseDTO = new CourseDTO();
            model.addAttribute("courseDTO", courseDTO);
            CategoryDTO temp = new CategoryDTO();
            model.addAttribute("categoryDTO", temp);
            model.addAttribute("category", categoryService.getAllCategories());
            temp = categoryService.getCategoryByName(categoryDTO.getCategoryName());
            int categoryID = temp.getCategoryID();
            model.addAttribute("courseList", courseService.getAllCoursesByCategoryID(categoryID));
            return getPaginatedByAttribute(1, "courseID", "desc", "categoryID", String.valueOf(categoryID), model, request, response);
        }
    }

    @GetMapping("/getCourseByCategoryID")
    public String getCourseByCategoryID(@RequestParam Integer categoryID, Model model,
                         HttpServletRequest request, HttpServletResponse response){
        CourseDTO courseDTO = new CourseDTO();
        model.addAttribute("courseDTO", courseDTO);
        CategoryDTO temp = new CategoryDTO();
        model.addAttribute("categoryDTO", temp);
        model.addAttribute("category", categoryService.getAllCategories());
        model.addAttribute("courseList", courseService.getAllCoursesByCategoryID(categoryID));
        return getPaginatedByAttribute(1, "courseID", "desc", "categoryID", String.valueOf(categoryID), model, request, response);
    }

    @PostMapping("/sort")
    public String sort(@ModelAttribute("courseDTO") CourseDTO courseDTO, @RequestParam("option") String option,
                       Model model, HttpServletRequest request, HttpServletResponse response){
        if(option.equals("Newest")){
            return getPaginated(1, "startDate", "desc", model, request, response);
        }
        else if(option.equals("Oldest")){
            return getPaginated(1, "startDate", "asc", model, request, response);
        }
        else if(option.equals("About to end")){
            return getPaginated(1, "endDate", "asc", model, request, response);
        }
        else if(option.equals("High to low")){
            return getPaginated(1, "price", "desc", model, request, response);
        }
        else if(option.equals("Low to high")){
            return getPaginated(1, "price", "asc", model, request, response);
        }
        else if(option.equals("Free")){
            String sortField = "price";
            String sortDir = "desc";
            return getPaginatedByAttribute(1, sortField, sortDir, "price", "0", model, request, response);
        }
        else{
            return getPaginated(1, "courseID", "asc", model, request, response);
        }
    }

    @PostMapping("/myCourseSort")
    public String myCourseSort(@ModelAttribute("courseDTO") CourseDTO courseDTO, @RequestParam("option") String option,
                       Model model, HttpServletRequest request, HttpServletResponse response){
        if(option.equals("Newest")){
            return getMyCoursePaginated(1, "startDate", "desc", model, request, response);
        }
        else if(option.equals("Oldest")){
            return getMyCoursePaginated(1, "startDate", "asc", model, request, response);
        }
        else if(option.equals("About to end")){
            return getMyCoursePaginated(1, "endDate", "asc", model, request, response);
        }
        else if(option.equals("High to low")){
            return getMyCoursePaginated(1, "price", "desc", model, request, response);
        }
        else if(option.equals("Low to high")){
            return getMyCoursePaginated(1, "price", "asc", model, request, response);
        }
        else if(option.equals("Free")){
            String sortField = "price";
            String sortDir = "desc";
            return getMyCoursePaginatedByAttribute(1, sortField, sortDir, "price", "0", model, request, response);
        }
        else{
            return getMyCoursePaginated(1, "courseID", "asc", model, request, response);
        }
    }

    @GetMapping("/addCourse")
    public String addCourse(Model model, HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        if(session.getAttribute("CSys") == null){
            return authController.loginPage(model, request, response);
        }
        else{
            String accountName = (String) session.getAttribute("CSys");
            SystemAccountDTO systemAccountDTO = accountService.findUserByAccountName(accountName);
            if(systemAccountDTO.getRoleID().getRoleID()!=2){
                return authController.loginPage(model, request, response);
            }
            else{
                Course course = new Course();
                CourseDetails courseDetails = new CourseDetails();
                AddCourseForm addCourseForm = new AddCourseForm();
                if(session.getAttribute("newCourse")!=null && session.getAttribute("newCourseDetails")!=null){
                    addCourseForm.setCourseName(course.getCourseName());
                    addCourseForm.setCourseDes(course.getCourseDes());
                    addCourseForm.setPrice(course.getPrice());
                    addCourseForm.setCourseImage(course.getCourseImage());
                    addCourseForm.setCategory(course.getCategoryID().getCategoryName());
                    addCourseForm.setStartDate(course.getStartDate());
                    addCourseForm.setEndDate(course.getEndDate());
                    addCourseForm.setCourseDescription(courseDetails.getCourseDescription());
                    addCourseForm.setCourseDetailsContent(courseDetails.getCourseDetailsContent());
                    addCourseForm.setCourseRequirements(courseDetails.getCourseRequirements());
                    addCourseForm.setForWho(courseDetails.getForWho());
                }
                model.addAttribute("addCourseForm", addCourseForm);

                //nav bar attribute
                CategoryDTO cDto = new CategoryDTO();
                CourseDTO courseDTO = new CourseDTO();
                model.addAttribute("courseDTO", courseDTO);
                model.addAttribute("categoryDTO", cDto);
                model.addAttribute("category", categoryService.getAllCategories());
            }
        }
        return "addCourse";
    }

    @PostMapping("/inputLesson")
    public String addLesson(@RequestParam("file") MultipartFile file,
                            @ModelAttribute("addCourseForm") AddCourseForm addCourseForm, Model model,
                            HttpServletRequest request, HttpServletResponse response){
        Course course = new Course();
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
        HttpSession session = request.getSession();
        session.setAttribute("newCourse", course);
        //courseService.saveCourse(course);

        //Save courseDetails
        CourseDetails courseDetails = new CourseDetails();
        courseDetails.setCourseID(course);
        courseDetails.setCourseDescription(addCourseForm.getCourseDescription());
        courseDetails.setCourseRequirements(addCourseForm.getCourseRequirements());
        courseDetails.setCourseDetailsContent(addCourseForm.getCourseDetailsContent());
        courseDetails.setForWho(addCourseForm.getForWho());
        //courseService.saveCourseDetails(courseDetails);
        session.setAttribute("newCourseDetails", courseDetails);

        //set model to input lesson and material
        QuizListForm quizListForm = new QuizListForm();
        model.addAttribute("quizListForm", quizListForm);
        AddLessonFormDTO addLessonForm = new AddLessonFormDTO();
        model.addAttribute("addLessonForm", addLessonForm);

        return "addLesson";
    }

    @PostMapping("/createNewCourse")
    public String createNewCourse(@RequestParam("questionDTO-content")String questionContents,
                                  @RequestParam("answerDTO-content") String answerContents,
                                  @ModelAttribute("quizListForm") QuizListForm quizListForm,
                                  @RequestParam("submit") String submit,
                                  Model model, HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        /* Add new lesson
        *  Save Course to session
        *  Save CourseDetails to session
        *  Save QuizListFormList to session
        *  Save addLessonFormDTOList to session
        *  */
        if(submit.equals("Add new lesson")){
            saveCurrentQuestionsAndAnswers(questionContents, answerContents, quizListForm, model, request, response);

            return lessonController.addLessonForm(model, request, response);
        }
        else{
            //Save course
            Course course = (Course) session.getAttribute("newCourse");
            CourseDetails courseDetails = (CourseDetails) session.getAttribute("newCourseDetails");
            courseService.saveCourse(course);
            courseDetails.setCourseID(course);
            courseService.saveCourseDetails(courseDetails);
            //Add quiz to questionList and answerList
            List<QuizListForm> quizListFormList = (List<QuizListForm>) session.getAttribute("quizListFormList");
            if(quizListFormList == null){
                //If the new course has one lesson only
                Quiz quiz = saveQuizToDatabase(questionContents, answerContents, quizListForm, course, model, request, response);

                //Save lesson
                List<AddLessonFormDTO> addLessonFormDTOList = (List<AddLessonFormDTO>) session.getAttribute("addLessonFormDTOList");
                Lesson lesson = new Lesson();
                lesson.setCourseID(course);
                lesson.setLessonName(addLessonFormDTOList.get(0).getLessonName());
                lesson.setLessonDes(addLessonFormDTOList.get(0).getLessonDes());
                lesson.setQuizID(quiz);
                lesson = lessonService.saveLesson(lesson);

                //Save lesson content/Learning material
                LearningMaterial learningMaterial = new LearningMaterial();
                learningMaterial.setLessonID(lesson);
                learningMaterial.setLearningMaterialName(addLessonFormDTOList.get(0).getLearningMaterialName());
                learningMaterial.setLearningMaterialDes(addLessonFormDTOList.get(0).getLearningMaterialDes());
                learningMaterial.setLearningMaterialLink(addLessonFormDTOList.get(0).getLearningMaterialLink());
                learningMaterialService.saveLearningMaterial(learningMaterial);

            }
            else{
                saveCurrentQuestionsAndAnswers(questionContents, answerContents, quizListForm, model, request, response);
                List<AddLessonFormDTO> addLessonFormDTOList = (List<AddLessonFormDTO>) session.getAttribute("addLessonFormDTOList");

                for(int start = 0; start < addLessonFormDTOList.size(); start++){
                    QuizListForm quizListFormTemp = quizListFormList.get(start);
                    String questionContentsTemp = quizListFormTemp.getQuestionContents();
                    String answerContentsTemp = quizListFormTemp.getAnswerContents();

                    Quiz quiz = saveQuizToDatabase(questionContentsTemp, answerContentsTemp, quizListFormTemp, course, model, request, response);

                    //Save lesson
                    Lesson lesson = new Lesson();
                    lesson.setCourseID(course);
                    lesson.setLessonName(addLessonFormDTOList.get(start).getLessonName());
                    lesson.setLessonDes(addLessonFormDTOList.get(start).getLessonDes());
                    lesson.setQuizID(quiz);
                    lesson = lessonService.saveLesson(lesson);

                    //Save lesson content/Learning material
                    LearningMaterial learningMaterial = new LearningMaterial();
                    learningMaterial.setLessonID(lesson);
                    learningMaterial.setLearningMaterialName(addLessonFormDTOList.get(start).getLearningMaterialName());
                    learningMaterial.setLearningMaterialDes(addLessonFormDTOList.get(start).getLearningMaterialDes());
                    learningMaterial.setLearningMaterialLink(addLessonFormDTOList.get(start).getLearningMaterialLink());
                    learningMaterialService.saveLearningMaterial(learningMaterial);
                }
            }
            session.removeAttribute("newCourse");
            session.removeAttribute("newCourseDetails");
            session.removeAttribute("quizListFormList");
            session.removeAttribute("addLessonFormDTOList");

            return getCourse(model, request, response);
        }
    }

    private void saveCurrentQuestionsAndAnswers(String questionContents, String answerContents, QuizListForm quizListForm,
                                                Model model, HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        List<QuizListForm> quizListFormList = (List<QuizListForm>) session.getAttribute("quizListFormList");
        if(quizListFormList == null){
            quizListFormList = new ArrayList<>();
        }
        quizListForm.setQuestionContents(questionContents);
        quizListForm.setAnswerContents(answerContents);
        quizListFormList.add(quizListForm);
        session.setAttribute("quizListFormList", quizListFormList);
    }

    private Quiz saveQuizToDatabase(String questionContents, String answerContents, QuizListForm quizListForm,
                                    Course course, Model model, HttpServletRequest request, HttpServletResponse response){
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
                if(tempIndex == 0){
                    String isCorrect = request.getParameter("isCorrect");
                    if(answerOrdinal.equals(isCorrect)){
                        answerDTO.setIsCorrect("right");
                    }
                    else{
                        answerDTO.setIsCorrect("wrong");
                    }
                }else{
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
                }
                answerList.add(answerDTO);
                isCorrectIndex++;
                tempIndex = i+1;
            }
        }
        //add Quiz to database
        Quiz quiz = new Quiz();
        quiz.setCourseID(course);
        quiz.setQuizName(quizListForm.getQuizDTO().getQuizName());
        quiz.setQuizDes(quizListForm.getQuizDTO().getQuizDes());
        Time time = new Time(0,0,0);
        quiz.setQuizPeriod(time);
        quiz = quizService.saveQuiz(quiz);
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
        return quiz;
    }
}
