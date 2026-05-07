package com.coursePlatform.patterns.behavioral.strategy;

public class PriceResult {
    private final double originalPrice;
    private final double finalPrice;
    private final String discountDescription;
    private final String badgeColor;

    public PriceResult(double originalPrice, DiscountStrategy strategy) {
        this.originalPrice       = originalPrice;
        this.finalPrice          = strategy.applyDiscount(originalPrice);
        this.discountDescription = strategy.getDescription();
        this.badgeColor          = strategy.getBadgeColor();
    }

    public double getOriginalPrice()       { return originalPrice; }
    public double getFinalPrice()          { return finalPrice; }
    public String getDiscountDescription() { return discountDescription; }
    public String getBadgeColor()          { return badgeColor; }
    public boolean hasDiscount()           { return finalPrice < originalPrice; }
}
