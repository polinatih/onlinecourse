package com.coursePlatform.patterns.behavioral.chain;

import org.springframework.stereotype.Component;


@Component
public class AgeCheckHandler extends EnrollmentCheckHandler {
    @Override
    public CheckResult handle(CheckRequest request) {
        if (request.getAge() < 16) {
            return CheckResult.failure("❌ Возраст " + request.getAge() + " лет — минимум 16 лет");
        }
        CheckResult result = passToNext(request);
        result.getPassedSteps().add(0, "✅ Возраст: " + request.getAge() + " лет — OK");
        return result;
    }
}
