package com.coursePlatform.patterns.behavioral.chain;

import org.springframework.stereotype.Component;

@Component
public class PaymentCheckHandler extends EnrollmentCheckHandler {

    @Override
    public CheckResult handle(CheckRequest request) {
        if (!request.isHasPaid()) {
            return CheckResult.failure("Оплата не подтверждена");
        }
        CheckResult result = passToNext(request);
        result.getPassedSteps().add(0, "✅ Оплата: подтверждена");
        return result;
    }
}