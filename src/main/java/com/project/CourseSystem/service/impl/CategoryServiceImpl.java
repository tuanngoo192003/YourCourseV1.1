package com.project.CourseSystem.service.impl;

import com.project.CourseSystem.converter.CategoryConverter;
import com.project.CourseSystem.dto.CategoryDTO;
import com.project.CourseSystem.entity.Category;
import com.project.CourseSystem.repository.CategoryRepository;
import com.project.CourseSystem.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    final private CategoryConverter categoryConverter;
    final private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryConverter categoryConverter) {
        this.categoryConverter = categoryConverter;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> category = categoryRepository.getAllCategories();
        List<CategoryDTO> categoryDTO = new ArrayList<>();
        for (Category c : category) {
            CategoryDTO categoryDTO1 = new CategoryDTO();
            categoryDTO1.setCategoryID(c.getCategoryID());
            categoryDTO1.setCategoryName(c.getCategoryName());
            categoryDTO.add(categoryDTO1);
        }
        return categoryDTO;
    }

    @Override
    public CategoryDTO getCategoryByID(Integer categoryID) {
        CategoryDTO categoryDTO = new CategoryDTO();
        Category category = categoryRepository.getCategoryByID(categoryID);
        categoryDTO = categoryConverter.convertEntityToDTO(category);
        return categoryDTO;
    }

    @Override
    public CategoryDTO getCategoryByName(String categoryName) {
        CategoryDTO categoryDTO = new CategoryDTO();
        Category category = categoryRepository.getCategoryByName(categoryName);
        categoryDTO = categoryConverter.convertEntityToDTO(category);
        return categoryDTO;
    }
}
