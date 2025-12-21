package com.project.CourseSystem.service;

import com.project.CourseSystem.dto.DiscountDTO;

import java.util.List;

public interface DiscountService {

    public DiscountDTO getDiscountByCourseId(Integer courseID);

    public List<DiscountDTO> getAllDiscounts();

    void deleteDiscount(Integer discountID);

    void addDiscount(DiscountDTO discountDTO);

    void deleteExpiredDiscounts();
}
