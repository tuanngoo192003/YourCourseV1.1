package com.project.CourseSystem.service.impl;

import com.project.CourseSystem.dto.EmailDetailsDTO;
import com.project.CourseSystem.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}") private String sender;

    @Override
    public String sendSimpleEmail(EmailDetailsDTO emailDetails) {
        // Try block to check for exceptions
        try{
            //Creating a simple mail message
            SimpleMailMessage message = new SimpleMailMessage();

            //Setting up necessary details
            message.setFrom(sender);
            message.setTo(emailDetails.getRecipient());
            message.setText(emailDetails.getMsgBody());
            message.setSubject(emailDetails.getSubject());

            //sending the mail
            javaMailSender.send(message);
            return "Mail sent successfully...";
        }
        catch (Exception e){
            return "Mail not sent...";
        }
    }

    //send an email with attachment
    @Override
    public String sendMailWithAttachment(EmailDetailsDTO emailDetails) {
        //creating a mime message
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;

        try{
            // Setting multipart as true for attachments to be send
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(sender);
            helper.setTo(emailDetails.getRecipient());
            helper.setText(emailDetails.getMsgBody());
            helper.setSubject(emailDetails.getSubject());
            emailDetails.getSubject();

            //Adding the attachment
            FileSystemResource file = new FileSystemResource(new File(emailDetails.getAttachment()));

            helper.addAttachment(file.getFilename(), file);

            //Sending the mail
            javaMailSender.send(message);
            return "Mail sent successfully...";
        }
        catch (Exception e){
            return "Mail not sent...";
        }
    }
}
