package com.coursePlatform.patterns.behavioral.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Конкретный субъект — Курс.
 * Хранит список наблюдателей и оповещает их при изменениях.
 */
public class Course implements CourseSubject {

    private String title;
    private List<CourseObserver> observers = new ArrayList<>();

    public Course(String title) {
        this.title = title;
    }

    @Override
    public void addObserver(CourseObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(CourseObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String eventType, String message) {
        for (CourseObserver observer : observers) {
            observer.update(eventType, message);
        }
    }

    public void publishNewLesson(String lessonTitle) {
        System.out.println("[Course] Новый урок добавлен: " + lessonTitle);
        notifyObservers("NEW_LESSON", "В курсе «" + title + "» появился новый урок: " + lessonTitle);
    }

    public void markCourseCompleted() {
        System.out.println("[Course] Курс завершён: " + title);
        notifyObservers("COURSE_COMPLETED", "Курс «" + title + "» успешно завершён!");
    }
}
