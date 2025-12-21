package com.project.CourseSystem.repository;

import com.project.CourseSystem.dto.LessonDTO;
import com.project.CourseSystem.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Integer> {

    @Query(value = """
            SELECT
                l.lesson_id as lessonID,
                l.name as lessonName,
                l.description as lessonDes,
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
                    q.quiz_id as quizID,
                    q.name as quizName,
                    q.description as quizDes,
                    q.period as quizPeriod
                ) as quiz
            FROM lesson l
            LEFT JOIN courses c AS c.course_id = l.course_id
            LEFT JOIN quizzes q AS q.quiz_id = l.quiz_id
            WHERE
                courseID = :courseID
            """, nativeQuery = true)
    List<LessonDTO> findAllByCourseID(@Param("courseID") int courseID);

    @Query(value = """
            SELECT
                l.quiz_id
            FROM lesson l
            WHERE
                lesson_id = :lessonID
            """, nativeQuery = true)
    Integer getQuizIDByLessonID(@Param("lessonID") int lessonID);

    @Query(value = """
            SELECT
                l.lesson_id as lessonID,
                l.name as lessonName,
                l.description as lessonDes,
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
                    q.quiz_id as quizID,
                    q.name as quizName,
                    q.description as quizDes,
                    q.period as quizPeriod
                ) as quiz
            FROM lesson
            WHERE
                lesson_id = lessonID
            """, nativeQuery = true)
    LessonDTO findByLessonID(@Param("lessonID") Integer lessonID);
}
