package com.coursePlatform.patterns.behavioral.observer;

/**
 * Конкретный наблюдатель — Студент.
 * Получает уведомления об изменениях в курсе.
 */
public class StudentObserver implements CourseObserver {

    private String studentName;

    public StudentObserver(String studentName) {
        this.studentName = studentName;
    }

    @Override
    public void update(String eventType, String message) {
        System.out.println("[Уведомление для " + studentName + "] (" + eventType + "): " + message);
    }
}
