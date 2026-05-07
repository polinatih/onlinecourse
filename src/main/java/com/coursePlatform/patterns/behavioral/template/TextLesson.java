package com.coursePlatform.patterns.behavioral.template;

import org.springframework.stereotype.Component;

@Component("textLesson")
public class TextLesson extends LessonTemplate {

    @Override
    public String getLessonTitle() { return "Текстовый урок"; }

    @Override
    public String getLessonType() { return "Текстовый курс"; }

    @Override
    public String getIcon() { return "bi-file-text-fill"; }

    @Override
    protected void showTheory() {
        steps.add("📖 Загружается статья и PDF-материалы");
        steps.add("🔖 Доступны закладки и заметки");
    }

    @Override
    protected void doPractice() {
        steps.add("✍️ Студент выполняет письменное задание");
    }

    @Override
    protected void checkKnowledge() {
        steps.add("📋 Тест с развёрнутыми ответами");
    }
}
