package com.project.CourseSystem.repository;

import com.project.CourseSystem.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Integer> {

    @Query(value = "SELECT * FROM lesson WHERE courseID = ?1", nativeQuery = true)
    List<Lesson> findAllByCourseID(int courseID);

    @Query(value = "SELECT quizid FROM lesson WHERE lessonid = ?1", nativeQuery = true)
    Integer getQuizIDByLessonID(int lessonID);

    @Query(value = "SELECT * FROM lesson WHERE lessonid = ?1", nativeQuery = true)
    Lesson findByLessonID(Integer lessonID);
}
