package com.project.CourseSystem.controller;

import com.project.CourseSystem.converter.CourseConverter;
import com.project.CourseSystem.converter.System_AccountConverter;
import com.project.CourseSystem.dto.CourseDTO;
import com.project.CourseSystem.dto.SystemAccountDTO;
import com.project.CourseSystem.entity.*;
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

    DiscountService discountService;

    PaymentDetailsService paymentDetailsService;

    PaymentController(CourseService courseService, PaymentService paymentService,
                      AccountService accountService, UserService userService,
                      CourseController courseController, AuthController authController,
                      CategoryRepository categoryService, CourseConverter courseConverter,
                      System_AccountConverter system_accountConverter,
                      EnrolledService enrolledService, LearnController learnController,
                      DiscountService discountService, PaymentDetailsService paymentDetailsService) {
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
        this.discountService = discountService;
        this.paymentDetailsService = paymentDetailsService;
    }

    @PostMapping("/cart")
    public String cart(@ModelAttribute("courseDTO") CourseDTO courseDTO, @RequestParam("paymentButton") String paymentButton,
                       @RequestParam("fromPage") String fromPage, Model model, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if(courseDTO.getCourseID()==null){
                session.removeAttribute("cart");
                session.removeAttribute("sum");
                session.removeAttribute("cartSize");
            return courseController.getCourse(model, request, response);
        }
        CourseDTO courseDTO1 = courseService.getCourseByID(courseDTO.getCourseID());
        if(request.getParameter("discountPrice")!=null){
            float discountPrice = Float.parseFloat(request.getParameter("discountPrice"));
            if(discountPrice!=0){
                courseDTO1.setPrice(discountPrice);
            }
        }
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
            else if(paymentButton.equals("Remove All")){
                session.removeAttribute("cart");
                session.removeAttribute("sum");
                session.removeAttribute("cartSize");
            }
            else{ //Buy now
                if(session.getAttribute("CSys")==null){
                    return authController.loginPage(model, request, response);
                }
                else{
                    Payment payment = new Payment();
                    model.addAttribute("paymentInfo", payment);
                    model.addAttribute("courseDTO", courseDTO1);
                    model.addAttribute("forOne", "forOne");
                    return "checkout";
                }
            }

        if(fromPage.equals("list")){
            return courseController.getCourse(model, request, response);
        }
        else{
            return learnController.courseDetailsPage(courseDTO1.getCourseID(), model, request, response);
        }
    }

    @PostMapping("course/page/cart")
    public String paymentForPage(@ModelAttribute("courseDTO") CourseDTO courseDTO, @RequestParam("paymentButton") String paymentButton,
                          @RequestParam("fromPage") String fromPage, Model model, HttpServletRequest request, HttpServletResponse response) {
        return cart(courseDTO, paymentButton, fromPage, model, request, response);
    }

    @GetMapping("/checkout")
    public String checkout(@RequestParam("cartButton") String cartButton, Model model, HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        if(session.getAttribute("CSys")==null){
            return authController.loginPage(model, request, response);
        }
        else {
            Payment payment = new Payment();
            model.addAttribute("paymentInfo", payment);
            model.addAttribute("forMany", "forMany");
            return "checkout";
        }
    }

    @PostMapping("/checkout/confirmation")
    public String checkoutConfirm(@ModelAttribute("paymentInfo") Payment payment, @RequestParam("oneOrMany") String oneOrMany,
                                  @RequestParam("paymentStatus") String paymentStatus, Model model, HttpServletRequest request, HttpServletResponse response){
        payment.setStatus(paymentStatus);
        if(oneOrMany.equals("forOne")){
            String courseID =  request.getParameter("courseID");
            CourseDTO courseDTO = courseService.getCourseByID(Integer.parseInt(courseID));
            Discount discount = discountService.getDiscountByCourseId(courseDTO.getCourseID());
            if(discount!=null){
                float discountPrice = courseDTO.getPrice() - (courseDTO.getPrice() * discount.getPercentage() / 100);
                courseDTO.setPrice(discountPrice);
            }
            return checkoutConfirmForOne(payment ,courseDTO ,model, request, response);
        }
        else{
            return checkoutConfirmForMany(payment ,model, request, response);
        }
    }

    public String checkoutConfirmForOne(Payment payment, CourseDTO courseDTOTemp, Model model, HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        CourseDTO courseDTO = courseService.getCourseByID(courseDTOTemp.getCourseID());
        String accountName = (String)session.getAttribute("CSys");
        SystemAccountDTO systemAccountDTO = accountService.findUserByAccountName(accountName);
        Integer userID = userService.findUserIDByAccountID(systemAccountDTO.getAccountID());

        payment.setPaymentAmount(courseDTO.getPrice());
        payment.setPaymentDate(new java.sql.Date(System.currentTimeMillis()));
        payment.setUserID(userService.findUser(userID));
        Payment payment1 = paymentService.addPayment(payment);

        //save bought course to database
        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setCourseID(courseConverter.convertDtoToEtity(courseDTO));
        paymentDetails.setPaymentID(payment1);
        paymentDetailsService.savePaymentDetails(paymentDetails);

        /* set up cart */
        removeFromCart(request, response, courseDTO);
        session.setAttribute("successMsg", "Checkout successfully! Waiting for admin to confirm your payment! You can check your payment status in your profile!");
        return "redirect:/course";
    }

    public String checkoutConfirmForMany(Payment payment, Model model, HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();

        String accountName = (String) session.getAttribute("CSys");
        SystemAccountDTO systemAccountDTO = accountService.findUserByAccountName(accountName);
        Integer userID = userService.findUserIDByAccountID(systemAccountDTO.getAccountID());
        List<CourseDTO> list = (List<CourseDTO>) session.getAttribute("cart");

        float price = 0;
        for (CourseDTO courseDTO : list) {
            price += courseDTO.getPrice();
        }
        payment.setPaymentAmount(price);
        payment.setPaymentDate(new java.sql.Date(System.currentTimeMillis()));
        payment.setUserID(userService.findUser(userID));
        paymentService.addPaymentForOne(payment);

        //save bought course to database
        for (int i = 0; i < list.size(); i++) {
            PaymentDetails paymentDetails = new PaymentDetails();
            paymentDetails.setCourseID(courseConverter.convertDtoToEtity(list.get(i)));
            paymentDetails.setPaymentID(payment);
            paymentDetailsService.savePaymentDetails(paymentDetails);
        }

        /* set up cart */
        session.removeAttribute("cart");
        session.removeAttribute("sum");
        session.removeAttribute("cartSize");

        session.setAttribute("successMsg", "Checkout successfully! Waiting for admin to confirm your payment! You can check your payment status in your profile!");
        return "redirect:/course";
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

    @PostMapping("/confirmPayment")
    public String confirmPayment(@RequestParam("paymentID") String paymentID, @RequestParam("pStatus") String paymentStatus,
                                 Model model, HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        if(paymentStatus.equals("Confirm")){
            Payment payment = paymentService.getPaymentByID(Integer.parseInt(paymentID));
            if(paymentStatus.equals(payment.getStatus())){
                session.setAttribute("errorMsg", "Payment has been confirmed");
                return "redirect:/dashboard?error=Payment has been confirmed";
            }
            if(payment.getStatus().equals("Reject")){
                session.setAttribute("errorMsg", "Payment has been rejected");
                return "redirect:/dashboard?error=Payment has been rejected";
            }
            payment.setStatus(paymentStatus);
            payment.setPaymentDate(new java.sql.Date(System.currentTimeMillis()));
            paymentService.updatePayment(payment);
            List<PaymentDetails> paymentDetailsList = paymentDetailsService.getAllPaymentDetailsByPaymentID(payment.getPaymentID());
            //enroll user to course
            for (int i = 0; i < paymentDetailsList.size(); i++) {
                Enrolled enrolled = new Enrolled();
                CourseDTO courseDTO = courseService.getCourseByID(paymentDetailsList.get(i).getCourseID().getCourseID());
                enrolled.setCourseID(courseConverter.convertDtoToEtity(courseDTO));
                enrolled.setEnrolledDate(new java.sql.Date(System.currentTimeMillis()));
                UserInfo userInfo = userService.findByUserID(payment.getUserID().getUserID());
                SystemAccount systemAccount = accountService.findAccountByID(userInfo.getAccountID().getAccountID());
                enrolled.setAccountID(systemAccount);
                enrolled.setPaymentID(payment);
                enrolledService.saveEnrolled(enrolled);
            }
        }
        else if(paymentStatus.equals("Reject")){
            Payment payment = paymentService.getPaymentByID(Integer.parseInt(paymentID));
            if(paymentStatus.equals(payment.getStatus())){
                session.setAttribute("errorMsg", "Payment has been confirmed");
                return "redirect:/dashboard?error=Payment has been rejected";
            }
            if(payment.getStatus().equals("Confirm")){
                session.setAttribute("errorMsg", "Payment has been confirmed");
                return "redirect:/dashboard?error=You cannot change confirmed payment";
            }
            payment.setStatus(paymentStatus);
            paymentService.updatePayment(payment);
            List<PaymentDetails> paymentDetailsList = paymentDetailsService.getAllPaymentDetailsByPaymentID(payment.getPaymentID());
            //delete payment details
            for (int i = 0; i < paymentDetailsList.size(); i++) {
                paymentDetailsService.deletePaymentDetails(paymentDetailsList.get(i).getPaymentDetailsID());
            }
        }
        else{
            Payment payment = paymentService.getPaymentByID(Integer.parseInt(paymentID));
            if(payment.getStatus().equals("Confirm")){
                session.setAttribute("errorMsg", "You cannot change confirmed payment");
                return "redirect:/dashboard?error=You cannot change confirmed payment";
            } else if (payment.getStatus().equals("Reject")) {
                session.setAttribute("errorMsg", "You cannot change rejected payment");
                return "redirect:/dashboard?error=You cannot change rejected payment";
            }
            else{
                session.setAttribute("errorMsg", "Payment status has already been pending");
                return "redirect:/dashboard?error=Payment status has already been pending";
            }

        }

        session.setAttribute("successMessage", "Payment status has been changed successfully!");
        return "redirect:/dashboard";
    }
}
