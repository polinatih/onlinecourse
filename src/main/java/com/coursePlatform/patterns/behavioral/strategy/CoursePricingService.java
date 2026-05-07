package com.coursePlatform.patterns.behavioral.strategy;
import org.springframework.beans.factory.annotation.Qualifier;
import com.coursePlatform.model.course.Course;
import com.coursePlatform.model.user.User;
import com.coursePlatform.model.user.UserRole;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Сервис расчёта цены курса — использует паттерн Strategy.
 * Стратегия выбирается на основе роли пользователя.
 */
@Service
public class CoursePricingService {

    private final DiscountStrategy noDiscount;
    private final DiscountStrategy studentDiscount;
    private final DiscountStrategy promoDiscount;

    public CoursePricingService(
        @Qualifier("noDiscount")      DiscountStrategy noDiscount,
        @Qualifier("studentDiscount") DiscountStrategy studentDiscount,
        @Qualifier("promoDiscount")   DiscountStrategy promoDiscount) {
    this.noDiscount      = noDiscount;
    this.studentDiscount = studentDiscount;
    this.promoDiscount   = promoDiscount;
}

    public PriceResult calculatePrice(Course course, User user) {
        double base = course.getDurationHours() * 100.0; // условная цена: 100 руб/час

        DiscountStrategy strategy;
        if (user == null) {
            strategy = noDiscount;
        } else if (user.getRole() == UserRole.STUDENT && user.getEnrolledCourses().size() >= 2) {
            strategy = studentDiscount; // постоянный студент
        } else if (course.getDurationHours() != null && course.getDurationHours() > 20) {
            strategy = promoDiscount;   // длинный курс — промо
        } else {
            strategy = noDiscount;
        }

        return new PriceResult(base, strategy);
    }
}
