package com.coursePlatform.patterns.behavioral.chain;

/**
 * Паттерн Chain of Responsibility (Цепочка ответственности)
 * Используется для проверки студента перед записью на курс:
 * проверка возраста → проверка оплаты → проверка предварительных курсов.
 */
public abstract class EnrollmentHandler {

    protected EnrollmentHandler nextHandler;

    public EnrollmentHandler setNext(EnrollmentHandler handler) {
        this.nextHandler = handler;
        return handler; // позволяет строить цепочку fluent-стилем
    }

    public abstract boolean handle(EnrollmentRequest request);

    public boolean handle(CheckRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handle'");
    }
}
