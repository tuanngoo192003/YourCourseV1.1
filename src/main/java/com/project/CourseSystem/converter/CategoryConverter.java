package com.project.CourseSystem.converter;

import com.project.CourseSystem.dto.CategoryDTO;
import com.project.CourseSystem.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter {

    public CategoryDTO convertEntityToDTO(Category category){
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryID(category.getCategoryID());
        categoryDTO.setCategoryName(category.getCategoryName());
        return categoryDTO;
    }

    public Category convertDtoToEtity(CategoryDTO categoryDTO){
        Category category = new Category();
        category.setCategoryID(categoryDTO.getCategoryID());
        category.setCategoryName(categoryDTO.getCategoryName());
        return category;
    }
}
