package com.project.CourseSystem.controller;

import com.project.CourseSystem.converter.CourseConverter;
import com.project.CourseSystem.converter.DiscountConverter;
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
import org.springframework.web.bind.annotation.ModelAttribute;
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

    private ReportService reportService;

    private UserService userService;

    private CourseConverter courseConverter;

    private RatingCourseService ratingCourseService;

    private DiscountService discountService;

    private DiscountConverter discountConverter;

    private AuthController authController;

    private PaymentDetailsService paymentDetailsService;

    private PaymentService paymentService;

    private LearnController(LessonService lessonService, QuizService quizService, CourseService courseService
    , CategoryService categoryService, EnrolledService enrolledService, EnrolledConverter enrolledConverter,
                            AccountService accountService, LearningMaterialService learningMaterialService,
                            ReportService reportService, UserService userService, CourseConverter courseConverter,
                            RatingCourseService ratingCourseService, DiscountService discountService,
                            DiscountConverter discountConverter, AuthController authController,
                            PaymentDetailsService paymentDetailsService, PaymentService paymentService) {
        this.lessonService = lessonService;
        this.quizService = quizService;
        this.courseService = courseService;
        this.categoryService = categoryService;
        this.enrolledService = enrolledService;
        this.enrolledConverter = enrolledConverter;
        this.accountService = accountService;
        this.learningMaterialService = learningMaterialService;
        this.reportService = reportService;
        this.userService = userService;
        this.courseConverter = courseConverter;
        this.ratingCourseService = ratingCourseService;
        this.discountService = discountService;
        this.discountConverter = discountConverter;
        this.authController = authController;
        this.paymentDetailsService = paymentDetailsService;
        this.paymentService = paymentService;
    }

    @GetMapping("/courseDetails")
    public String courseDetailsPage(@RequestParam Integer courseID, Model model,
                                    HttpServletRequest request, HttpServletResponse response) {
        if(courseID == null) {
            return "redirect:/course";
        }
        int id = courseID.intValue();
        CourseDTO course = courseService.getCourseByID(id);

            /* check if user is enrolled */
        HttpSession session = request.getSession();
        if(session.getAttribute("CSys")!=null){
            String accountName = (String) session.getAttribute("CSys");
            SystemAccountDTO accountDTO = accountService.findUserByAccountName(accountName);
            Enrolled enrolled = enrolledService.findByAccountIdAndCourseID(accountDTO.getAccountID(), courseID);
            if(enrolled != null){
                return "redirect:/courseDetails/learn?courseID="+courseID;
            }
            else{
                setCourseDetailsContentModel(course, model, request, response);
                /* Get discount */
                Discount discount = discountService.getDiscountByCourseId(courseID);
                if(discount != null){
                    //calculate discount price
                    float discountPercent = (100 - (float)discount.getPercentage())/100;
                    float temp = course.getPrice() * discountPercent;
                    int discountPrice = (int) temp;
                    DiscountDTO discountDTO = discountConverter.convertToDTO(discount);
                    model.addAttribute("discountDTO", discountDTO);
                    model.addAttribute("discountPrice", discountPrice);
                }
                /* get paid */
                int userID = userService.findUserIDByAccountID(accountDTO.getAccountID());
                UserInfo userInfo = userService.findByUserID(userID);
                List<Payment> paymentList = paymentService.findPaymentByUserID(userID);
                List<PaymentDetails> paymentDetailsList = paymentDetailsService.getAllByCourseID(courseID);
                for(PaymentDetails paymentDetails : paymentDetailsList){
                    for(Payment payment : paymentList){
                        if(paymentDetails.getPaymentID().getPaymentID() == payment.getPaymentID()){
                            if(paymentDetails.getCourseID().getCourseID() == courseID){
                                model.addAttribute("paid", "paid");
                            }
                        }
                    }
                }
                return "courseDetails";
            }
        }
        else{
            setCourseDetailsContentModel(course, model, request, response);

                /* Get discount */
                Discount discount = discountService.getDiscountByCourseId(courseID);
                if(discount != null){
                    //calculate discount price
                    float discountPercent = (100 - (float)discount.getPercentage())/100;
                    float temp = course.getPrice() * discountPercent;
                    int discountPrice = (int) temp;
                    DiscountDTO discountDTO = discountConverter.convertToDTO(discount);
                    model.addAttribute("discountDTO", discountDTO);
                    model.addAttribute("discountPrice", discountPrice);
                }
                return "courseDetails";
        }
    }

    public void setCourseDetailsContentModel(CourseDTO course, Model model,
                                             HttpServletRequest request, HttpServletResponse response){
        /* get lesson list */
        List<LessonDTO> lessonList = lessonService.getAllByCourseID(course.getCourseID());
        List<QuizDTO> quizList = new ArrayList<>();
        for (LessonDTO lesson : lessonList) {
            quizList.add(quizService.getAllByLessonID(lesson.getLessonID()));
        }

        /* get learning status */
        List<Integer> passedQuiz = getPassedQuiz(request, response);
        List<PassedStatusCheck> learningStatus = new ArrayList<>();
        int learningStatusCount = 0;
        int finishedLessonCount = 0;

        for(int i = 0; i < lessonList.size(); i++){
            PassedStatusCheck passedStatusCheck = new PassedStatusCheck();
            int status = 0;
            for(int j = 0; j < passedQuiz.size(); j++){
                if(lessonList.get(i).getQuizID().getQuizID() == passedQuiz.get(j)){
                    passedStatusCheck.setCheckStatus(1);
                    passedStatusCheck.setStatusContent("Lesson-"+lessonList.get(i).getLessonName()+": Completed");
                    passedStatusCheck.setLessonID(lessonList.get(i).getLessonID());
                    learningStatus.add(passedStatusCheck);
                    status = 1;
                    learningStatusCount++;
                    finishedLessonCount++;
                    break;
                }
            }
            if(status == 0){
                passedStatusCheck.setCheckStatus(0);
                passedStatusCheck.setStatusContent("Lesson-"+lessonList.get(i).getLessonName()+": Not completed");
                passedStatusCheck.setLessonID(lessonList.get(i).getLessonID());
                learningStatus.add(passedStatusCheck);
            }
        }
        String totalStatus = "";
        if(learningStatusCount == lessonList.size()){
            totalStatus="Status: Completed";
        }
        else{
            totalStatus="Status: Not completed";
        }
        model.addAttribute("totalStatus", totalStatus);
        model.addAttribute("learningStatus", learningStatus);
        model.addAttribute("finishedLessonCount", finishedLessonCount);

        /* get course details */
        CourseDetailsDTO courseDetailsDTO =courseService.getCourseDetailsByID(course.getCourseID());

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
        RatingCourseDTO ratingCourseDTO = new RatingCourseDTO();
        model.addAttribute("ratingCourseDTO", ratingCourseDTO);
    }

    @GetMapping("/courseDetails/learn")
    public String learnPage(@RequestParam("courseID") int courseID, Model model, HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        if(session.getAttribute("CSys")==null){
            return "redirect:/courseDetails?courseID="+courseID;
        }
        else{
            String accountName = (String) session.getAttribute("CSys");
            SystemAccountDTO accountDTO = accountService.findUserByAccountName(accountName);
            Enrolled enrolled = enrolledService.findByAccountIdAndCourseID(accountDTO.getAccountID(), courseID);
            if(enrolled != null){
                CourseDTO course = courseService.getCourseByID(courseID);
                setCourseDetailsContentModel(course, model, request, response);
                EnrolledDTO enrolledDTO = enrolledConverter.convertEntityToDTO(enrolled);
                model.addAttribute("enrolledDTO", enrolledDTO);
                /* get rating */
                UserInfo userInfo = userService.findUser(accountDTO.getAccountID());
                List<Report> reportList = reportService.getAllReportByUserID(userInfo.getUserID());
                RatingCourse ratingCourse = ratingCourseService.getRatingCourseByCourseIdAndUserId(courseID, userInfo.getUserID());
                if(ratingCourse != null){
                    model.addAttribute("rating", ratingCourse.getRating());
                    model.addAttribute("comment", ratingCourse.getComment());
                }
                return "learn";
            }
            else {
                return "redirect:/courseDetails?courseID="+courseID;
            }
        }
    }

    @PostMapping("/learn/feedback")
    public String submitRating(@RequestParam("rating") String rating, @RequestParam("currentCourseID") int courseID,
                               @ModelAttribute("ratingCourseDTO") RatingCourseDTO ratingCourseDTO,
                               Model model, HttpServletRequest request, HttpServletResponse response){

        RatingCourse ratingCourse = new RatingCourse();
        ratingCourse.setRating(Integer.parseInt(rating));
        ratingCourse.setCourseID(courseConverter.convertDtoToEtity(courseService.getCourseByID(courseID)));
        HttpSession session = request.getSession();
        String accountName = (String) session.getAttribute("CSys");
        SystemAccountDTO accountDTO = accountService.findUserByAccountName(accountName);
        UserInfo userInfo = userService.findUser(accountDTO.getAccountID());
        ratingCourse.setUserID(userInfo);
        ratingCourse.setComment(ratingCourseDTO.getComment());
        ratingCourseService.addRatingCourse(ratingCourse);
        return courseDetailsPage(courseID, model, request, response);
    }

    private List<Integer> getPassedQuiz(HttpServletRequest request, HttpServletResponse response){
        List<Integer> passedQuiz = new ArrayList<>();
        HttpSession session = request.getSession();
        if(session.getAttribute("CSys")!=null){
            String accountName = (String) session.getAttribute("CSys");
            SystemAccountDTO accountDTO = accountService.findUserByAccountName(accountName);
            UserInfo userInfo = userService.findUser(accountDTO.getAccountID());
            List<Report> reportList = reportService.getAllReportByUserID(userInfo.getUserID());
            for(int i = 0; i < reportList.size(); i++){
                if(reportList.get(i).getMark() >= 50){
                    passedQuiz.add(reportList.get(i).getQuizID().getQuizID());
                }
            }
            return passedQuiz;
        }
        else {
            return passedQuiz;
        }
    }
}
