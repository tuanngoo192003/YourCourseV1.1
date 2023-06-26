package com.project.CourseSystem.repository;

import com.project.CourseSystem.dto.CategoryDTO;
import com.project.CourseSystem.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query(value = "SELECT * FROM Category", nativeQuery = true)
    List<Category> getAllCategories();

    @Query(value = "SELECT * FROM Category WHERE categoryid = ?1", nativeQuery = true)
    Category getCategoryByID(Integer categoryID);

    @Query(value = "SELECT * FROM Category WHERE category_name = ?1", nativeQuery = true)
    Category getCategoryByName(String categoryName);
}
