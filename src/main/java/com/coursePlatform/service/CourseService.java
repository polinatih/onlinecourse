package com.coursePlatform.service;

import com.coursePlatform.model.course.Course;
import com.coursePlatform.model.course.DifficultyLevel;
import com.coursePlatform.patterns.factory.CourseCreator;
import com.coursePlatform.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Сервис курсов. Использует FACTORY METHOD для создания курсов.
 * SOLID DIP: зависит от абстракции CourseCreator.
 */
@Service
@Transactional
public class CourseService {

    private final CourseRepository courseRepository;
    private final Map<String, CourseCreator> courseCreators;

    public CourseService(CourseRepository courseRepository,
                         Map<String, CourseCreator> courseCreators) {
        this.courseRepository = courseRepository;
        this.courseCreators = courseCreators;
    }

    public Course createCourse(String courseType, String title, String description,
                                String author, DifficultyLevel level, Integer durationHours,
                                Object... extraParams) {
        CourseCreator creator = courseCreators.get(courseType);
        if (creator == null) {
            throw new IllegalArgumentException("Неизвестный тип курса: " + courseType);
        }
        Course course = creator.registerCourse(title, description, author, level, durationHours, extraParams);
        return courseRepository.save(course);
    }

    @Transactional(readOnly = true)
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
}
