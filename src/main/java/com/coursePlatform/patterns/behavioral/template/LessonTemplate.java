package com.coursePlatform.patterns.behavioral.template;

import java.util.ArrayList;
import java.util.List;

public abstract class LessonTemplate {

    protected List<String> steps = new ArrayList<>();

    public final void completeLesson() {
        System.out.println("=== Начинаем урок: " + getLessonTitle() + " ===");
        showTheory();
        doPractice();
        checkKnowledge();
        if (hasCertificate()) {
            issueCertificate();
        }
        System.out.println("=== Урок завершён ===\n");
    }

    public final List<String> getSteps() {
        steps.clear();
        showTheory();
        doPractice();
        checkKnowledge();
        if (hasCertificate()) {
            steps.add("🏆 Выдаётся сертификат об окончании!");
        }
        return steps;
    }

    public abstract String getLessonTitle();
    public abstract String getLessonType();
    public abstract String getIcon();
    protected abstract void showTheory();
    protected abstract void doPractice();
    protected abstract void checkKnowledge();

    protected boolean hasCertificate() { return false; }

    private void issueCertificate() {
        System.out.println("[Сертификат] Выдаётся сертификат об окончании урока.");
    }
}