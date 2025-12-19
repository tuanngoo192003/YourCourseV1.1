package com.project.CourseSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetailsDTO {

    private String recipient;

    private String msgBody;

    private String subject;
    
    private String attachment;
}
