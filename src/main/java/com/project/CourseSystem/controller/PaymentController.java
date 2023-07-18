package com.project.CourseSystem.controller;

import com.project.CourseSystem.converter.CourseConverter;
import com.project.CourseSystem.converter.System_AccountConverter;
import com.project.CourseSystem.dto.CourseDTO;
import com.project.CourseSystem.dto.SystemAccountDTO;
import com.project.CourseSystem.entity.Enrolled;
import com.project.CourseSystem.entity.Payment;
import com.project.CourseSystem.repository.CategoryRepository;
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
public class PaymentController {

    CourseService courseService;

    PaymentService paymentService;

    AccountService accountService;

    System_AccountConverter system_accountConverter;

    UserService userService;

    CourseController courseController;

    CourseConverter courseConverter;

    AuthController authController;

    CategoryRepository categoryService;

    EnrolledService enrolledService;

    LearnController learnController;

    PaymentController(CourseService courseService, PaymentService paymentService,
                      AccountService accountService, UserService userService,
                      CourseController courseController, AuthController authController,
                      CategoryRepository categoryService, CourseConverter courseConverter,
                      System_AccountConverter system_accountConverter,
                      EnrolledService enrolledService, LearnController learnController) {
        this.courseService = courseService;
        this.paymentService = paymentService;
        this.accountService = accountService;
        this.userService = userService;
        this.courseController = courseController;
        this.authController = authController;
        this.categoryService = categoryService;
        this.courseConverter = courseConverter;
        this.system_accountConverter = system_accountConverter;
        this.enrolledService = enrolledService;
        this.learnController = learnController;
    }

    @PostMapping("/payment")
    public String payment(@ModelAttribute("courseDTO") CourseDTO courseDTO, @RequestParam("paymentButton") String paymentButton,
                          @RequestParam("fromPage") String fromPage, Model model, HttpServletRequest request, HttpServletResponse response) {
        CourseDTO courseDTO1 = courseService.getCourseByID(courseDTO.getCourseID());
        if(request.getParameter("discountPrice")!=null){
            float discountPrice = Float.parseFloat(request.getParameter("discountPrice"));
            if(discountPrice!=0){
                courseDTO1.setPrice(discountPrice);
            }
        }
        HttpSession session = request.getSession();
            if(paymentButton.equals("Add To Cart")){
                if(session.getAttribute("cart")==null){
                    List<CourseDTO> list = new ArrayList<>();
                    list.add(courseDTO1);
                    session.setAttribute("cart", list);
                    float sum = list.get(0).getPrice();
                    session.setAttribute("sum", sum);
                    session.setAttribute("cartSize", list.size());
                }
                else{
                    List<CourseDTO> list = (List<CourseDTO>) session.getAttribute("cart");
                    for(int i = 0; i < list.size(); i++){
                        if(list.get(i).getCourseID().equals(courseDTO1.getCourseID())){
                            model.addAttribute("message", "This course is already in your cart!");
                            return courseController.getCourse(model, request, response);
                        }
                    }
                        list.add(courseDTO1);
                        session.setAttribute("cart", list);
                        float sum = 0;
                        for (int i = 0; i < list.size(); i++) {
                            sum += list.get(i).getPrice();
                        }
                        session.setAttribute("sum", sum);
                        session.setAttribute("cartSize", list.size());
                }
            }
            else if(paymentButton.equals("Remove from cart")){
                removeFromCart(request, response, courseDTO1);
            }
            else{ //Buy now
                if(session.getAttribute("CSys")==null){
                    return authController.loginPage(model, request, response);
                }
                else{
                    model.addAttribute("courseDTO", courseDTO1);
                    model.addAttribute("forOne", "forOne");
                    return "payment";
                }
            }
        if(fromPage.equals("list")){
            return courseController.getCourse(model, request, response);
        }
        else{
            return learnController.courseDetailsPage(courseDTO1.getCourseID(), model, request, response);
        }
    }

    @PostMapping("course/page/payment")
    public String paymentForPage(@ModelAttribute("courseDTO") CourseDTO courseDTO, @RequestParam("paymentButton") String paymentButton,
                          @RequestParam("fromPage") String fromPage, Model model, HttpServletRequest request, HttpServletResponse response) {
        return payment(courseDTO, paymentButton, fromPage, model, request, response);
    }

    @GetMapping("/checkout")
    public String checkout(@RequestParam("cartButton") String cartButton, Model model, HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
            if(cartButton.equals("checkout")){
                if(session.getAttribute("CSys")==null){
                    return authController.loginPage(model, request, response);
                }
                else {
                    model.addAttribute("forMany", "forMany");
                    return "payment";
                }
            }
            else{
                session.removeAttribute("cart");
                session.removeAttribute("sum");
                session.removeAttribute("cartSize");
            }
        return courseController.getCourse(model, request, response);
    }

