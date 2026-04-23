package com.coursePlatform.patterns.structural.proxy;

import com.coursePlatform.model.course.Course;
import com.coursePlatform.model.user.User;

import java.util.List;
import java.util.Optional;

/**
 * ПАТТЕРН PROXY — общий интерфейс для реального объекта и прокси (Subject).
 */
public interface CourseAccess {
    Optional<Course> getCourse(Long courseId, User requestingUser);
    List<Course> getAllCourses(User requestingUser);
    boolean canEdit(Long courseId, User requestingUser);
}