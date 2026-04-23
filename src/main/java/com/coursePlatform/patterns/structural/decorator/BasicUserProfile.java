package com.coursePlatform.patterns.structural.decorator;

import com.coursePlatform.model.user.User;

/**
 * ПАТТЕРН DECORATOR — конкретный компонент (ConcreteComponent).
 * Базовый профиль без каких-либо украшений.
 */
public class BasicUserProfile implements UserProfile {

    private final User user;

    public BasicUserProfile(User user) {
        this.user = user;
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public String getDisplayInfo() {
        return String.format("Пользователь: %s | Роль: %s",
                user.getFullName() != null ? user.getFullName() : user.getUsername(),
                user.getRole());
    }

    @Override
    public double getProfileCompleteness() {
        return 0.3; // базовый профиль заполнен на 30%
    }
}