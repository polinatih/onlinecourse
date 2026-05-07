package com.coursePlatform.patterns.behavioral.strategy;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("noDiscount")
public class NoDiscountStrategy implements DiscountStrategy {

    @Override
    public double applyDiscount(double originalPrice) {
        return originalPrice;
    }

    @Override
    public String getDescription() {
        return "Без скидки";
    }

    @Override
    public String getBadgeColor() {
        return "gray";
    }
}