package com.project.CourseSystem.service.impl;

import com.project.CourseSystem.converter.CourseConverter;
import com.project.CourseSystem.converter.CourseDetailsConverter;
import com.project.CourseSystem.dto.CourseDTO;
import com.project.CourseSystem.dto.CourseDetailsDTO;
import com.project.CourseSystem.entity.Course;
import com.project.CourseSystem.entity.CourseDetails;
import com.project.CourseSystem.entity.Report;
import com.project.CourseSystem.repository.CourseDetailsRepository;
import com.project.CourseSystem.repository.CourseRepository;
import com.project.CourseSystem.repository.ReportRepository;
import com.project.CourseSystem.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    final private CourseRepository courseRepository;

    final private CourseConverter courseConverter;

    final private CourseDetailsRepository courseDetailsRepository;

    final private CourseDetailsConverter courseDetailsConverter;

    final private ReportRepository reportRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, CourseConverter courseConverter,
            CourseDetailsRepository courseDetailsRepository, CourseDetailsConverter courseDetailsConverter,
            ReportRepository reportRepository) {
        this.courseRepository = courseRepository;
        this.courseConverter = courseConverter;
        this.courseDetailsRepository = courseDetailsRepository;
        this.courseDetailsConverter = courseDetailsConverter;
        this.reportRepository = reportRepository;
    }

    public List<CourseDTO> getAllCourses() {
        List<CourseDTO> courseList = new ArrayList<>();
        courseRepository.findAll().forEach(course -> courseList.add(courseConverter.convertEntityToDTO(course)));
        return courseList;
    }

    @Override
    public CourseDTO getCourseByID(int id) {
        CourseDTO courseDTO = courseConverter.convertEntityToDTO(courseRepository.findById(id).get());
        return courseDTO;
    }

    @Override
    public List<CourseDTO> getAllCoursesByCategoryID(int categoryID) {
        List<CourseDTO> courseList = new ArrayList<>();
        courseRepository.getAllByCategoryID(categoryID)
                .forEach(course -> courseList.add(courseConverter.convertEntityToDTO(course)));
        return courseList;
    }

    @Override
    public List<CourseDTO> getAllCoursesByCourseNameContaining(String courseName) {
        List<CourseDTO> courseList = new ArrayList<>();
        courseRepository.getAllByCourseNameContaining(courseName)
                .forEach(course -> courseList.add(courseConverter.convertEntityToDTO(course)));
        return courseList;
    }

    @Override
    public Page<Course> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();
        return courseRepository.findAll(PageRequest.of(pageNo - 1, pageSize, sort));
    }

    @Override
    public Page<Course> findPaginatedByAttribute(int pageNo, int pageSize, String sortField, String sortDirection,
            String attribute, String value) {
        return courseRepository.findAllByAttribute(attribute, value,
                PageRequest.of(pageNo - 1, pageSize, Sort.by(sortField).ascending()));
    }

    @Override
    public CourseDetailsDTO getCourseDetailsByID(int id) {
        CourseDetailsDTO courseDetailsDTO = courseDetailsRepository.findCourseDetailsByCourseID(id);
        return courseDetailsDTO;
    }

    @Override
    public void saveCourse(Course course) {
        courseRepository.save(course);
    }

    @Override
    public void saveCourseDetails(CourseDetails courseDetails) {
        courseDetailsRepository.save(courseDetails);
    }

    @Override
    public void updateCourse(Course course) {
        courseRepository.save(course);
    }

    @Override
    public void addReport(Report report) {
        reportRepository.save(report);
    }

    @Override
    public Course findFirstCourseByCategoryID(Integer categoryID) {
        return courseRepository.findFirstCourseByCategory(categoryID);
    }

    @Override
    public void deleteCourseDetails(Integer courseID) {
        courseDetailsRepository.deleteCourseDetailsByCourseID(courseID);
    }

    @Override
    public void deleteCourse(Integer courseID) {
        courseRepository.deleteById(courseID);
    }

}
