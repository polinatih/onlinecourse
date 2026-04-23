package com.coursePlatform.patterns.structural.adapter;

import com.coursePlatform.model.course.Course;
import com.coursePlatform.model.user.User;

import java.util.List;

/**
 * ПАТТЕРН ADAPTER — целевой интерфейс (Target).
 * Единый контракт для всех внешних LMS-систем.
 */
public interface LmsAdapter {
    void publishCourse(Course course);
    void enrollStudent(User student, Course course);
    List<String> fetchExternalCourses();
    String getLmsName();
}