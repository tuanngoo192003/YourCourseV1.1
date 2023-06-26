package com.project.CourseSystem.converter;

import com.project.CourseSystem.dto.CourseDTO;
import com.project.CourseSystem.entity.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseConverter {

    public CourseDTO convertEntityToDTO(Course course) {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setCourseID(course.getCourseID());
        courseDTO.setCourseName(course.getCourseName());
        courseDTO.setCourseDes(course.getCourseDes());
        courseDTO.setCreatedDate(course.getCreatedDate());
        courseDTO.setStartDate(course.getStartDate());
        courseDTO.setEndDate(course.getEndDate());
        courseDTO.setCourseImage(course.getCourseImage());
        courseDTO.setPrice(course.getPrice());
        courseDTO.setCategoryID(course.getCategoryID());

        return courseDTO;
    }

    public Course convertDtoToEtity(CourseDTO courseDTO){
        Course course = new Course();
        course.setCourseID(courseDTO.getCourseID());
        course.setCourseName(courseDTO.getCourseName());
        course.setCourseDes(courseDTO.getCourseDes());
        course.setCreatedDate(courseDTO.getCreatedDate());
        course.setStartDate(courseDTO.getStartDate());
        course.setEndDate(courseDTO.getEndDate());
        course.setCourseImage(courseDTO.getCourseImage());
        course.setPrice(courseDTO.getPrice());
        course.setCategoryID(courseDTO.getCategoryID());

        return course;
    }
}
