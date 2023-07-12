package com.project.CourseSystem.service;

import com.project.CourseSystem.dto.CourseDTO;
import com.project.CourseSystem.dto.CourseDetailsDTO;
import com.project.CourseSystem.entity.Course;
import com.project.CourseSystem.entity.CourseDetails;
import com.project.CourseSystem.entity.Report;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CourseService {
    List<CourseDTO> getAllCourses();

    CourseDTO getCourseByID(int id);

    List<CourseDTO> getAllCoursesByCategoryID(int categoryID);

    List<CourseDTO> getAllCoursesByCourseNameContaining(String courseName);

    Page<Course> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

    Page<Course> findPaginatedByAttribute(int pageNo, int pageSize, String sortField, String sortDirection, String attribute, String value);

    CourseDetailsDTO getCourseDetailsByID(int id);

    void saveCourse(Course course);

    void saveCourseDetails(CourseDetails courseDetails);

    void updateCourse(Course course);

    void addReport(Report report);

    Course findFirstCourseByCategoryID(Integer categoryID);
}
