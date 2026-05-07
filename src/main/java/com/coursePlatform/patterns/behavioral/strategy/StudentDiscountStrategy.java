package com.coursePlatform.patterns.behavioral.strategy;

import org.springframework.stereotype.Component;

@Component("studentDiscount")
public class StudentDiscountStrategy implements DiscountStrategy {

    private static final double DISCOUNT_RATE = 0.20;

    @Override
    public double applyDiscount(double originalPrice) {
        return originalPrice * (1 - DISCOUNT_RATE);
    }

    @Override
    public String getDescription() {
        return "Студенческая скидка 20%";
    }

    @Override
    public String getBadgeColor() {
        return "blue";
    }
}