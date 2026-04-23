package com.coursePlatform.patterns.structural.decorator;

/**
 * ПАТТЕРН DECORATOR — компонент (Component).
 * Общий интерфейс для базового профиля и всех декораторов.
 */
public interface UserProfile {
    String getUsername();
    String getDisplayInfo();
    double getProfileCompleteness(); // от 0.0 до 1.0
}