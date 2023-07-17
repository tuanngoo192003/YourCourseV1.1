package com.project.CourseSystem.service.impl;

import com.project.CourseSystem.entity.Discount;
import com.project.CourseSystem.repository.DiscountRepository;
import com.project.CourseSystem.service.DiscountService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountServiceImpl implements DiscountService {

    DiscountRepository discountRepository;

    public DiscountServiceImpl(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    @Override
    public Discount getDiscountByCourseId(Integer courseID) {
        Discount discount = discountRepository.getDiscountByCourseId(courseID);
        return discount;
    }

    @Override
    public List<Discount> getAllDiscounts() {
        List<Discount> discounts = discountRepository.findAll();
        return discounts;
    }

    @Override
    public void deleteDiscount(Integer discountID) {
        discountRepository.deleteById(discountID);
    }
}
