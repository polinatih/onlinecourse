package com.coursePlatform.patterns.behavioral.strategy;

/**
 * Контекст — заказ курса.
 * Использует стратегию скидки для расчёта итоговой цены.
 */
public class CourseOrder {

    private String courseTitle;
    private double price;
    private DiscountStrategy discountStrategy;

    public CourseOrder(String courseTitle, double price, DiscountStrategy discountStrategy) {
        this.courseTitle = courseTitle;
        this.price = price;
        this.discountStrategy = discountStrategy;
    }

    public void setDiscountStrategy(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    public double getFinalPrice() {
        return discountStrategy.applyDiscount(price);
    }

    public void printOrderSummary() {
        System.out.println("Курс: " + courseTitle);
        System.out.println("Базовая цена: " + price + " руб.");
        System.out.println("Применена стратегия: " + discountStrategy.getDescription());
        System.out.printf("Итого к оплате: %.2f руб.%n", getFinalPrice());
    }
    
}
