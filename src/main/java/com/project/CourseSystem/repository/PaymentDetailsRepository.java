package com.project.CourseSystem.repository;

import com.project.CourseSystem.dto.PaymentDetailsDTO;
import com.project.CourseSystem.entity.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails, Integer> {

    @Query(value = """
            SELECT
                pd.payment_details_id
                json_build_object(
                    c.course_id as courseID,
                    c.course_name as courseName,
                    c.course_image as courseImage,
                    c.description as courseDes,
                    c.created_date as createdDate,
                    c.start_date as startDate,
                    c.end_date as endDate,
                    c.price as price
                ) as course,
                json_build_object(
                    p.payment_id as paymentID,
                    p.date as paymentDate,
                    p.name_on_card as nameOnCard,
                    p.card_number as cardNumber,
                    p.expiry_date as expiryDate,
                    p.cvv as cvv,
                    p.status as status,
                    p.amount as paymentAmount,
                    p.user_id as userID,
                    p.course_id as courseID
                )
            FROM payment_details pd
            WHERE
                paymentid = :paymentID
            """, nativeQuery = true)
    List<PaymentDetailsDTO> findAllByPaymentID(@Param("paymmentID") Integer paymentID);

    @Query(value = """
            SELECT
                pd.payment_details_id
                json_build_object(
                    c.course_id as courseID,
                    c.course_name as courseName,
                    c.course_image as courseImage,
                    c.description as courseDes,
                    c.created_date as createdDate,
                    c.start_date as startDate,
                    c.end_date as endDate,
                    c.price as price
                ) as course,
                json_build_object(
                    p.payment_id as paymentID,
                    p.date as paymentDate,
                    p.name_on_card as nameOnCard,
                    p.card_number as cardNumber,
                    p.expiry_date as expiryDate,
                    p.cvv as cvv,
                    p.status as status,
                    p.amount as paymentAmount,
                    p.user_id as userID,
                    p.course_id as courseID
                )
            FROM payment_details pd
            WHERE
                p.course_id = :courseID
            """, nativeQuery = true)
    List<PaymentDetailsDTO> findAllByCourseID(@Param("courseID") Integer courseID);
}
