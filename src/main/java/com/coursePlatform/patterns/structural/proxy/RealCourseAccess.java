package com.coursePlatform.patterns.structural.proxy;

import com.coursePlatform.model.course.Course;
import com.coursePlatform.model.user.User;
import com.coursePlatform.model.user.UserRole;
import com.coursePlatform.repository.CourseRepository;

import java.util.List;
import java.util.Optional;

/**
 * ПАТТЕРН PROXY — реальный объект (RealSubject).
 * Содержит основную бизнес-логику доступа к курсам.
 */
public class RealCourseAccess implements CourseAccess {

    private final CourseRepository courseRepository;

    public RealCourseAccess(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Optional<Course> getCourse(Long courseId, User requestingUser) {
        return courseRepository.findById(courseId);
    }

    @Override
    public List<Course> getAllCourses(User requestingUser) {
        return courseRepository.findAll();
    }

    @Override
    public boolean canEdit(Long courseId, User requestingUser) {
        return requestingUser.getRole() == UserRole.ADMIN
                || requestingUser.getRole() == UserRole.INSTRUCTOR;
    }
}