package com.project.CourseSystem.service.impl;

import com.project.CourseSystem.dto.DiscountDTO;
import com.project.CourseSystem.entity.Course;
import com.project.CourseSystem.entity.Discount;
import com.project.CourseSystem.repository.CourseRepository;
import com.project.CourseSystem.repository.DiscountRepository;
import com.project.CourseSystem.service.DiscountService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepository;

    private final CourseRepository courseRepository;

    @Override
    public DiscountDTO getDiscountByCourseId(Integer courseID) {
        return discountRepository.getDiscountByCourseId(courseID);
    }

    @Override
    public List<DiscountDTO> getAllDiscounts() {
        return discountRepository.findAllDiscount();
    }

    @Override
    public void deleteDiscount(Integer discountID) {
        discountRepository.deleteById(discountID);
    }

    @Override
    public void addDiscount(DiscountDTO discountDTO) {
        Course course = courseRepository.findById(discountDTO.getCourseID()).get();
        if (Objects.isNull(course)) {
            // TODO: error handling

            return;
        }

        Discount discount = new Discount();
        discount.setCourseID(course);
        discount.setDiscountEnd(discountDTO.getDiscountEnd());
        discount.setDiscountStart(discountDTO.getDiscountStart());
        discount.setPercentage(discountDTO.getPercentage());

        discountRepository.save(discount);
    }

    @Override
    public void deleteExpiredDiscounts() {
        LocalDate today = LocalDate.now();
        List<DiscountDTO> expiredDiscounts = discountRepository.findAllExpiredDiscount(today.toString());
        if (!expiredDiscounts.isEmpty()) {
            expiredDiscounts.forEach(expiredDiscount -> {
                Discount discount = discountRepository.findById(expiredDiscount.getDiscountID()).get();
                if (Objects.isNull(discount)) {

                }
                discountRepository.delete(discount);
            });

        }
    }
}
