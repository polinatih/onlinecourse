package com.coursePlatform.service;

import com.coursePlatform.model.course.Course;
import com.coursePlatform.model.question.Question;
import com.coursePlatform.model.test.Test;
import com.coursePlatform.patterns.abstractfactory.QuestionFactory;
import com.coursePlatform.patterns.builder.TestBuilder;
import com.coursePlatform.patterns.prototype.QuestionPrototypeRegistry;
import com.coursePlatform.repository.CourseRepository;
import com.coursePlatform.repository.QuestionRepository;
import com.coursePlatform.repository.TestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Сервис тестов. Объединяет BUILDER, ABSTRACT FACTORY и PROTOTYPE.
 */
@Service
@Transactional
public class TestService {

    private final TestRepository testRepository;
    private final CourseRepository courseRepository;
    private final QuestionRepository questionRepository;
    private final TestBuilder testBuilder;
    private final QuestionPrototypeRegistry prototypeRegistry;
    private final Map<String, QuestionFactory> questionFactories;

    public TestService(TestRepository testRepository,
                       CourseRepository courseRepository,
                       QuestionRepository questionRepository,
                       TestBuilder testBuilder,
                       QuestionPrototypeRegistry prototypeRegistry,
                       Map<String, QuestionFactory> questionFactories) {
        this.testRepository = testRepository;
        this.courseRepository = courseRepository;
        this.questionRepository = questionRepository;
        this.testBuilder = testBuilder;
        this.prototypeRegistry = prototypeRegistry;
        this.questionFactories = questionFactories;
    }

    public Test createTest(Long courseId, String title, int timeLimitMinutes,
                            int passingScore, String factoryType) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Курс не найден: " + courseId));

        QuestionFactory factory = questionFactories.get(factoryType);
        if (factory == null) {
            throw new IllegalArgumentException("Неизвестная фабрика: " + factoryType);
        }

        // ABSTRACT FACTORY: создаём вопросы нужного уровня сложности
        Question q1 = factory.createMultipleChoiceQuestion(
                "Какой из вариантов верен?", "A;B;C;D", "A");
        Question q2 = factory.createTrueFalseQuestion(
                "Java — объектно-ориентированный язык?", true);
        Question q3 = factory.createOpenTextQuestion(
                "Объясните принцип инкапсуляции.", "Скрытие внутренней реализации");

        List<Question> saved = questionRepository.saveAll(List.of(q1, q2, q3));

        // PROTOTYPE: регистрируем прототипы для будущего клонирования
        prototypeRegistry.register("mc_" + courseId, saved.get(0));
        prototypeRegistry.register("tf_" + courseId, saved.get(1));

        // BUILDER: пошагово собираем тест
        Test test = testBuilder
                .title(title)
                .forCourse(course)
                .withTimeLimit(timeLimitMinutes)
                .withPassingScore(passingScore)
                .withMaxAttempts(3)
                .addQuestions(saved)
                .build();

        return testRepository.save(test);
    }

    public Test addClonedQuestion(Long testId, String prototypeKey) {
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new IllegalArgumentException("Тест не найден: " + testId));
        Question cloned = prototypeRegistry.cloneQuestion(prototypeKey);
        Question saved = questionRepository.save(cloned);
        test.getQuestions().add(saved);
        return testRepository.save(test);
    }

    @Transactional(readOnly = true)
    public List<Test> getTestsByCourse(Long courseId) {
        return testRepository.findByCourseId(courseId);
    }

    @Transactional(readOnly = true)
    public Optional<Test> getTestById(Long id) {
        return testRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<String> getAvailablePrototypes() {
        return List.copyOf(prototypeRegistry.getRegisteredKeys());
    }
}
