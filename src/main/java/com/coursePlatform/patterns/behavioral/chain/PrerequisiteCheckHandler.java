package com.coursePlatform.patterns.behavioral.chain;

import org.springframework.stereotype.Component;

@Component
public class PrerequisiteCheckHandler extends EnrollmentCheckHandler {

    @Override
    public CheckResult handle(CheckRequest request) {
        CheckResult result = passToNext(request);
        result.getPassedSteps().add(0, "✅ Пререквизиты: пройдены");
        return result;
    }
}