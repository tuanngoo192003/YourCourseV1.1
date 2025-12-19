package com.project.CourseSystem.service;

import com.project.CourseSystem.dto.EmailDetailsDTO;

public interface EmailService {

    String sendSimpleEmail(EmailDetailsDTO emailDetails);

    String sendMailWithAttachment(EmailDetailsDTO emailDetails);
}
