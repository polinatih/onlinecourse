package com.coursePlatform.patterns.behavioral.observer;

/**
 * Конкретный наблюдатель — пользователь сайта.
 */
public class UserNotificationListener implements CourseEventListener {

    private final String username;

    public UserNotificationListener(String username) {
        this.username = username;
    }

    @Override
    public void onEvent(String eventType, String message) {
        System.out.println("[Observer] " + username + " получил: " + message);
    }

    @Override
    public String getSubscriberName() {
        return username;
    }
}
