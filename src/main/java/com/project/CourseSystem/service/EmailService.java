package com.project.CourseSystem.service;

// Importing required classes
import com.project.CourseSystem.entity.EmailDetails;

//interface
public interface EmailService {

    // Method
    // To send a simple email
    String sendSimpleEmail(EmailDetails emailDetails);

    // Method
    // To send an email with attachment
    String sendMailWithAttachment(EmailDetails emailDetails);
}
