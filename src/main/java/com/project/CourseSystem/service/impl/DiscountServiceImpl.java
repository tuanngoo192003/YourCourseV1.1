package com.project.CourseSystem.service.impl;

import com.project.CourseSystem.dto.DiscountDTO;
import com.project.CourseSystem.entity.Discount;
import com.project.CourseSystem.repository.DiscountRepository;
import com.project.CourseSystem.service.DiscountService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    @Override
    public void addDiscount(DiscountDTO discountDTO) {
        Discount discount = new Discount();
        discount.setCourseID(discountDTO.getCourseID());
        discount.setDiscountEnd(discountDTO.getDiscountEnd());
        discount.setDiscountStart(discountDTO.getDiscountStart());
        discount.setPercentage(discountDTO.getPercentage());
        discountRepository.save(discount);
    }

    @Override
    public void deleteExpiredDiscounts() {
        LocalDate today = LocalDate.now();
        List<Discount> expiredDiscounts = discountRepository.findAllByDiscountEnd(today);
        if (!expiredDiscounts.isEmpty()) {
            discountRepository.deleteAll(expiredDiscounts);
        }
    }
}
