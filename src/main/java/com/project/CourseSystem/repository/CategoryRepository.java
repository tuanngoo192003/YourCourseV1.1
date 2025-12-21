package com.project.CourseSystem.repository;

import com.project.CourseSystem.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query(value = """
               SELECT
                   c.category_id as categoryID,
                   c.category_name as categoryName
               FROM category c
               WHERE category_name = :categoryName
            """, nativeQuery = true)
    Category getCategoryByName(@Param("categoryName") String categoryName);
}
