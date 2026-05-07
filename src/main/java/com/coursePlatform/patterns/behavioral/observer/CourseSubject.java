package com.coursePlatform.patterns.behavioral.observer;

public interface CourseSubject {
    void addObserver(CourseObserver observer);
    void removeObserver(CourseObserver observer);
    void notifyObservers(String eventType, String message);
}
