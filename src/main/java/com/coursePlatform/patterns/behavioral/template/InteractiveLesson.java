package com.coursePlatform.patterns.behavioral.template;

import org.springframework.stereotype.Component;

@Component("interactiveLesson")
public class InteractiveLesson extends LessonTemplate {

    @Override
    public String getLessonTitle() { return "Интерактивный урок"; }

    @Override
    public String getLessonType() { return "Интерактивный курс"; }

    @Override
    public String getIcon() { return "bi-controller"; }

    @Override
    protected void showTheory() {
        steps.add("🎮 Запускается интерактивный тренажёр");
        steps.add("⚙️ Загружаются задачи и симулятор");
    }

    @Override
    protected void doPractice() {
        steps.add("🧪 Студент решает задачи в реальном времени");
        steps.add("🤖 Система проверяет каждый шаг автоматически");
    }

    @Override
    protected void checkKnowledge() {
        steps.add("🏁 Финальный проект — 20 задач");
    }

    @Override
    protected boolean hasCertificate() { return true; }
}
