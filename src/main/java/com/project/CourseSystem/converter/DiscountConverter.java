package com.project.CourseSystem.converter;

import com.project.CourseSystem.dto.DiscountDTO;
import com.project.CourseSystem.entity.Discount;
import org.springframework.stereotype.Component;

@Component
public class DiscountConverter {

    public Discount convertToEntity(DiscountDTO discountDTO) {
        Discount discountEntity = new Discount();
        discountEntity.setDiscountID(discountDTO.getDiscountID());
        discountEntity.setDiscountEnd(discountDTO.getDiscountEnd());
        discountEntity.setDiscountStart(discountDTO.getDiscountStart());
        discountEntity.setDiscountContent(discountDTO.getDiscountContent());
        discountEntity.setPercentage(discountDTO.getPercentage());
        discountEntity.setCourseID(discountDTO.getCourseID());
        return discountEntity;
    }

    public DiscountDTO convertToDTO(Discount discountEntity) {
        DiscountDTO discountDTO = new DiscountDTO();
        discountDTO.setDiscountID(discountEntity.getDiscountID());
        discountDTO.setDiscountEnd(discountEntity.getDiscountEnd());
        discountDTO.setDiscountStart(discountEntity.getDiscountStart());
        discountDTO.setDiscountContent(discountEntity.getDiscountContent());
        discountDTO.setPercentage(discountEntity.getPercentage());
        discountDTO.setCourseID(discountEntity.getCourseID());
        return discountDTO;
    }
}
