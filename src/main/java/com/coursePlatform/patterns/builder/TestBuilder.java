package com.coursePlatform.patterns.builder;

import com.coursePlatform.model.course.Course;
import com.coursePlatform.model.question.Question;
import com.coursePlatform.model.test.Test;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * ═══════════════════════════════════════════════════════════
 * ПАТТЕРН: BUILDER
 * ═══════════════════════════════════════════════════════════
 * Пошагово конструирует объект Test с множеством параметров.
 * Fluent API: testBuilder.title("...").withTimeLimit(30).build()
 * SOLID SRP: отвечает только за конструирование Test.
 *
 * Пример:
 *   Test test = testBuilder
 *       .title("Тест по Java")
 *       .forCourse(course)
 *       .withTimeLimit(30)
 *       .withPassingScore(70)
 *       .addQuestion(q1)
 *       .build();
 */
@Component
public class TestBuilder {

    private String title;
    private Integer timeLimitMinutes;
    private Integer passingScore;
    private Integer maxAttempts = 3;
    private Boolean isRandomized = false;
    private Course course;
    private final List<Question> questions = new ArrayList<>();

    public TestBuilder title(String title) {
        this.title = title;
        return this;
    }

    public TestBuilder forCourse(Course course) {
        this.course = course;
        return this;
    }

    public TestBuilder withTimeLimit(int minutes) {
        this.timeLimitMinutes = minutes;
        return this;
    }

    public TestBuilder withPassingScore(int score) {
        this.passingScore = score;
        return this;
    }

    public TestBuilder withMaxAttempts(int attempts) {
        this.maxAttempts = attempts;
        return this;
    }

    public TestBuilder addQuestion(Question question) {
        this.questions.add(question);
        return this;
    }

    public TestBuilder addQuestions(List<Question> questionList) {
        this.questions.addAll(questionList);
        return this;
    }

    public TestBuilder randomized() {
        this.isRandomized = true;
        return this;
    }

    public Test build() {
        if (title == null || title.isBlank()) {
            throw new IllegalStateException("Название теста обязательно");
        }
        if (passingScore == null) {
            throw new IllegalStateException("Проходной балл обязателен");
        }

        Test test = new Test();
        test.setTitle(title);
        test.setTimeLimitMinutes(timeLimitMinutes);
        test.setPassingScore(passingScore);
        test.setMaxAttempts(maxAttempts);
        test.setIsRandomized(isRandomized);
        test.setCourse(course);
        test.setQuestions(new ArrayList<>(questions));

        System.out.printf("[TestBuilder] Построен тест '%s': %d вопросов, %d мин., проходной балл: %d%n",
                title, questions.size(), timeLimitMinutes != null ? timeLimitMinutes : 0, passingScore);

        reset();
        return test;
    }

    public TestBuilder reset() {
        this.title = null;
        this.timeLimitMinutes = null;
        this.passingScore = null;
        this.maxAttempts = 3;
        this.isRandomized = false;
        this.course = null;
        this.questions.clear();
        return this;
    }
}
