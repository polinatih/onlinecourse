package com.coursePlatform.patterns.abstractfactory;

import com.coursePlatform.model.question.Question;
import com.coursePlatform.model.question.QuestionType;
import org.springframework.stereotype.Component;

public interface QuestionFactory {
    Question createMultipleChoiceQuestion(String text, String options, String correctAnswer);
    Question createOpenTextQuestion(String text, String correctAnswer);
    Question createTrueFalseQuestion(String text, boolean correctAnswer);
}

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
