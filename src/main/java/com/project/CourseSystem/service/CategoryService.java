package com.project.CourseSystem.service;

import com.project.CourseSystem.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAllCategories();

    CategoryDTO getCategoryByID(Integer categoryID);

    CategoryDTO getCategoryByName(String categoryName);
}
