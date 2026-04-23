package com.coursePlatform.patterns.structural.proxy;

import com.coursePlatform.model.course.Course;
import com.coursePlatform.model.user.User;
import com.coursePlatform.model.user.UserRole;
import com.coursePlatform.repository.CourseRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * ПАТТЕРН PROXY (Заместитель) — структурный паттерн.
 *
 * Проблема: нужно контролировать доступ к курсам и логировать
 * обращения, не засоряя основную бизнес-логику посторонним кодом.
 *
 * Решение: прокси реализует тот же интерфейс CourseAccess, перехватывает
 * вызовы, добавляет логирование и проверку прав, затем делегирует
 * реальному объекту. Клиент не знает, что работает через прокси.
 *
 * Виды прокси: Protection Proxy (права) + Logging Proxy (журнал).
 *
 * SOLID SRP: логика контроля доступа изолирована от бизнес-логики.
 */
@Component
public class CourseAccessProxy implements CourseAccess {

    private final CourseRepository courseRepository;
    private RealCourseAccess realAccess;

    public CourseAccessProxy(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    /** Ленивая инициализация реального объекта */
    private RealCourseAccess getRealAccess() {
        if (realAccess == null) {
            realAccess = new RealCourseAccess(courseRepository);
        }
        return realAccess;
    }

    /**
     * Логирует запрос, затем для студентов фильтрует по записи.
     */
    @Override
    public Optional<Course> getCourse(Long courseId, User requestingUser) {
        logAccess("getCourse", courseId, requestingUser);

        Optional<Course> course = getRealAccess().getCourse(courseId, requestingUser);

        // Protection Proxy: студент видит курс только если записан
        if (course.isPresent() && requestingUser.getRole() == UserRole.STUDENT) {
            boolean enrolled = requestingUser.getEnrolledCourses()
                    .stream()
                    .anyMatch(c -> c.getId().equals(courseId));
            if (!enrolled) {
                logDenied("getCourse", courseId, requestingUser, "не записан на курс");
                return Optional.empty();
            }
        }

        return course;
    }

    /**
     * Студент видит только свои курсы, остальные — все.
     */
    @Override
    public List<Course> getAllCourses(User requestingUser) {
        logAccess("getAllCourses", null, requestingUser);

        if (requestingUser.getRole() == UserRole.STUDENT) {
            List<Course> courses = List.copyOf(requestingUser.getEnrolledCourses());
            System.out.printf("[CourseProxy] Студент %s видит %d курс(ов)%n",
                    requestingUser.getUsername(), courses.size());
            return courses;
        }

        return getRealAccess().getAllCourses(requestingUser);
    }

    /**
     * Проверяет и логирует право на редактирование.
     */
    @Override
    public boolean canEdit(Long courseId, User requestingUser) {
        boolean result = getRealAccess().canEdit(courseId, requestingUser);
        System.out.printf("[CourseProxy] canEdit(courseId=%d, user=%s, role=%s) → %s%n",
                courseId, requestingUser.getUsername(), requestingUser.getRole(), result);
        return result;
    }

    private void logAccess(String operation, Long courseId, User user) {
        System.out.printf("[CourseProxy] %s | %s | user=%s role=%s | courseId=%s%n",
                LocalDateTime.now(), operation, user.getUsername(),
                user.getRole(), courseId != null ? courseId : "*");
    }

    private void logDenied(String operation, Long courseId, User user, String reason) {
        System.out.printf("[CourseProxy] ОТКАЗАНО | %s | user=%s | courseId=%d | причина: %s%n",
                operation, user.getUsername(), courseId, reason);
    }
}