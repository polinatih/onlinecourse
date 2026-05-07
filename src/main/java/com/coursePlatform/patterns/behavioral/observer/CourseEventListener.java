package com.coursePlatform.patterns.behavioral.observer;

public interface CourseEventListener {
    void onEvent(String eventType, String message);
    String getSubscriberName();
}
