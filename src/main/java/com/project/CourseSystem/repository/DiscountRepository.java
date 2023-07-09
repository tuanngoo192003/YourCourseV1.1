package com.project.CourseSystem.repository;

import com.project.CourseSystem.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DiscountRepository extends JpaRepository<Discount, Integer> {

    @Query(value = "SELECT * FROM discount WHERE courseid = ?1", nativeQuery = true)
    public Discount getDiscountByCourseId(Integer courseID);
}