    @PostMapping("/checkout/confirmation/forOne")
    public String checkoutConfirmForOne(@ModelAttribute("courseDTO") CourseDTO courseDTOTemp, Model model, HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        CourseDTO courseDTO = courseService.getCourseByID(courseDTOTemp.getCourseID());
        String accountName = (String)session.getAttribute("CSys");
        SystemAccountDTO systemAccountDTO = accountService.findUserByAccountName(accountName);
        Integer userID = userService.findUserIDByAccountID(systemAccountDTO.getAccountID());
        Payment payment = new Payment();
        payment.setPaymentAmount(courseDTO.getPrice());
        payment.setPaymentDate(new java.sql.Date(System.currentTimeMillis()));
        payment.setUserID(userService.findUser(userID));
        paymentService.addPaymentForOne(payment);

        //enroll user to course
        List<Enrolled> enrolledList = new ArrayList<>();
        Enrolled enrolled = new Enrolled();
        enrolled.setCourseID(courseConverter.convertDtoToEtity(courseDTO));
        enrolled.setEnrolledDate(new java.sql.Date(System.currentTimeMillis()));
        enrolled.setAccountID(system_accountConverter.convertDTOToEntity(accountService.findUserByAccountName(accountName)));
        enrolled.setPaymentID(payment);
        enrolledList.add(enrolled);
        enrolledService.addEnrolled(enrolledList);

        /* set up enrolled */
        List<Enrolled> enrolledListAccount = enrolledService.findByAccountId(systemAccountDTO.getAccountID());
        session.setAttribute("enrolledList", enrolledListAccount);

        /* set up cart */
        removeFromCart(request, response, courseDTO);
        return "redirect:/courseDetails/learn?courseID=" + courseDTO.getCourseID();
    }

    @PostMapping("/checkout/confirmation/forMany")
    public String checkoutConfirmForMany(Model model, HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();

        String accountName = (String) session.getAttribute("CSys");
        SystemAccountDTO systemAccountDTO = accountService.findUserByAccountName(accountName);
        Integer userID = userService.findUserIDByAccountID(systemAccountDTO.getAccountID());
        List<CourseDTO> list = (List<CourseDTO>) session.getAttribute("cart");
        Payment payment = new Payment();
        float price = 0;
        for (CourseDTO courseDTO : list) {
            price += courseDTO.getPrice();
        }
        payment.setPaymentAmount(price);
        payment.setPaymentDate(new java.sql.Date(System.currentTimeMillis()));
        payment.setUserID(userService.findUser(userID));
        paymentService.addPaymentForOne(payment);

        //enroll user to course
        List<Enrolled> enrolledList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Enrolled enrolled = new Enrolled();
            CourseDTO courseDTO = list.get(i);
            enrolled.setCourseID(courseConverter.convertDtoToEtity(courseDTO));
            enrolled.setEnrolledDate(new java.sql.Date(System.currentTimeMillis()));
            enrolled.setAccountID(system_accountConverter.convertDTOToEntity(accountService.findUserByAccountName(accountName)));
            enrolled.setPaymentID(payment);
            enrolledList.add(enrolled);
        }
        enrolledService.addEnrolled(enrolledList);
        session.removeAttribute("cart");
        session.removeAttribute("sum");
        session.removeAttribute("cartSize");

        /* set up enrolled */
        List<Enrolled> enrolledListAccount = enrolledService.findByAccountId(systemAccountDTO.getAccountID());
        session.setAttribute("enrolledList", enrolledListAccount);

        return "redirect:/myCourse";
    }

    public void removeFromCart(HttpServletRequest request, HttpServletResponse response, CourseDTO courseDTO1){
        HttpSession session = request.getSession();
        List<CourseDTO> list = (List<CourseDTO>) session.getAttribute("cart");
        if(list==null){
            session.removeAttribute("cart");
            session.removeAttribute("sum");
        }
        else {
            for(int i = 0; i < list.size(); i++){
                if(list.get(i).getCourseID().equals(courseDTO1.getCourseID())){
                    list.remove(i);
                }
            }
            if(list.isEmpty()){
                session.removeAttribute("cart");
                session.removeAttribute("sum");
                session.removeAttribute("cartSize");
            }
            else{
                session.setAttribute("cart", list);
                if(list!=null){
                    float sum = 0;
                    for (int i = 0; i < list.size(); i++) {
                        sum += list.get(i).getPrice();
                    }
                    session.setAttribute("sum", sum);
                    session.setAttribute("cartSize", list.size());
                }
            }
        }
    }
}
