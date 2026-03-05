package com.coursePlatform.controller;

import com.coursePlatform.model.question.Question;
import com.coursePlatform.model.question.QuestionType;
import com.coursePlatform.model.test.Test;
import com.coursePlatform.repository.TestRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/attempt")
public class TestAttemptController {

    private final TestRepository testRepository;

    public TestAttemptController(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    /** Страница прохождения теста */
    @GetMapping("/{testId}")
    public String startTest(@PathVariable Long testId, Model model) {
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new IllegalArgumentException("Тест не найден"));
        model.addAttribute("test", test);
        return "attempt/take";
    }

    /** Обработка ответов и подсчёт результата */
    @PostMapping("/{testId}/submit")
    public String submitTest(@PathVariable Long testId,
                              @RequestParam Map<String, String> answers,
                              Model model) {
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new IllegalArgumentException("Тест не найден"));

        List<Question> questions = test.getQuestions();
        int totalPoints = 0;
        int earnedPoints = 0;
        int correctCount = 0;

        for (Question q : questions) {
            totalPoints += q.getPoints();
            String userAnswer = answers.get("answer_" + q.getId());
            if (userAnswer != null && !userAnswer.isBlank()) {
                boolean isCorrect = false;
                if (q.getQuestionType() == QuestionType.TRUE_FALSE) {
                    isCorrect = userAnswer.equalsIgnoreCase(q.getCorrectAnswer());
                } else {
                    isCorrect = userAnswer.trim()
                            .equalsIgnoreCase(q.getCorrectAnswer().trim());
                }
                if (isCorrect) {
                    earnedPoints += q.getPoints();
                    correctCount++;
                }
            }
        }

        int percentage = totalPoints > 0
                ? (earnedPoints * 100 / totalPoints) : 0;
        boolean passed = percentage >= test.getPassingScore();

        model.addAttribute("test", test);
        model.addAttribute("totalPoints", totalPoints);
        model.addAttribute("earnedPoints", earnedPoints);
        model.addAttribute("correctCount", correctCount);
        model.addAttribute("totalCount", questions.size());
        model.addAttribute("percentage", percentage);
        model.addAttribute("passed", passed);
        model.addAttribute("answers", answers);

        return "attempt/result";
    }
}