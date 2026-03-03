package com.coursePlatform.patterns.abstractfactory;

import com.coursePlatform.model.question.Question;
import com.coursePlatform.model.question.QuestionType;
import org.springframework.stereotype.Component;

/**
 * ═══════════════════════════════════════════════════════════
 * ПАТТЕРН: ABSTRACT FACTORY
 * ═══════════════════════════════════════════════════════════
 * Создаёт семейства связанных объектов — вопросы разных типов
 * с одинаковым уровнем сложности.
 * SOLID DIP: сервисы зависят от интерфейса QuestionFactory.
 */
public interface QuestionFactory {
    Question createMultipleChoiceQuestion(String text, String options, String correctAnswer);
    Question createOpenTextQuestion(String text, String correctAnswer);
    Question createTrueFalseQuestion(String text, boolean correctAnswer);
}

// ─── Конкретная фабрика #1 — Вопросы начального уровня ───────
@Component("beginnerQuestionFactory")
class BeginnerQuestionFactory implements QuestionFactory {

    @Override
    public Question createMultipleChoiceQuestion(String text, String options, String correctAnswer) {
        return new Question(text, QuestionType.MULTIPLE_CHOICE, options, correctAnswer, 1, 1);
    }

    @Override
    public Question createOpenTextQuestion(String text, String correctAnswer) {
        return new Question(text, QuestionType.OPEN_TEXT, null, correctAnswer, 2, 2);
    }

    @Override
    public Question createTrueFalseQuestion(String text, boolean correctAnswer) {
        return new Question(text, QuestionType.TRUE_FALSE, null, String.valueOf(correctAnswer), 1, 1);
    }
}

// ─── Конкретная фабрика #2 — Вопросы продвинутого уровня ─────
@Component("advancedQuestionFactory")
class AdvancedQuestionFactory implements QuestionFactory {

    @Override
    public Question createMultipleChoiceQuestion(String text, String options, String correctAnswer) {
        return new Question(text, QuestionType.MULTIPLE_CHOICE, options, correctAnswer, 4, 4);
    }

    @Override
    public Question createOpenTextQuestion(String text, String correctAnswer) {
        return new Question(text, QuestionType.OPEN_TEXT, null, correctAnswer, 5, 5);
    }

    @Override
    public Question createTrueFalseQuestion(String text, boolean correctAnswer) {
        return new Question(text, QuestionType.TRUE_FALSE, null, String.valueOf(correctAnswer), 3, 4);
    }
}
