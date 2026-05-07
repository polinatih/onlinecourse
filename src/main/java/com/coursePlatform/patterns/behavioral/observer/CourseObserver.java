package com.coursePlatform.patterns.behavioral.observer;

/**
 * Паттерн Observer (Наблюдатель)
 * Используется для уведомления пользователей о событиях:
 * запись на курс, завершение урока, новые материалы и т.д.
 */
public interface CourseObserver {
    void update(String eventType, String message);
}
