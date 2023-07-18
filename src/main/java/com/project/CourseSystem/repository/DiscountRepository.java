package com.project.CourseSystem.repository;

import com.project.CourseSystem.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface DiscountRepository extends JpaRepository<Discount, Integer> {

    @Query(value = "SELECT * FROM discount WHERE courseid = ?1", nativeQuery = true)
    public Discount getDiscountByCourseId(Integer courseID);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM discount WHERE discountend >= ?1", nativeQuery = true)
    List<Discount> findAllByDiscountEnd(LocalDate today);
}
