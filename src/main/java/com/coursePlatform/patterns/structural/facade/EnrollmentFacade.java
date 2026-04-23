package com.coursePlatform.patterns.structural.facade;

import com.coursePlatform.model.course.Course;
import com.coursePlatform.model.test.Test;
import com.coursePlatform.model.user.User;
import com.coursePlatform.patterns.structural.adapter.LmsAdapter;
import com.coursePlatform.repository.CourseRepository;
import com.coursePlatform.repository.TestRepository;
import com.coursePlatform.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * ПАТТЕРН FACADE (Фасад) — структурный паттерн.
 *
 * Проблема: запись студента на курс затрагивает несколько подсистем —
 * репозитории, LMS-адаптеры, тесты. Контроллер не должен знать обо всей
 * этой цепочке.
 *
 * Решение: фасад скрывает координацию всех подсистем за одним методом.
 *
 * SOLID SRP: вся логика записи в одном месте.
 * SOLID ISP: контроллер зависит только от фасада, а не от 4 классов.
 */
@Component
public class EnrollmentFacade {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final TestRepository testRepository;
    private final Map<String, LmsAdapter> lmsAdapters;

    public EnrollmentFacade(UserRepository userRepository,
                             CourseRepository courseRepository,
                             TestRepository testRepository,
                             Map<String, LmsAdapter> lmsAdapters) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.testRepository = testRepository;
        this.lmsAdapters = lmsAdapters;
    }

    /**
     * Основной метод фасада: записывает студента на курс.
     * Координирует все шаги — внешний код не знает о деталях.
     */
    public EnrollmentResult enrollStudent(Long userId, Long courseId) {
        System.out.printf("[EnrollmentFacade] Начало записи: userId=%d, courseId=%d%n",
                userId, courseId);

        // Шаг 1: Загрузить пользователя и курс
        User student = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден: " + userId));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Курс не найден: " + courseId));

        // Шаг 2: Проверить, не записан ли уже
        if (student.getEnrolledCourses().contains(course)) {
            System.out.printf("[EnrollmentFacade] %s уже записан на '%s'%n",
                    student.getUsername(), course.getTitle());
            return new EnrollmentResult(false, "Студент уже записан на этот курс", List.of());
        }

        // Шаг 3: Записать студента на курс
        student.getEnrolledCourses().add(course);
        userRepository.save(student);
        System.out.printf("[EnrollmentFacade] %s записан на курс '%s'%n",
                student.getUsername(), course.getTitle());

        // Шаг 4: Синхронизировать со всеми LMS
        lmsAdapters.forEach((name, adapter) -> {
            try {
                adapter.enrollStudent(student, course);
            } catch (Exception e) {
                System.err.printf("[EnrollmentFacade] Ошибка LMS %s: %s%n", name, e.getMessage());
            }
        });

        // Шаг 5: Получить доступные тесты курса
        List<Test> availableTests = testRepository.findByCourseId(courseId);
        System.out.printf("[EnrollmentFacade] Доступно тестов: %d%n", availableTests.size());

        return new EnrollmentResult(true, "Успешно записан на курс: " + course.getTitle(), availableTests);
    }

    /**
     * Отписать студента от курса.
     */
    public void unenrollStudent(Long userId, Long courseId) {
        User student = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден: " + userId));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Курс не найден: " + courseId));

        student.getEnrolledCourses().remove(course);
        userRepository.save(student);
        System.out.printf("[EnrollmentFacade] %s отписан от '%s'%n",
                student.getUsername(), course.getTitle());
    }

    // ─── Результат операции ────────────────────────────────────────────────
    public static class EnrollmentResult {
        private final boolean success;
        private final String message;
        private final List<Test> availableTests;

        public EnrollmentResult(boolean success, String message, List<Test> availableTests) {
            this.success = success;
            this.message = message;
            this.availableTests = availableTests;
        }

        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
        public List<Test> getAvailableTests() { return availableTests; }
    }
}