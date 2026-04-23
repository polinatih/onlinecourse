package com.coursePlatform.patterns.structural.decorator;

/**
 * ПАТТЕРН DECORATOR — базовый декоратор (Decorator).
 * Хранит ссылку на обёрнутый профиль и делегирует ему все вызовы.
 * Подклассы переопределяют только те методы, которые хотят расширить.
 */
public abstract class UserProfileDecorator implements UserProfile {

    protected final UserProfile wrapped;

    protected UserProfileDecorator(UserProfile wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public String getUsername() {
        return wrapped.getUsername();
    }

    @Override
    public String getDisplayInfo() {
        return wrapped.getDisplayInfo();
    }

    @Override
    public double getProfileCompleteness() {
        return wrapped.getProfileCompleteness();
    }
}