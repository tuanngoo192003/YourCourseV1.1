package com.project.CourseSystem.repository;

import com.project.CourseSystem.dto.PaymentDTO;
import com.project.CourseSystem.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    @Query(value = """
            SELECT
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
            FROM payment p
            WHERE
                payment_date >= DATE_SUB(NOW(), INTERVAL :endMonth MONTH)
                AND payment_date < DATE_SUB(NOW(), INTERVAL :startMonth MONTH)
                AND status = :status
            """, nativeQuery = true)
    List<PaymentDTO> getPaymentByMonth(@Param("endMonth") int endMonth,
            @Param("startMonth") int startMonth,
            @Param("status") String status);

    @Query(value = """
            SELECT
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
            FROM payment p
            WHERE
                p.user_id = :userID
            """, nativeQuery = true)
    List<PaymentDTO> findPaymentByUserID(@Param("userID") Integer userID);

    @Query(value = """
            SELECT
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
            FROM payment p
            WHERE
                p.status = :status
            """, nativeQuery = true)
    List<PaymentDTO> getAllConfirmedPayment(@Param("status") String status);
}
