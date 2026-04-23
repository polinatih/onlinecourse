package com.coursePlatform.patterns.structural.decorator;

/**
 * ПАТТЕРН DECORATOR — конкретный декоратор: Аватар.
 * Добавляет к профилю URL аватара и увеличивает заполненность на 20%.
 */
public class AvatarDecorator extends UserProfileDecorator {

    private final String avatarUrl;

    public AvatarDecorator(UserProfile wrapped, String avatarUrl) {
        super(wrapped);
        this.avatarUrl = avatarUrl;
    }

    @Override
    public String getDisplayInfo() {
        return wrapped.getDisplayInfo() + String.format(" | 🖼 Аватар: %s", avatarUrl);
    }

    @Override
    public double getProfileCompleteness() {
        return Math.min(1.0, wrapped.getProfileCompleteness() + 0.2);
    }
}