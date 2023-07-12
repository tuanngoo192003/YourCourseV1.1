package com.project.CourseSystem.service;

import com.project.CourseSystem.entity.Discount;

import java.util.List;

public interface DiscountService {

    public Discount getDiscountByCourseId(Integer courseID);

    public List<Discount> getAllDiscounts();
}
