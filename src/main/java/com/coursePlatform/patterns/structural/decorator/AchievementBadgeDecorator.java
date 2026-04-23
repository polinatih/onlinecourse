package com.coursePlatform.patterns.structural.decorator;

/**
 * ПАТТЕРН DECORATOR — конкретный декоратор: Значок достижения.
 * Добавляет значок к профилю и увеличивает заполненность на 15%.
 */
public class AchievementBadgeDecorator extends UserProfileDecorator {

    private final String badgeName;
    private final String badgeIcon;

    public AchievementBadgeDecorator(UserProfile wrapped, String badgeName, String badgeIcon) {
        super(wrapped);
        this.badgeName = badgeName;
        this.badgeIcon = badgeIcon;
    }

    @Override
    public String getDisplayInfo() {
        return wrapped.getDisplayInfo() + String.format(" | %s %s", badgeIcon, badgeName);
    }

    @Override
    public double getProfileCompleteness() {
        return Math.min(1.0, wrapped.getProfileCompleteness() + 0.15);
    }
}