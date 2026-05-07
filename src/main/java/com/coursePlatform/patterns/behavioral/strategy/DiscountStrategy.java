package com.coursePlatform.patterns.behavioral.strategy;

/**
 * Паттерн Strategy (Стратегия)
 * Используется для применения разных стратегий расчёта скидки на курсы:
 * для студентов, премиум-пользователей, промо-акций и т.д.
 */
public interface DiscountStrategy {
    double applyDiscount(double originalPrice);
    String getDescription();
    String getBadgeColor();
}
