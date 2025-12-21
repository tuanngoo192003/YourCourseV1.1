package com.project.CourseSystem.repository;

import com.project.CourseSystem.entity.Enrolled;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Repository
public interface EnrolledRepository extends JpaRepository<Enrolled, Integer> {

    @Query(value = """
            SELECT
                e.enrolled_id   AS enrolledID,
                e.enrolled_date AS enrolledDate,
                e.course_id      AS courseID,
                e.account_id     AS accountID,
                json_build_object(
                    'paymentID', p.payment_id,
                    'paymentDate', p.payment_date,
                    'nameOnCard', p.name_on_card,
                    'cardNumber', p.card_number,
                    'expiryDate', p.expiry_date,
                    'cvv', p.cvv,
                    'status', p.status,
                    'paymentAmount', p.payment_amount,
                    'userID', p.user_id
                )::text AS payment
            FROM enrolled e
            WHERE e.account_id = :accountId
              AND e.course_id  = :courseId
            """, nativeQuery = true)
    public Enrolled findByAccountIdAndCourseID(@Param("accountId") Integer accountId,
            @Param("courseId") Integer courseId);

    @Query(value = """
            SELECT
                e.enrolled_id   AS enrolledID,
                e.enrolled_date AS enrolledDate,
                e.course_id      AS courseID,
                e.account_id     AS accountID,
                json_build_object(
                    'paymentID', p.payment_id,
                    'paymentDate', p.payment_date,
                    'nameOnCard', p.name_on_card,
                    'cardNumber', p.card_number,
                    'expiryDate', p.expiry_date,
                    'cvv', p.cvv,
                    'status', p.status,
                    'paymentAmount', p.payment_amount,
                    'userID', p.user_id
                )::text AS payment
            FROM enrolled e
            WHERE e.account_id = :accountId
            """, nativeQuery = true)
    public List<Enrolled> findByAccountId(@Param("accountId") Integer accountID);

    @Query(value = """
            SELECT
                e.enrolled_id   AS enrolledID,
                e.enrolled_date AS enrolledDate,
                e.course_id      AS courseID,
                e.account_id     AS accountID,
                json_build_object(
                    'paymentID', p.payment_id,
                    'paymentDate', p.payment_date,
                    'nameOnCard', p.name_on_card,
                    'cardNumber', p.card_number,
                    'expiryDate', p.expiry_date,
                    'cvv', p.cvv,
                    'status', p.status,
                    'paymentAmount', p.payment_amount,
                    'userID', p.user_id
                )::text AS payment
            FROM enrolled e
            WHERE e.payment_id = :paymentID
            """, nativeQuery = true)
    List<Enrolled> findByPaymentID(@Param("paymentID") Integer paymentID);

    @Query(value = """
            SELECT
                e.enrolled_id   AS enrolledID,
                e.enrolled_date AS enrolledDate,
                e.course_id      AS courseID,
                e.account_id     AS accountID,
                json_build_object(
                    'paymentID', p.payment_id,
                    'paymentDate', p.payment_date,
                    'nameOnCard', p.name_on_card,
                    'cardNumber', p.card_number,
                    'expiryDate', p.expiry_date,
                    'cvv', p.cvv,
                    'status', p.status,
                    'paymentAmount', p.payment_amount,
                    'userID', p.user_id
                )::text AS payment
            FROM enrolled e
            WHERE e.course_id = :courseID
            """, nativeQuery = true)
    List<Enrolled> findByCourseID(@Param("courseID") Integer courseID);

    @Modifying
    @Transactional
    @Query(value = """
            INSERT INTO enrolled (enrolled_date, accountid, courseid, paymentid)
            VALUES (:enrolledDate, :accountID, :courseID, :paymentID)
            """, nativeQuery = true)
    void insertEnrolled(@Param("enrolledDate") Date enrolledDate,
            @Param("accountID") Integer accountID,
            @Param("courseID") Integer courseID,
            @Param("paymentID") Integer paymentID);
}
