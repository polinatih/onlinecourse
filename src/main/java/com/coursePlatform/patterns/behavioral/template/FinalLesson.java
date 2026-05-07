package com.coursePlatform.patterns.behavioral.template;

public class FinalLesson extends LessonTemplate {

    @Override
    public String getLessonTitle() { return "Финальный экзамен"; }

    @Override
    public String getLessonType() { return "Финальный урок"; }

    @Override
    public String getIcon() { return "bi-trophy-fill"; }

    @Override
    protected void showTheory() {
        steps.add("📚 Повторение всего пройденного материала");
    }

    @Override
    protected void doPractice() {
        steps.add("📝 Написание финального проекта");
    }

    @Override
    protected void checkKnowledge() {
        steps.add("🏁 Финальное тестирование — 20 вопросов");
    }

    @Override
    protected boolean hasCertificate() { return true; }
}
