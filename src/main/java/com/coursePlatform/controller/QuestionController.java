package com.coursePlatform.controller;

import com.coursePlatform.model.question.Question;
import com.coursePlatform.model.question.QuestionType;
import com.coursePlatform.model.test.Test;
import com.coursePlatform.repository.QuestionRepository;
import com.coursePlatform.repository.TestRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionRepository questionRepository;
    private final TestRepository testRepository;

    public QuestionController(QuestionRepository questionRepository,
                               TestRepository testRepository) {
        this.questionRepository = questionRepository;
        this.testRepository = testRepository;
    }

    // Форма добавления вопроса
    @GetMapping("/add")
    public String showAddForm(@RequestParam Long testId, Model model) {
        model.addAttribute("test", testRepository.findById(testId)
                .orElseThrow(() -> new IllegalArgumentException("Тест не найден")));
        model.addAttribute("questionTypes", QuestionType.values());
        return "questions/add";
    }

    // Сохранение вопроса
    @PostMapping("/add")
    public String addQuestion(
            @RequestParam Long testId,
            @RequestParam String text,
            @RequestParam QuestionType questionType,
            @RequestParam(required = false) String answerOptions,
            @RequestParam String correctAnswer,
            @RequestParam Integer points,
            @RequestParam Integer difficultyScore) {

        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new IllegalArgumentException("Тест не найден"));

        Question question = new Question(text, questionType,
                answerOptions, correctAnswer, points, difficultyScore);
        Question saved = questionRepository.save(question);
        test.getQuestions().add(saved);
        testRepository.save(test);

        return "redirect:/tests/" + testId;
    }

    // Форма редактирования вопроса
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id,
                                @RequestParam Long testId, Model model) {
        model.addAttribute("question", questionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Вопрос не найден")));
        model.addAttribute("testId", testId);
        model.addAttribute("questionTypes", QuestionType.values());
        return "questions/edit";
    }

    // Сохранение изменений вопроса
    @PostMapping("/{id}/edit")
    public String editQuestion(
            @PathVariable Long id,
            @RequestParam Long testId,
            @RequestParam String text,
            @RequestParam QuestionType questionType,
            @RequestParam(required = false) String answerOptions,
            @RequestParam String correctAnswer,
            @RequestParam Integer points,
            @RequestParam Integer difficultyScore) {

        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Вопрос не найден"));

        question.setText(text);
        question.setQuestionType(questionType);
        question.setAnswerOptions(answerOptions);
        question.setCorrectAnswer(correctAnswer);
        question.setPoints(points);
        question.setDifficultyScore(difficultyScore);
        questionRepository.save(question);

        return "redirect:/tests/" + testId;
    }

    // Удаление вопроса из теста
    @PostMapping("/{id}/delete")
    public String deleteQuestion(@PathVariable Long id,
                                  @RequestParam Long testId) {
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new IllegalArgumentException("Тест не найден"));
        test.getQuestions().removeIf(q -> q.getId().equals(id));
        testRepository.save(test);
        questionRepository.deleteById(id);
        return "redirect:/tests/" + testId;
    }
}