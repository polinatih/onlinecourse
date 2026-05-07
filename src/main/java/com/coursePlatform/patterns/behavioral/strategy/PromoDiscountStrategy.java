package com.coursePlatform.patterns.behavioral.strategy;

import org.springframework.stereotype.Component;

@Component("promoDiscount")
public class PromoDiscountStrategy implements DiscountStrategy {

    private final double discountAmount = 500.0;

    @Override
    public double applyDiscount(double originalPrice) {
        return Math.max(0, originalPrice - discountAmount);
    }

    @Override
    public String getDescription() {
        return "Промо-скидка: -" + discountAmount + " руб.";
    }

    @Override
    public String getBadgeColor() {
        return "green";
    }
}