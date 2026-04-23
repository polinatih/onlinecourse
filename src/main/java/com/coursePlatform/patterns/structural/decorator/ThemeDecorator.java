package com.coursePlatform.patterns.structural.decorator;

/**
 * ПАТТЕРН DECORATOR — конкретный декоратор: Тема оформления.
 * Добавляет выбранную тему интерфейса и увеличивает заполненность на 10%.
 */
public class ThemeDecorator extends UserProfileDecorator {

    private final String themeName;

    public ThemeDecorator(UserProfile wrapped, String themeName) {
        super(wrapped);
        this.themeName = themeName;
    }

    @Override
    public String getDisplayInfo() {
        return wrapped.getDisplayInfo() + String.format(" | 🎨 Тема: %s", themeName);
    }

    @Override
    public double getProfileCompleteness() {
        return Math.min(1.0, wrapped.getProfileCompleteness() + 0.1);
    }
}