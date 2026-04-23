package com.coursePlatform.patterns.structural.adapter;

/**
 * ПАТТЕРН ADAPTER — адаптируемый класс (Adaptee).
 * Имитирует внешний REST API Moodle с несовместимым интерфейсом.
 */
public class MoodleExternalApi {

    public void moodleCreateCourse(String shortName, String fullName, String summary) {
        System.out.printf("[Moodle API] Создан курс: %s | %s%n", shortName, fullName);
    }

    public void moodleEnrolUser(String userEmail, String courseShortName, String role) {
        System.out.printf("[Moodle API] Пользователь %s записан на %s как %s%n",
                userEmail, courseShortName, role);
    }

    public String[] moodleGetCourses() {
        return new String[]{"Moodle: Основы Java", "Moodle: Базы данных", "Moodle: Spring Boot"};
    }
}