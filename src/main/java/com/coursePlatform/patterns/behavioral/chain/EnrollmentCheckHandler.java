package com.coursePlatform.patterns.behavioral.chain;

public abstract class EnrollmentCheckHandler {

    protected EnrollmentCheckHandler next;

    public EnrollmentCheckHandler setNext(EnrollmentCheckHandler next) {
        this.next = next;
        return next;
    }

    public abstract CheckResult handle(CheckRequest request);

    protected CheckResult passToNext(CheckRequest request) {
        if (next == null) {
            return CheckResult.success("✅ Все проверки пройдены! Запись разрешена.");
        }
        return next.handle(request);
    }
}
