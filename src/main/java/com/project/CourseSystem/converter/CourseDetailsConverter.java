package com.project.CourseSystem.converter;

import com.project.CourseSystem.dto.CourseDetailsDTO;
import com.project.CourseSystem.entity.CourseDetails;
import org.springframework.stereotype.Component;

@Component
public class CourseDetailsConverter {

    public CourseDetails convertDtoToEntity(CourseDetailsDTO courseDetailsDTO){
        CourseDetails courseDetails = new CourseDetails();
        courseDetails.setCourseID(courseDetailsDTO.getCourseID());
        courseDetails.setCourseDetailsContent(courseDetailsDTO.getCourseDetailsContent());
        courseDetails.setCourseDetailsID(courseDetailsDTO.getCourseDetailsID());
        courseDetails.setCourseDescription(courseDetailsDTO.getCourseDescription());
        courseDetails.setUpdatedDate(courseDetailsDTO.getUpdatedDate());
        courseDetails.setCourseRequirements(courseDetailsDTO.getCourseRequirements());
        courseDetails.setForWho(courseDetailsDTO.getForWho());
        return courseDetails;
    }

    public CourseDetailsDTO convertEntityToDTO(CourseDetails courseDetails){
        CourseDetailsDTO courseDetailsDTO = new CourseDetailsDTO();
        courseDetailsDTO.setCourseID(courseDetails.getCourseID());
        courseDetailsDTO.setCourseDetailsContent(courseDetails.getCourseDetailsContent());
        courseDetailsDTO.setCourseDetailsID(courseDetails.getCourseDetailsID());
        courseDetailsDTO.setCourseDescription(courseDetails.getCourseDescription());
        courseDetailsDTO.setUpdatedDate(courseDetails.getUpdatedDate());
        courseDetailsDTO.setCourseRequirements(courseDetails.getCourseRequirements());
        courseDetailsDTO.setForWho(courseDetails.getForWho());
        return courseDetailsDTO;
    }
}
