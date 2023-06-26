package com.project.CourseSystem.service;

import com.project.CourseSystem.entity.Enrolled;

public interface EnrolledService {

    Enrolled findByAccountIdAndCourseID(Integer userID, Integer courseID);
}
