package com.coursePlatform.patterns.behavioral.template;

import org.springframework.stereotype.Component;

@Component("videoLesson")
public class VideoLesson extends LessonTemplate {

    @Override
    public String getLessonTitle() { return "Видеоурок"; }

    @Override
    public String getLessonType() { return "Видеокурс"; }

    @Override
    public String getIcon() { return "bi-play-circle-fill"; }

    @Override
    protected void showTheory() {
        steps.add("▶️ Воспроизводится обучающее видео");
        steps.add("📝 Отображаются субтитры и конспект");
    }

    @Override
    protected void doPractice() {
        steps.add("💻 Студент повторяет действия из видео");
    }

    @Override
    protected void checkKnowledge() {
        steps.add("❓ Мини-тест из 5 вопросов по видео");
    }
}
